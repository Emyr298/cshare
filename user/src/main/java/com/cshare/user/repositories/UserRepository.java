package com.cshare.user.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cshare.user.models.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    Mono<User> findById(UUID id);
}
