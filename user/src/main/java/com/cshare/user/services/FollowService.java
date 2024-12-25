package com.cshare.user.services;

import java.util.UUID;

import com.cshare.user.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FollowService {
    Flux<UUID> getFollowedUserIds(UUID userId);

    Flux<User> getFollowedUsers(UUID userId);

    Mono<Void> followUser(UUID followerId, UUID followedId);

    Mono<Void> unfollowUser(UUID followerId, UUID followedId);
}
