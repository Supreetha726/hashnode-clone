package com.hashnodeclone.controller;

import com.hashnodeclone.dto.PostRequest;
import com.hashnodeclone.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {


    @Autowired
    private PostService postService;


    // =========================================================
    // PUBLIC FEED
    // =========================================================

    @GetMapping
    public ResponseEntity<?> getPosts(
            @RequestParam(required = false)
            String search,

            @RequestParam(required = false)
            String tag) {

        return ResponseEntity.ok(
                postService.getPublishedPosts(
                        search,
                        tag
                )
        );
    }


    // =========================================================
    // MY POSTS
    // =========================================================

    @GetMapping("/mine")
    public ResponseEntity<?> getMyPosts(
            Authentication authentication) {

        return ResponseEntity.ok(
                postService.getMyPosts(
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // SINGLE PUBLISHED POST
    // =========================================================

    @GetMapping("/{slug}")
    public ResponseEntity<?> getPostBySlug(
            @PathVariable String slug) {

        return ResponseEntity.ok(
                postService.getPostBySlug(
                        slug
                )
        );
    }


    // =========================================================
    // CREATE POST
    // =========================================================

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestBody PostRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                postService.createPost(
                        request,
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // UPDATE POST
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,

            @RequestBody PostRequest request,

            Authentication authentication) {

        return ResponseEntity.ok(
                postService.updatePost(
                        id,
                        request,
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // DELETE POST
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long id,

            Authentication authentication) {

        return ResponseEntity.ok(
                postService.deletePost(
                        id,
                        authentication.getName()
                )
        );
    }
}