package com.cshare.user.services;

import java.util.UUID;

import reactor.core.publisher.Flux;

public interface FollowService {
    Flux<UUID> getFollowedUserIds(UUID userId);
}
