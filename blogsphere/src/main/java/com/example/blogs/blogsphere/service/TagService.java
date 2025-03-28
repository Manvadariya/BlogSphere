package com.example.blogs.blogsphere.service;

import java.util.List;

import com.example.blogs.blogsphere.entity.Tag;

public interface TagService {
    
    // Retrieve all tags
    List<Tag> getAllTags();
    
    // Retrieve a tag by its ID
    Tag getTagById(Long tagId);
    
    // Create a new tag
    Tag createTag(Tag tag);
    
    // Update an existing tag
    Tag updateTag(Long tagId, Tag updatedTag);
    
    // Delete a tag by its ID
    void deleteTag(Long tagId);
}