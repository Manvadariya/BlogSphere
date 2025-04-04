package com.example.blogs.blogsphere.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.blogs.blogsphere.entity.Comment;

public interface CommentDAO extends JpaRepository<Comment, Long> {
    // find all the comments by user id
    @Query("SELECT c FROM Comment c WHERE c.user.userId = :userId")
    List<Comment> findCommentsByUserId(@Param("userId") Long userId);
}
