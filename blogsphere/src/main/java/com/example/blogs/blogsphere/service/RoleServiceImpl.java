package com.example.blogs.blogsphere.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogs.blogsphere.DAO.RoleDAO;
import com.example.blogs.blogsphere.DAO.UserDAO;
import com.example.blogs.blogsphere.entity.Role;
import com.example.blogs.blogsphere.entity.User;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDAO roleDAO;
    private final UserDAO userDAO;

    public RoleServiceImpl(RoleDAO roleDAO, UserDAO userDAO) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
    }

    // Retrieve all roles for a given user id.
    @Override
    public List<Role> getRolesByUserId(Long userId) {
        return roleDAO.findByUserUserId(userId);
    }

    // Assign a role to a user using the helper method in the User entity.
    @Override
    @Transactional
    public Role addRoleToUser(Long userId, Role role) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        // Use the helper method in the User entity to add the role.
        // user.addRole(role);
        user.getRoles().add(role);
        role.setUser(user);
        // Persist the change; cascade settings on the User entity should handle the Role.
        userDAO.save(user);
        return role;
    }

    // Remove a role from a user using the helper method in the User entity.
    @Override
    @Transactional
    public void removeRoleForUser(Long userId, Long roleId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        Role role = user.getRoles().stream()
                .filter(r -> r.getRoleId().equals(roleId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        
        // Use the helper method to remove the role.
        // user.removeRole(role);
        user.getRoles().remove(role);
        role.setUser(null);
        // Persist the removal.
        userDAO.save(user);
    }
}
