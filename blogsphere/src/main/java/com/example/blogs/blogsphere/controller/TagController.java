package com.example.blogs.blogsphere.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogs.blogsphere.entity.Tag;
import com.example.blogs.blogsphere.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // GET /api/tags - Retrieve all tags.
    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    // GET /api/tags/{id} - Retrieve a tag by ID.
    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") Long id) {
        return tagService.getTagById(id);
    }

    // POST /api/tags - Create a new tag.
    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    // PUT /api/tags/{id} - Update an existing tag.
    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable("id") Long id, @RequestBody Tag tag) {
        return tagService.updateTag(id, tag);
    }

    // DELETE /api/tags/{id} - Delete a tag.
    @DeleteMapping("/{id}")
    public Void deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
        return null;
    }
}
