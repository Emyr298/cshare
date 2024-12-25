package com.cshare.user.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.user.dto.Response;
import com.cshare.user.dto.follow.FollowDto;
import com.cshare.user.dto.follow.FollowListResponse;
import com.cshare.user.models.User;
import com.cshare.user.services.FollowService;
import com.cshare.user.utils.AuthUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @GetMapping("/followed")
    public Mono<ResponseEntity<FollowListResponse>> getFollowed(Authentication authentication) {
        User user = AuthUtils.getCurrentUser(authentication);
        Flux<User> users = followService.getFollowedUsers(user.getId());
        return users
                .collectList()
                .map(userList -> FollowListResponse.builder()
                        .message("Successfully fetched followed users")
                        .users(userList)
                        .build())
                .map(data -> ResponseEntity.ok().body(data));
    }

    @GetMapping("/followers")
    public Mono<ResponseEntity<FollowListResponse>> getFollowers(Authentication authentication) {
        User user = AuthUtils.getCurrentUser(authentication);
        Flux<User> users = followService.getFollowerUsers(user.getId());
        return users
                .collectList()
                .map(userList -> FollowListResponse.builder()
                        .message("Successfully fetched followers")
                        .users(userList)
                        .build())
                .map(data -> ResponseEntity.ok().body(data));
    }

    @PostMapping("/follow")
    public Mono<ResponseEntity<Response>> follow(
            Authentication authentication,
            @Valid @RequestBody FollowDto payload) {
        User user = AuthUtils.getCurrentUser(authentication);
        Mono<Void> followMono = followService.followUser(user.getId(), UUID.fromString(payload.getFollowedId()));
        return followMono.then(Mono.just(ResponseEntity.ok()
                .body(new Response("Successfully followed user with id " + payload.getFollowedId()))));
    }

    @DeleteMapping("/unfollow/{followedId}")
    public Mono<ResponseEntity<Response>> unfollow(
            Authentication authentication,
            @PathVariable String followedId) {
        User user = AuthUtils.getCurrentUser(authentication);
        Mono<Void> unfollowMono = followService.unfollowUser(user.getId(), UUID.fromString(followedId));
        return unfollowMono.then(Mono.just(ResponseEntity.ok()
                .body(new Response("Successfully unfollowed user with id " + followedId))));
    }
}
