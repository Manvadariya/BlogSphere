package com.example.blogs.blogsphere.service;

import java.util.List;

import com.example.blogs.blogsphere.entity.Role;

public interface RoleService {
    
    // Retrieve all roles for a given user id
    List<Role> getRolesByUserId(Long userId);
    
    // Assign a role to a user
    Role addRoleToUser(Long userId, Role role);
    
    // Remove a role from a user
    void removeRoleForUser(Long userId, Long roleId);
}
