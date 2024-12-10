package com.cshare.user.services;

import reactor.core.publisher.Mono;

public interface OAuthService {
    Mono<Boolean> verifyGoogleToken(String token);
}
