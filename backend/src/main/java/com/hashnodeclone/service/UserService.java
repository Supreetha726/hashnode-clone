package com.hashnodeclone.service;

import com.hashnodeclone.model.User;
import com.hashnodeclone.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // PUBLIC PROFILE
    public Map<String, Object> getUserProfile(
            Long id) {


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        Map<String, Object> response =
                new HashMap<>();


        response.put(
                "id",
                user.getId()
        );


        response.put(
                "name",
                user.getName()
        );


        response.put(
                "email",
                user.getEmail()
        );


        response.put(
                "bio",
                user.getBio()
        );


        response.put(
                "avatarUrl",
                user.getAvatarUrl()
        );


        return response;
    }


    // UPDATE PROFILE
    public Map<String, Object> updateProfile(
            String email,
            String name,
            String bio,
            String avatarUrl) {


        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        if (name != null) {

            user.setName(name);
        }


        if (bio != null) {

            user.setBio(bio);
        }


        if (avatarUrl != null) {

            user.setAvatarUrl(avatarUrl);
        }


        User saved =
                userRepository.save(user);


        Map<String, Object> response =
                new HashMap<>();


        response.put(
                "id",
                saved.getId()
        );

        response.put(
                "name",
                saved.getName()
        );

        response.put(
                "email",
                saved.getEmail()
        );

        response.put(
                "bio",
                saved.getBio()
        );

        response.put(
                "avatarUrl",
                saved.getAvatarUrl()
        );


        return response;
    }
}