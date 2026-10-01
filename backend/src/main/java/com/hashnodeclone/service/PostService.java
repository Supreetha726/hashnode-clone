package com.hashnodeclone.service;

import com.hashnodeclone.dto.PostRequest;
import com.hashnodeclone.model.Post;
import com.hashnodeclone.model.Status;
import com.hashnodeclone.model.Tag;
import com.hashnodeclone.model.User;
import com.hashnodeclone.repository.PostRepository;
import com.hashnodeclone.repository.TagRepository;
import com.hashnodeclone.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;


    // =========================================================
    // PUBLIC FEED
    // =========================================================

    public List<Post> getPublishedPosts(
            String search,
            String tag) {

        // Search by title
        if (search != null && !search.isBlank()) {

            return postRepository
                    .findByStatusAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
                            Status.PUBLISHED,
                            search.trim()
                    );
        }


        // Filter by tag
        if (tag != null && !tag.isBlank()) {

            return postRepository
                    .findDistinctByStatusAndTagsSlugOrderByCreatedAtDesc(
                            Status.PUBLISHED,
                            tag.trim().toLowerCase()
                    );
        }


        // All published posts
        return postRepository
                .findByStatusOrderByCreatedAtDesc(
                        Status.PUBLISHED
                );
    }


    // =========================================================
    // GET SINGLE PUBLISHED POST
    // =========================================================

    public Post getPostBySlug(
            String slug) {

        return postRepository
                .findBySlugAndStatus(
                        slug,
                        Status.PUBLISHED
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post not found"
                        )
                );
    }


    // =========================================================
    // CREATE POST
    // =========================================================

    public Post createPost(
            PostRequest request,
            String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        if (request.getTitle() == null ||
                request.getTitle().isBlank()) {

            throw new RuntimeException(
                    "Post title is required"
            );
        }


        if (request.getContent() == null ||
                request.getContent().isBlank()) {

            throw new RuntimeException(
                    "Post content is required"
            );
        }


        Post post = new Post();


        post.setTitle(
                request.getTitle().trim()
        );


        post.setContent(
                request.getContent()
        );


        post.setExcerpt(
                request.getExcerpt()
        );


        post.setCoverImage(
                request.getCoverImage()
        );


        post.setStatus(
                getStatus(
                        request.getStatus()
                )
        );


        post.setAuthor(user);


        post.setSlug(
                generateUniqueSlug(
                        request.getTitle()
                )
        );


        post.setTags(
                createOrFindTags(
                        request.getTags()
                )
        );


        return postRepository.save(post);
    }


    // =========================================================
    // UPDATE POST
    // =========================================================

    public Post updatePost(
            Long id,
            PostRequest request,
            String email) {

        Post post =
                postRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Post not found"
                                )
                        );


        // Ownership check
        if (!post.getAuthor()
                .getEmail()
                .equals(email)) {

            throw new RuntimeException(
                    "You can update only your own post"
            );
        }


        // Update title and slug
        if (request.getTitle() != null &&
                !request.getTitle().isBlank()) {

            String newTitle =
                    request.getTitle().trim();

            post.setTitle(newTitle);

            post.setSlug(
                    generateUniqueSlugForUpdate(
                            newTitle,
                            post.getId()
                    )
            );
        }


        // Update content
        if (request.getContent() != null) {

            post.setContent(
                    request.getContent()
            );
        }


        // Update excerpt
        if (request.getExcerpt() != null) {

            post.setExcerpt(
                    request.getExcerpt()
            );
        }


        // Update cover image
        if (request.getCoverImage() != null) {

            post.setCoverImage(
                    request.getCoverImage()
            );
        }


        // Update status
        if (request.getStatus() != null) {

            post.setStatus(
                    getStatus(
                            request.getStatus()
                    )
            );
        }


        // Update tags
        if (request.getTags() != null) {

            post.setTags(
                    createOrFindTags(
                            request.getTags()
                    )
            );
        }


        return postRepository.save(post);
    }


    // =========================================================
    // DELETE POST
    // =========================================================

    public String deletePost(
            Long id,
            String email) {

        Post post =
                postRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Post not found"
                                )
                        );


        // Ownership check
        if (!post.getAuthor()
                .getEmail()
                .equals(email)) {

            throw new RuntimeException(
                    "You can delete only your own post"
            );
        }


        postRepository.delete(post);


        return "Post deleted successfully";
    }


    // =========================================================
    // MY POSTS
    // =========================================================

    public List<Post> getMyPosts(
            String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        return postRepository
                .findByAuthorOrderByCreatedAtDesc(
                        user
                );
    }


    // =========================================================
    // CREATE OR FIND TAGS
    // =========================================================

    private List<Tag> createOrFindTags(
            List<String> tagNames) {

        List<Tag> tags =
                new ArrayList<>();


        if (tagNames == null) {

            return tags;
        }


        for (String name : tagNames) {

            if (name == null ||
                    name.isBlank()) {

                continue;
            }


            String cleanName =
                    name.trim()
                            .toLowerCase();


            String slug =
                    generateSlug(
                            cleanName
                    );


            Tag tag =
                    tagRepository
                            .findBySlug(slug)
                            .orElseGet(() -> {

                                Tag newTag =
                                        new Tag();

                                newTag.setName(
                                        cleanName
                                );

                                newTag.setSlug(
                                        slug
                                );

                                return tagRepository
                                        .save(newTag);
                            });


            tags.add(tag);
        }


        return tags;
    }


    // =========================================================
    // STATUS
    // =========================================================

    private Status getStatus(
            String status) {

        if (status == null ||
                status.isBlank()) {

            return Status.DRAFT;
        }


        if (status.equalsIgnoreCase(
                "published")) {

            return Status.PUBLISHED;
        }


        if (status.equalsIgnoreCase(
                "draft")) {

            return Status.DRAFT;
        }


        throw new RuntimeException(
                "Invalid post status. Use DRAFT or PUBLISHED"
        );
    }


    // =========================================================
    // CREATE SLUG
    // =========================================================

    private String generateUniqueSlug(
            String title) {

        String baseSlug =
                generateSlug(title);


        String slug =
                baseSlug;


        int count = 1;


        while (postRepository.existsBySlug(slug)) {

            slug =
                    baseSlug +
                    "-" +
                    count;

            count++;
        }


        return slug;
    }


    // =========================================================
    // UPDATE SLUG
    // =========================================================

    private String generateUniqueSlugForUpdate(
            String title,
            Long postId) {

        String baseSlug =
                generateSlug(title);


        String slug =
                baseSlug;


        int count = 1;


        while (
                postRepository.existsBySlugAndIdNot(
                        slug,
                        postId
                )
        ) {

            slug =
                    baseSlug +
                    "-" +
                    count;

            count++;
        }


        return slug;
    }


    // =========================================================
    // SLUG GENERATOR
    // =========================================================

    private String generateSlug(
            String value) {

        return value
                .toLowerCase()
                .trim()
                .replaceAll(
                        "[^a-z0-9\\s]",
                        ""
                )
                .replaceAll(
                        "\\s+",
                        "-"
                );
    }
}