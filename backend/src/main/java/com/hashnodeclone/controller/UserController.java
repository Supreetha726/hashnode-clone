package com.hashnodeclone.controller;

import com.hashnodeclone.dto.ProfileRequest;
import com.hashnodeclone.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;


    // PUBLIC PROFILE
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(
            @PathVariable Long id) {


        return ResponseEntity.ok(
                userService.getUserProfile(id)
        );
    }


    // UPDATE MY PROFILE
    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileRequest request,
            Authentication authentication) {


        return ResponseEntity.ok(
                userService.updateProfile(
                        authentication.getName(),
                        request.getName(),
                        request.getBio(),
                        request.getAvatarUrl()
                )
        );
    }
}