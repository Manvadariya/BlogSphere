package com.example.blogs.blogsphere.DAO;

import com.example.blogs.blogsphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
}