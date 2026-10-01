package com.hashnodeclone.controller;

import com.hashnodeclone.dto.LoginRequest;
import com.hashnodeclone.dto.RegisterRequest;
import com.hashnodeclone.model.User;
import com.hashnodeclone.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }


    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(
            Authentication authentication) {


        String email =
                authentication.getName();


        return ResponseEntity.ok(
                authService.getCurrentUser(email)
        );
    }
}