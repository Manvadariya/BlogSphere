package com.example.blogs.blogsphere.service;

import java.util.List;

import com.example.blogs.blogsphere.entity.Post;

public interface PostService {
    
    // Retrieve all posts
    List<Post> getAllPosts();
    
    // Retrieve a post by its ID
    Post getPostById(Long postId);
    
    // Create a new post
    Post createPost(Post post);
    
    // Update an existing post
    Post updatePost(Long postId, Post updatedPost);
    
    // Delete a post by its ID
    void deletePost(Long postId);
    
    // Add a tag to a post
    Post addTagToPost(Long postId, Long tagId);
    
    // Remove a tag from a post
    Post removeTagFromPost(Long postId, Long tagId);
    
    // Retrieve all posts by a specific tag name
    List<Post> getPostsByTagName(String tagName);
}