package com.cshare.user.services;

import com.cshare.user.dto.auth.ProviderTokenDto;
import com.cshare.user.models.User;

import org.springframework.http.codec.multipart.FilePart;

import com.cshare.user.dto.auth.LoginResponseDto;
import com.cshare.user.dto.auth.ProviderRegisterDto;

import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<User> getUserFromProviderToken(ProviderTokenDto payload);

    Mono<LoginResponseDto> login(ProviderTokenDto payload);

    Mono<LoginResponseDto> register(ProviderRegisterDto payload, FilePart avatarImage,
            FilePart coverImage);
}
