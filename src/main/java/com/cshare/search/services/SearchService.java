package com.cshare.search.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cshare.search.dto.DigestContentDto;
import com.cshare.search.models.Content;
import com.cshare.search.repositories.ContentRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ContentRepository contentRepository;

    public Flux<Content> search(String query, Integer pageNumber, Integer pageSize) {
        return contentRepository.findByTitleOrDescription(query, query, PageRequest.of(pageNumber, pageSize));
    }

    public Mono<Content> digest(DigestContentDto data) {
        Content content = Content.builder()
            .title(data.getTitle())
            .description(data.getDescription())
            .userId(data.getUserId())
            .createdAt(data.getCreatedAt())
            .updatedAt(data.getUpdatedAt())
            .publishedAt(data.getPublishedAt())
            .build();
        return contentRepository.save(content);
    }
}
