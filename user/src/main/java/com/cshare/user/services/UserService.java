package com.cshare.user.services;

import com.cshare.user.dto.users.CreateUserDto;
import com.cshare.user.models.User;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> getUserById(String userId);

    Mono<User> getUserByEmail(String email);

    Mono<User> createUser(CreateUserDto payload);
}
