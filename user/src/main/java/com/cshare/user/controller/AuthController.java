package com.cshare.user.controller;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.user.dto.auth.ProviderTokenDto;
import com.cshare.user.core.oauth.OAuthProvider;
import com.cshare.user.dto.ResponseDto;
import com.cshare.user.dto.auth.CheckDto;
import com.cshare.user.dto.auth.LoginResponseDto;
import com.cshare.user.dto.auth.ProviderRegisterDto;
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
    public Mono<ResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody ProviderTokenDto payload) {
        return authService.login(payload).map(ResponseDto::new);
    }

    @PostMapping("/register")
    public Mono<ResponseDto<LoginResponseDto>> register(
            @RequestPart("avatarImage") Mono<FilePart> avatarImageMono,
            @RequestPart("coverImage") Mono<FilePart> coverImageMono,
            @RequestPart("username") String username,
            @RequestPart("name") String name,
            @RequestPart("provider") String provider,
            @RequestPart("providerAccessToken") String providerAccessToken) {
        Mono<FilePart> avatarCheck = avatarImageMono
                .doOnError(ClassCastException.class,
                        exc -> Mono.error(new IllegalArgumentException(
                                "avatarImage cannot be empty")));
        Mono<FilePart> coverCheck = coverImageMono
                .doOnError(ClassCastException.class,
                        exc -> Mono.error(new IllegalArgumentException(
                                "avatarImage cannot be empty")));
        Mono<OAuthProvider> providerCheck = Mono.just(provider).map(OAuthProvider::valueOf);

        return Mono.zip(avatarCheck, coverCheck, providerCheck).flatMap(tuple -> {
            FilePart avatarImage = tuple.getT1();
            FilePart coverImage = tuple.getT2();
            OAuthProvider prov = tuple.getT3();
            ProviderRegisterDto payload = ProviderRegisterDto.builder()
                    .provider(prov)
                    .providerAccessToken(providerAccessToken)
                    .username(username)
                    .name(name)
                    .build();
            return authService.register(payload, avatarImage, coverImage).map(ResponseDto::new);
        });
    }

    @PostMapping("/check")
    public Mono<ResponseDto<CheckDto>> checkProviderToken(
            @Valid @RequestBody ProviderTokenDto payload) {
        return authService.getUserFromProviderToken(payload)
                .map(user -> new CheckDto(true))
                .defaultIfEmpty(new CheckDto(false)).map(ResponseDto::new);
    }
}
