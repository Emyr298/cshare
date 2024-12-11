package com.cshare.user.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cshare.user.models.User;
import com.cshare.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public Mono<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
