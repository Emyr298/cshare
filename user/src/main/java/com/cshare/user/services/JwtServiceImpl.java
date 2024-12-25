package com.cshare.user.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cshare.user.exceptions.PermissionException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${cshare.user.auth.access-token.secret}")
    private String accessTokenSecret;

    @Value("${cshare.user.auth.access-token.expiration-seconds}")
    private Long accessTokenExpirationSeconds;

    @Value("${cshare.user.auth.refresh-token.expiration-seconds}")
    private Long refreshTokenExpirationSeconds;

    public Mono<Boolean> isAccessTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC256(accessTokenSecret)).build().verify(token);
            return Mono.just(true);
        } catch (JWTVerificationException e) {
            return Mono.just(false);
        }
    }

    public Mono<String> getAccessTokenUserId(String accessToken) {
        try {
            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(accessTokenSecret)).build().verify(accessToken);
            return Mono.just(decodedJwt.getSubject());
        } catch (JWTVerificationException exc) {
            return Mono.error(new PermissionException("invalid access token"));
        }
    }

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
