package com.cshare.user.utils;

import org.springframework.security.core.Authentication;

import com.cshare.user.models.User;

public class AuthUtils {
    private AuthUtils() {
    }

    public static User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
