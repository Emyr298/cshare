package com.cshare.user.services;

import com.cshare.user.dto.auth.LoginRequestDto;
import com.cshare.user.dto.auth.LoginResponseDto;

import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<LoginResponseDto> login(LoginRequestDto payload);
}
