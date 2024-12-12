package com.cshare.user.services;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.cshare.user.core.oauth.OAuthStrategy;
import com.cshare.user.core.oauth.factory.OAuthStrategyFactory;
import com.cshare.user.core.storage.adapters.FilePartStorage;
import com.cshare.user.dto.auth.ProviderTokenDto;
import com.cshare.user.dto.users.CreateUserDto;
import com.cshare.user.dto.auth.LoginResponseDto;
import com.cshare.user.dto.auth.ProviderRegisterDto;
import com.cshare.user.models.User;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserService userService;

    private final FilePartStorage filePartStorage;
    private final OAuthStrategyFactory oAuthStrategyFactory;

    @Value("${cshare.user.auth.refresh-token.secret}")
    private String refreshTokenSecret; // TODO: will implement rotations per session

    public Mono<User> getUserFromProviderToken(ProviderTokenDto payload) {
        OAuthStrategy strategy = oAuthStrategyFactory.create(payload.getProvider());
        Mono<String> emailMono = strategy.getEmail(payload.getProviderAccessToken());
        return emailMono.flatMap(userService::getUserByEmail);
    }

    public Mono<LoginResponseDto> login(ProviderTokenDto payload) {
        Mono<User> userMono = getUserFromProviderToken(payload);
        Mono<Tuple2<String, String>> tokensMono = userMono.flatMap(user -> Mono.zip(
                jwtService.signRefreshToken(user.getId().toString(), refreshTokenSecret),
                jwtService.signAccessToken(user.getId().toString())));
        return tokensMono.map(tokens -> new LoginResponseDto(tokens.getT1(), tokens.getT2()));
    }

    public Mono<LoginResponseDto> register(ProviderRegisterDto payload, FilePart avatarImage,
            FilePart coverImage) {
        OAuthStrategy strategy = oAuthStrategyFactory.create(payload.getProvider());
        Mono<String> emailMono = strategy.getEmail(payload.getProviderAccessToken());

        Flux<FilePart> flux = Flux.just(avatarImage, coverImage);
        var imageUrls = flux.flatMap(filePartStorage::uploadFile);

        Mono<URL> avatarUrl = imageUrls.elementAt(0);
        Mono<URL> coverUrl = imageUrls.elementAt(1);

        var userMono = Mono
                .zip(avatarUrl, coverUrl, emailMono)
                .flatMap(tuple -> userService.createUser(
                        new CreateUserDto(
                                payload.getUsername(),
                                tuple.getT3(),
                                payload.getName(),
                                tuple.getT1().toString(),
                                tuple.getT2().toString())));
        Mono<Tuple2<String, String>> tokensMono = userMono.flatMap(user -> Mono.zip(
                jwtService.signRefreshToken(user.getId().toString(), refreshTokenSecret),
                jwtService.signAccessToken(user.getId().toString())));
        return tokensMono.map(tokens -> new LoginResponseDto(tokens.getT1(), tokens.getT2()));
    }
}
