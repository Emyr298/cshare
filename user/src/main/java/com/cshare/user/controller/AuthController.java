package com.cshare.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.user.dto.auth.LoginRequestDto;
import com.cshare.user.dto.auth.LoginResponseDto;
import com.cshare.user.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Mono<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto payload) {
        return authService.login(payload);
    }
}
