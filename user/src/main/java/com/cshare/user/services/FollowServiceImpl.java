package com.cshare.user.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cshare.user.models.Follow;
import com.cshare.user.repositories.FollowRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;

    public Flux<UUID> getFollowedUserIds(UUID userId) {
        return followRepository.findByFollowerId(userId).map(Follow::getId);
    }
}
