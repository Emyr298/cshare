package com.cshare.user.core.oauth;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import com.cshare.user.dto.oauth.GoogleResponseDto;
import com.cshare.user.exceptions.PermissionException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GoogleStrategy implements OAuthStrategy {
    private final WebClient webClient;
    private final String googleAPI;

    public Mono<String> getEmail(String token) {
        return webClient.get()
                .uri(googleAPI + "?accessToken=" + token)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        error -> Mono.error(new PermissionException("invalid oauth token")))
                .bodyToMono(GoogleResponseDto.class)
                .doOnError(Exception.class, exc -> Mono.error(new PermissionException("invalid oauth token")))
                .map(dto -> dto.getEmail());
    }
}
