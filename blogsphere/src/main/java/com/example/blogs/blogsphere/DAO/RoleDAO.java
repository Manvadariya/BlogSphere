package com.example.blogs.blogsphere.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogs.blogsphere.entity.Role;

public interface RoleDAO extends JpaRepository<Role, Long> {
    // Retrieve all roles for a given user id.
    List<Role> findByUserUserId(Long userId);
}
