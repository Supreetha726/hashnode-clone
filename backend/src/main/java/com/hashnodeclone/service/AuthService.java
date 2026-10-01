package com.hashnodeclone.service;

import com.hashnodeclone.dto.AuthResponse;
import com.hashnodeclone.dto.LoginRequest;
import com.hashnodeclone.dto.RegisterRequest;
import com.hashnodeclone.model.User;
import com.hashnodeclone.repository.UserRepository;
import com.hashnodeclone.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtUtil jwtUtil;


    // REGISTER
    public AuthResponse register(
            RegisterRequest request) {


        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }


        User user = new User();

        user.setName(request.getName());

        user.setEmail(
                request.getEmail().toLowerCase().trim()
        );


        // BCrypt password hashing
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        user.setBio("");

        user.setAvatarUrl("");


        User savedUser =
                userRepository.save(user);


        return new AuthResponse(
                null,
                "Registration successful",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }


    // LOGIN
    public AuthResponse login(
            LoginRequest request) {


        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                                        .toLowerCase()
                                        .trim()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email or password"
                                )
                        );


        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }


        String token =
                jwtUtil.generateToken(
                        user.getEmail()
                );


        return new AuthResponse(
                token,
                "Login successful",
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }


    // CURRENT USER
    public User getCurrentUser(
            String email) {


        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }
}