package com.example.blogs.blogsphere.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.blogs.blogsphere.entity.Post;

public interface PostDAO extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.tagName = :tagName")
    List<Post> findPostsByTagName(@Param("tagName") String tagName);

    // void deleteByUserId(Long userId);

    // find all the post by user id
    @Query("SELECT p FROM Post p WHERE p.user.userId = :userId")
    List<Post> findPostsByUserId(@Param("userId") Long userId);
}
