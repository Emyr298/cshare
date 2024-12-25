package com.cshare.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.user.dto.ResponseDto;
import com.cshare.user.models.User;
import com.cshare.user.services.FollowService;
import com.cshare.user.utils.AuthUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @GetMapping("/followed")
    public Mono<ResponseEntity<ResponseDto<List<UUID>>>> getFollowed(Authentication authentication) {
        User user = AuthUtils.getCurrentUser(authentication);
        Flux<UUID> userIds = followService.getFollowedUserIds(user.getId());
        Mono<ResponseDto<List<UUID>>> body = userIds.collectList().map(ResponseDto::new);
        return body.map(list -> ResponseEntity.ok().body(list));
    }
}
