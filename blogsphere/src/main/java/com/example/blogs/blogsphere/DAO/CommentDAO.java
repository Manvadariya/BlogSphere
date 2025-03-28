package com.example.blogs.blogsphere.DAO;

import com.example.blogs.blogsphere.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDAO extends JpaRepository<Comment, Long> {
}
