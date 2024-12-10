package com.cshare.user.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cshare.user.dto.OAuth.GoogleResponseDto;
import com.cshare.user.exceptions.PermissionException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl {
    private final WebClient webClient;

    @Value("${cshare.user.api.oauth.google}")
    private String googleAPI;

    public Mono<String> getEmailFromGoogleToken(String token) {
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
