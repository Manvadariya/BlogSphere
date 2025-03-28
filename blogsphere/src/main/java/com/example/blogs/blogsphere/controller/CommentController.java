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

import com.example.blogs.blogsphere.entity.Comment;
import com.example.blogs.blogsphere.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /api/comments - Retrieve all comments.
    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    // GET /api/comments/{id} - Retrieve a comment by ID.
    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable("id") Long id) {
        return commentService.getCommentById(id);
    }

    // POST /api/comments - Create a new comment.
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    // PUT /api/comments/{id} - Update an existing comment.
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    // DELETE /api/comments/{id} - Delete a comment.
    @DeleteMapping("/{id}")
    public Void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return null;
    }
}
