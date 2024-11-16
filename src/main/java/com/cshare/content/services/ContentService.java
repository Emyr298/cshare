package com.cshare.content.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cshare.content.dto.CreateContentDto;
import com.cshare.content.exceptions.NotFoundException;
import com.cshare.content.exceptions.PermissionException;
import com.cshare.content.models.Content;
import com.cshare.content.models.ContentStatus;
import com.cshare.content.repositories.ContentRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final Integer CONTENT_LIMIT = 5;
    
    public Flux<Content> findContentByUser(String userId, LocalDateTime from, LocalDateTime to) {
        if (from == null) from = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
        if (to == null) to = LocalDateTime.now();

        return contentRepository
            .findByUserIdAndStatusAndPublishedAtBetweenLimit(
                UUID.fromString(userId),
                ContentStatus.PUBLISHED,
                from,
                to,
                CONTENT_LIMIT
            );
    }

    public Flux<Content> findDrafts(String userId) {
        return contentRepository.findByUserIdAndStatusOrderByUpdatedAt(UUID.fromString(userId), ContentStatus.DRAFT);
    }

    public Mono<Content> getContent(String curUserId, String contentId) {
        return contentRepository
            .findById(UUID.fromString(contentId))
            .switchIfEmpty(Mono.error(new NotFoundException("Content with id " + contentId + " is not available")))
            .filter(
                content -> 
                    content.getStatus().equals(ContentStatus.PUBLISHED) 
                    || content.getUserId().equals(UUID.fromString(curUserId))
            )
            .switchIfEmpty(Mono.error(new PermissionException("User is not the owner of content " + contentId)));
    }

    public Mono<Content> createContent(String userId, CreateContentDto data) {
        LocalDateTime curTime = LocalDateTime.now();
        var contentBuilder = Content.builder()
            .title(data.getTitle())
            .description(data.getDescription())
            .status(data.getStatus())
            .userId(UUID.fromString(userId))
            .createdAt(curTime)
            .updatedAt(curTime);
        if (data.getStatus().equals(ContentStatus.PUBLISHED)) {
            contentBuilder = contentBuilder.publishedAt(curTime);
        }
        return contentRepository.save(contentBuilder.build());
    }
}
