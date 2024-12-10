package com.cshare.user.services;

import org.springframework.stereotype.Service;

import com.cshare.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
