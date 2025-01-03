package com.cshare.user.repositories;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cshare.user.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    Mono<User> findById(UUID id);

    Mono<User> findByEmail(String email);

    Flux<User> findByIdIn(Collection<UUID> ids);
}
