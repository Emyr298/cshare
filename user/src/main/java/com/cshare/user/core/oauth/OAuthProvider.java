package com.cshare.user.core.oauth;

public enum OAuthProvider {
    GOOGLE("GOOGLE");

    private final String value;

    OAuthProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
