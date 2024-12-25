package com.cshare.user.services;

import java.util.List;
import java.util.UUID;

import com.cshare.user.dto.users.CreateUserDto;
import com.cshare.user.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> getUserById(String userId);

    Mono<User> getUserByEmail(String email);

    Mono<User> createUser(CreateUserDto payload);

    Flux<User> getUserByIds(List<UUID> userIds);
}
