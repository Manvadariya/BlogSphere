package com.example.blogs.blogsphere.service;

import java.util.List;

import com.example.blogs.blogsphere.entity.Role;
import com.example.blogs.blogsphere.entity.User;

public interface UserService {
    
    // Retrieve all users
    List<User> getAllUsers();
    
    // Retrieve a user by its ID
    User getUserById(Long userId);
    
    // Create a new user
    User createUser(User user);
    
    // Update an existing user
    User updateUser(Long userId, User userDetails);
    
    // Delete a user by its ID
    void deleteUser(Long userId);
    
    // Assign a role to a user
    User assignRole(Long userId, Role role);
    
    // Remove a role from a user
    User removeRole(Long userId, Long roleId);
}