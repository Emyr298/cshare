package com.cshare.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.user.dto.ResponseDto;
import com.cshare.user.models.User;
import com.cshare.user.utils.AuthUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/current")
    public Mono<ResponseDto<User>> user(Authentication authentication) {
        return Mono.just(AuthUtils.getCurrentUser(authentication)).map(ResponseDto::new);
    }
}
