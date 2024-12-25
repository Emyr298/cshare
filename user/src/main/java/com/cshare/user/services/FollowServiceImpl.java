package com.cshare.user.services;

import java.util.UUID;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cshare.user.models.Follow;
import com.cshare.user.repositories.FollowRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;

    public Flux<UUID> getFollowedUserIds(UUID userId) {
        return followRepository.findByFollowerId(userId).map(Follow::getId);
    }

    public Mono<Void> followUser(UUID followerId, UUID followedId) {
        Follow follow = Follow.builder()
                .followerId(followerId)
                .followedId(followedId)
                .build();
        return followRepository.save(follow)
                .onErrorMap(DuplicateKeyException.class, exc -> new IllegalArgumentException("User already followed"))
                .then();
    }

    public Mono<Void> unfollowUser(UUID followerId, UUID followedId) {
        return followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
    }
}
