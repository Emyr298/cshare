package com.cshare.content.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cshare.content.models.Content;
import com.cshare.content.models.ContentStatus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ContentRepository extends ReactiveCrudRepository<Content, UUID> {
    Mono<Content> findById(UUID id);

    Flux<Content> findByUserId(UUID userId);

    @Query("""
            SELECT * FROM content
            WHERE user_id = :userId
                    AND status = :status
                    AND (published_at BETWEEN :from AND :to)
            LIMIT :limit
        """)
    Flux<Content> findByUserIdAndStatusAndPublishedAtBetweenLimit(
        UUID userId,
        ContentStatus status,
        LocalDateTime from,
        LocalDateTime to,
        Integer limit
    );

    @Query("""
            SELECT * FROM content
            WHERE user_id = :userId
                    AND status = :status
            ORDER BY updated_at DESC
        """)
    Flux<Content> findByUserIdAndStatusOrderByUpdatedAt(UUID userId, ContentStatus status);
}
