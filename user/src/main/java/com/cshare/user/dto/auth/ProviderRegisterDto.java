package com.cshare.user.dto.auth;

import com.cshare.user.core.oauth.OAuthProvider;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProviderRegisterDto {
    @NotNull
    private String providerAccessToken;

    @NotNull
    private OAuthProvider provider;

    @NotNull
    private String username;

    @NotNull
    private String name;
}
