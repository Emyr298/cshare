package com.cshare.user.services;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
import com.cshare.user.utils.FileUtils;

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
        Mono<User> userMono = getUserFromProviderToken(payload)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Email is not registered")));
        Mono<Tuple2<String, String>> tokensMono = userMono.flatMap(user -> Mono.zip(
                jwtService.signRefreshToken(user.getId().toString(), refreshTokenSecret),
                jwtService.signAccessToken(user.getId().toString())));
        return tokensMono.map(tokens -> new LoginResponseDto(tokens.getT1(), tokens.getT2()));
    }

    public Mono<LoginResponseDto> register(ProviderRegisterDto payload, FilePart avatarImage,
            FilePart coverImage) {
        String userId = UUID.randomUUID().toString();

        OAuthStrategy strategy = oAuthStrategyFactory.create(payload.getProvider());
        Mono<String> emailMono = strategy.getEmail(payload.getProviderAccessToken());

        Mono<List<URL>> imageUrlFlux = Flux.just(avatarImage, coverImage).flatMap(imagePart -> {
            String sanitizedFileName = imagePart.filename().replaceAll("[^A-Za-z0-9.]", "_");
            String ext = FileUtils.getFileExtension(sanitizedFileName);
            String remotePath = Paths.get(userId, UUID.randomUUID().toString() + "." + ext).toString();
            return filePartStorage.uploadFile(remotePath, imagePart);
        }).collectList();

        Mono<User> userMono = emailMono.flatMap(email -> {
            Mono<Object> isNotExistsMono = userService.getUserByEmail(email).flatMap(user -> {
                if (user != null) {
                    return Mono.error(
                            new IllegalArgumentException(
                                    "User with email " + user.getEmail() + " is already registered"));
                }
                return Mono.empty();
            });

            Mono<User> userCreateMono = imageUrlFlux
                    .flatMap(urls -> {
                        CreateUserDto dto = CreateUserDto.builder()
                                .id(userId)
                                .username(payload.getUsername())
                                .email(email)
                                .name(payload.getName())
                                .avatarUrl(urls.get(0).toString())
                                .coverUrl(urls.get(1).toString())
                                .build();
                        return userService.createUser(dto);
                    });

            return isNotExistsMono.then(userCreateMono);
        });

        Mono<Tuple2<String, String>> tokensMono = userMono.flatMap(user -> Mono.zip(
                jwtService.signRefreshToken(user.getId().toString(), refreshTokenSecret),
                jwtService.signAccessToken(user.getId().toString())));

        return tokensMono.map(tokens -> new LoginResponseDto(tokens.getT1(),
                tokens.getT2()));
    }
}
