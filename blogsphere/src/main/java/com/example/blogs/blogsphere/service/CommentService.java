package com.example.blogs.blogsphere.service;

import java.util.List;

import com.example.blogs.blogsphere.entity.Comment;

public interface CommentService {
    
    // Retrieve all comments
    List<Comment> getAllComments();
    
    // Retrieve a comment by its ID
    Comment getCommentById(Long commentId);
    
    // Create a new comment
    Comment createComment(Comment comment);
    
    // Update an existing comment
    Comment updateComment(Long commentId, Comment updatedComment);
    
    // Delete a comment by its ID
    void deleteComment(Long commentId);
}