package com.example.blogs.blogsphere.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogs.blogsphere.DAO.CommentDAO;
import com.example.blogs.blogsphere.entity.Comment;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;

    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    // Retrieve all comments.
    @Override
    public List<Comment> getAllComments() {
        return commentDAO.findAll();
    }

    // Retrieve a comment by its ID.
    @Override
    public Comment getCommentById(Long commentId) {
        return commentDAO.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
    }

    // Create a new comment.
    @Override
    @Transactional
    public Comment createComment(Comment comment) {
        // Set the creation time for the comment.
        comment.setCreatedAt(new Date());
        return commentDAO.save(comment);
    }

    // Update an existing comment.
    @Override
    @Transactional
    public Comment updateComment(Long commentId, Comment updatedComment) {
        Comment existingComment = getCommentById(commentId);
        existingComment.setContent(updatedComment.getContent());
        return commentDAO.save(existingComment);
    }

    // Delete a comment by its ID.
    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        commentDAO.deleteById(commentId);
    }
}
