package com.cshare.user.config.auth.manager;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.cshare.user.config.auth.authentication.JwtAuthentication;
import com.cshare.user.config.auth.authentication.UserAuthentication;
import com.cshare.user.exceptions.PermissionException;
import com.cshare.user.services.JwtService;
import com.cshare.user.services.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .cast(JwtAuthentication.class)
                .flatMap(auth -> jwtService.getAccessTokenUserId(auth.getToken()))
                .flatMap(userService::getUserById)
                .map(UserAuthentication::new)
                .cast(Authentication.class)
                .switchIfEmpty(Mono.error(new PermissionException("Invalid access token")))
                .onErrorResume(
                        exc -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, exc.getMessage())));
    }
}
