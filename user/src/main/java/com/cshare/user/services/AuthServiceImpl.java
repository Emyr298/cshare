package com.cshare.user.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cshare.user.core.oauth.OAuthStrategy;
import com.cshare.user.core.oauth.factory.OAuthStrategyFactory;
import com.cshare.user.dto.auth.LoginRequestDto;
import com.cshare.user.dto.auth.LoginResponseDto;
import com.cshare.user.models.User;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserService userService;

    private final OAuthStrategyFactory oAuthStrategyFactory;

    @Value("${cshare.user.auth.refresh-token.secret}")
    private String refreshTokenSecret; // TODO: will implement rotations per session

    public Mono<LoginResponseDto> login(LoginRequestDto payload) {
        System.out.println(refreshTokenSecret);
        OAuthStrategy strategy = oAuthStrategyFactory.create(payload.getProvider());
        Mono<String> emailMono = strategy.getEmail(payload.getProviderAccessToken());
        Mono<User> userMono = emailMono.flatMap(userService::getUserByEmail);
        Mono<Tuple2<String, String>> tokensMono = userMono.flatMap(user -> Mono.zip(
                jwtService.signRefreshToken(user.getId().toString(), refreshTokenSecret),
                jwtService.signAccessToken(user.getId().toString())));
        return tokensMono.map(tokens -> new LoginResponseDto(tokens.getT1(), tokens.getT2()));
    }
}
