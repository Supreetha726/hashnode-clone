package com.hashnodeclone.controller;

import com.hashnodeclone.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "http://localhost:5173")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getPostsByTag(@PathVariable String slug) {
        return ResponseEntity.ok(tagService.getPostsByTag(slug));
    }
}