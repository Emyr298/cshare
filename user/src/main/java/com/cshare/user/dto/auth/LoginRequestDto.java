package com.cshare.user.dto.auth;

import com.cshare.user.core.oauth.OAuthProvider;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    @NotNull
    private String providerAccessToken;

    @NotNull
    private OAuthProvider provider;
}
