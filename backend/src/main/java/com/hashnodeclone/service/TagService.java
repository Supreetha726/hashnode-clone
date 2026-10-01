package com.hashnodeclone.service;

import com.hashnodeclone.model.Post;
import com.hashnodeclone.model.Status;
import com.hashnodeclone.model.Tag;
import com.hashnodeclone.repository.PostRepository;
import com.hashnodeclone.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAllByOrderByNameAsc();
    }

    public List<Post> getPostsByTag(String slug) {
        return postRepository.findDistinctByStatusAndTagsSlugOrderByCreatedAtDesc(
                Status.PUBLISHED,
                slug
        );
    }
}