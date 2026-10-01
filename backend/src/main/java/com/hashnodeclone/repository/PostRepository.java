package com.hashnodeclone.repository;

import com.hashnodeclone.model.Post;
import com.hashnodeclone.model.Status;
import com.hashnodeclone.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Public feed
    List<Post> findByStatusOrderByCreatedAtDesc(
            Status status
    );


    // Search published posts by title
    List<Post> findByStatusAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
            Status status,
            String title
    );


    // Published posts by tag
    List<Post> findDistinctByStatusAndTagsSlugOrderByCreatedAtDesc(
            Status status,
            String slug
    );


    // Find one published post
    Optional<Post> findBySlugAndStatus(
            String slug,
            Status status
    );


    // Logged-in user's posts
    List<Post> findByAuthorOrderByCreatedAtDesc(
            User author
    );


    // Check slug
    boolean existsBySlug(
            String slug
    );


    // Check slug while excluding current post
    boolean existsBySlugAndIdNot(
            String slug,
            Long id
    );
}