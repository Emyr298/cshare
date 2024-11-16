package com.cshare.content.repositories;

import org.hibernate.validator.constraints.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cshare.content.models.ContentResource;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContentResourceRepository extends ReactiveCrudRepository<ContentResource, UUID> {
    Flux<ContentResource> findByContentId(UUID contentId);
    Mono<ContentResource> findById(UUID id);
}
