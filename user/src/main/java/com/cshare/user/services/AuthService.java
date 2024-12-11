package com.cshare.user.services;

import com.cshare.user.dto.auth.ProviderTokenDto;
import com.cshare.user.models.User;
import com.cshare.user.dto.auth.LoginResponseDto;

import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<User> getUserFromProviderToken(ProviderTokenDto payload);

    Mono<LoginResponseDto> login(ProviderTokenDto payload);
}
