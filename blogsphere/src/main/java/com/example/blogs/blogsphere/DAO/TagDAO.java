package com.example.blogs.blogsphere.DAO;

import com.example.blogs.blogsphere.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDAO extends JpaRepository<Tag, Long> {
}
