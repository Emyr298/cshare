package com.cshare.user.services;

import reactor.core.publisher.Mono;

public interface JwtService {
    Mono<String> getAccessTokenUserId(String accessToken);

    Mono<String> signAccessToken(String userId);

    Mono<String> signRefreshToken(String refreshTokenSecret, String userId);
}