package com.cshare.search.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cshare.search.dto.DigestContentDto;
import com.cshare.search.dto.ImageToTextResult;
import com.cshare.search.models.Content;
import com.cshare.search.models.ContentResourceText;
import com.cshare.search.repositories.ContentRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ContentRepository contentRepository;

    public Flux<Content> search(String query, Integer pageNumber, Integer pageSize) {
        return contentRepository.findByTitleOrDescription(query, query, PageRequest.of(pageNumber, pageSize));
    }

    public Mono<Content> digest(DigestContentDto data) {
        Content content = Content.builder() // TODO: set id as contentId
                .title(data.getTitle())
                .description(data.getDescription())
                .userId(data.getUserId())
                .createdAt(data.getCreatedAt())
                .updatedAt(data.getUpdatedAt())
                .publishedAt(data.getPublishedAt())
                .resourceTexts(data.getResourceTexts())
                .build();
        return contentRepository.save(content);
    }

    public Mono<Void> ingestImageToText(ImageToTextResult result) {
        // TODO: check on content service if image still exists -> prevent race
        // condition DELETE first before process done
        var contentMono = contentRepository.findById(result.getContentId()); // if content with id not found, ignore
        return contentMono.flatMap(content -> {
            return Flux.fromIterable(content.getResourceTexts())
                    .any(contentResourceText -> {
                        var curId = contentResourceText.getId();
                        return curId.equals(result.getContentResourceId());
                    })
                    .flatMap(isExist -> {
                        if (Boolean.FALSE.equals(isExist)) {
                            var newText = ContentResourceText.builder()
                                    .id(result.getContentResourceId())
                                    .contentId(result.getContentId())
                                    .text(result.getText())
                                    .build();
                            content.getResourceTexts().add(newText);
                            System.out.println(content.getResourceTexts());
                            return contentRepository.save(content);
                        }
                        return Mono.empty();
                    });
        }).flatMap(test -> Mono.empty());
    }
}
