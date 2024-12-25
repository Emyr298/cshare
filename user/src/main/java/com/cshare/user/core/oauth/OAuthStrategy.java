package com.cshare.user.core.oauth;

import reactor.core.publisher.Mono;

public interface OAuthStrategy {
    Mono<String> getEmail(String token);
}
