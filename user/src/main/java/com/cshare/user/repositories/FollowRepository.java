package com.cshare.user.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cshare.user.models.Follow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FollowRepository extends ReactiveCrudRepository<Follow, UUID> {
    Flux<Follow> findByFollowerId(UUID followerId);

    Flux<Follow> findByFollowedId(UUID followedId);

    Mono<Void> deleteByFollowerIdAndFollowedId(UUID followerId, UUID followedId);
}
