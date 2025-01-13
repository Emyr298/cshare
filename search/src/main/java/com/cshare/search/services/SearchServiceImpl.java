package com.cshare.search.services;

import java.util.ArrayList;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;
import org.springframework.stereotype.Service;

import com.cshare.search.dto.DigestContentDto;
import com.cshare.search.dto.ImageToTextResult;
import com.cshare.search.dto.IngestContentDto;
import com.cshare.search.models.Content;
import com.cshare.search.models.ContentResourceText;
import com.cshare.search.repositories.ContentRepository;
import com.cshare.search.repositories.DeletedContentResourceRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ContentRepository contentRepository;
    private final DeletedContentResourceRepository deletedContentResourceRepository;

    public Flux<Content> search(String query, Integer pageNumber, Integer pageSize) {
        return contentRepository.findByText(query, PageRequest.of(pageNumber, pageSize));
    }

    public Mono<Content> digest(DigestContentDto data) {
        Content content = Content.builder()
                .id(data.getId())
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

    public Mono<Content> ingestContentCreate(IngestContentDto data) {
        Content content = Content.builder()
                .id(data.getId())
                .title(data.getTitle())
                .description(data.getDescription())
                .userId(data.getUserId())
                .createdAt(data.getCreatedAt())
                .updatedAt(data.getUpdatedAt())
                .publishedAt(data.getPublishedAt())
                .resourceTexts(new ArrayList<>())
                .build();
        return contentRepository.save(content);
    }

    public Mono<Content> ingestContentUpdate(IngestContentDto data) {
        var contentMono = contentRepository.findByIdWithSeqNoPrimaryTerm(data.getId());
        return contentMono.flatMap(result -> {
            Content oldContent = result.getT1();
            SeqNoPrimaryTerm version = result.getT2();

            Content newContent = Content.builder()
                    .id(data.getId())
                    .title(data.getTitle())
                    .description(data.getDescription())
                    .userId(data.getUserId())
                    .createdAt(data.getCreatedAt())
                    .updatedAt(data.getUpdatedAt())
                    .publishedAt(data.getPublishedAt())
                    .resourceTexts(oldContent.getResourceTexts())
                    .build();
            return contentRepository.update(newContent, version);
        });
    }

    // public Mono<Void> ingestContentDelete(String contentId) {
    // return contentRepository.deleteById(contentId);
    // }

    // public Mono<Void> ingestResourceDelete(String contentId, String
    // contentResourceId) {
    // var contentMono = contentRepository.findById(contentId);
    // // contentMono.flatMap(content -> {

    // // })
    // contentRepository.save()
    // return contentRepository.deleteById(contentId);
    // }

    public Mono<Void> ingestImageToText(ImageToTextResult result) {
        var isDeletedMono = deletedContentResourceRepository
                .findById(result.getContentResourceId())
                .hasElement();

        var contentMono = contentRepository.findById(result.getContentId()); // if content with id not found, ignore
        // TODO: what if there's race condition on content create? (ex: done event is
        // read first than cdc events)
        // TODO: also need to classify between deleted and unprocessed
        var processMono = contentMono.flatMap(content -> {
            return Flux.fromIterable(content.getResourceTexts())
                    .any(contentResourceText -> {
                        var curId = contentResourceText.getId();
                        return curId.equals(result.getContentResourceId());
                    })
                    .flatMap(isExist -> {
                        if (Boolean.FALSE.equals(isExist)) {
                            var newText = ContentResourceText.builder()
                                    .id(result.getContentResourceId())
                                    .text(result.getText())
                                    .build();
                            content.getResourceTexts().add(newText);
                            return contentRepository
                                    .save(content);
                        }
                        return Mono.empty();
                    });
        });

        return isDeletedMono.flatMap(isDeleted -> {
            if (Boolean.TRUE.equals(isDeleted)) {
                return Mono.empty();
            } else {
                return processMono.then();
            }
        });
    }
}
