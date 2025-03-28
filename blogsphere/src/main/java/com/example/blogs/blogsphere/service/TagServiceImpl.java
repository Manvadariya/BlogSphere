package com.example.blogs.blogsphere.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogs.blogsphere.DAO.TagDAO;
import com.example.blogs.blogsphere.entity.Tag;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    // Retrieve all tags.
    @Override
    public List<Tag> getAllTags() {
        return tagDAO.findAll();
    }

    // Retrieve a tag by its ID.
    @Override
    public Tag getTagById(Long tagId) {
        return tagDAO.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));
    }

    // Create a new tag.
    @Override
    @Transactional
    public Tag createTag(Tag tag) {
        return tagDAO.save(tag);
    }

    // Update an existing tag.
    @Override
    @Transactional
    public Tag updateTag(Long tagId, Tag updatedTag) {
        Tag existingTag = getTagById(tagId);
        existingTag.setTagName(updatedTag.getTagName());
        return tagDAO.save(existingTag);
    }

    // Delete a tag by its ID.
    @Override
    @Transactional
    public void deleteTag(Long tagId) {
        tagDAO.deleteById(tagId);
    }
}
