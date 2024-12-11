package com.cshare.user.core.oauth.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cshare.user.core.oauth.GoogleStrategy;
import com.cshare.user.core.oauth.OAuthProvider;
import com.cshare.user.core.oauth.OAuthStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthStrategyFactoryImpl implements OAuthStrategyFactory {
    private final WebClient webClient;

    @Value("${cshare.user.api.oauth.google}")
    private String googleApi;

    public OAuthStrategy create(OAuthProvider provider) {
        if (provider.equals(OAuthProvider.GOOGLE)) {
            return new GoogleStrategy(webClient, googleApi);
        } else {
            throw new IllegalArgumentException("Invalid OAuth provider");
        }
    }
}
