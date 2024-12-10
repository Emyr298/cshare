package com.cshare.user.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import reactor.core.publisher.Mono;

public class JwtServiceImpl {
    @Value("${cshare.user.auth.access-token.secret}")
    private String accessTokenSecret;

    @Value("${cshare.user.auth.access-token.expiration-seconds}")
    private Long accessTokenExpirationSeconds;

    @Value("${cshare.user.auth.refresh-token.expiration-seconds}")
    private Long refreshTokenExpirationSeconds;

    public Mono<String> signAccessToken(String userId) {
        Date expiredAt = new Date();
        expiredAt.setTime(expiredAt.getTime() + accessTokenExpirationSeconds * 1000);

        Algorithm algorithm = Algorithm.HMAC256(accessTokenSecret);
        return Mono.just(JWT.create()
                .withSubject(userId)
                .withExpiresAt(expiredAt)
                .sign(algorithm));
    }

    public Mono<String> signRefreshToken(String refreshTokenSecret, String userId) {
        Date expiredAt = new Date();
        expiredAt.setTime(expiredAt.getTime() + refreshTokenExpirationSeconds * 1000);

        Algorithm algorithm = Algorithm.HMAC256(refreshTokenSecret);
        return Mono.just(JWT.create()
                .withSubject(userId)
                .sign(algorithm));
    }
}
