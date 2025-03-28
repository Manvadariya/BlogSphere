package com.example.blogs.blogsphere.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogs.blogsphere.DAO.RoleDAO;
import com.example.blogs.blogsphere.DAO.UserDAO;
import com.example.blogs.blogsphere.entity.Role;
import com.example.blogs.blogsphere.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    // Retrieve all users.
    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    // Retrieve a user by its ID.
    @Override
    public User getUserById(Long userId) {
        return userDAO.findById(userId) != null ? userDAO.findById(userId).get() : null;
    }

    // Create a new user
    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    // Update an existing user
    @Override
    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        user.setUserName(userDetails.getUserName());
        user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        return userDAO.save(user);
    }

    // Delete a user by its ID.
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (!userDAO.existsById(userId)) {
            throw new RuntimeException("User not found with id " + userId);
        }
        userDAO.deleteById(userId);
    }

    // Assign a role to a user using the helper method.
    @Override
    @Transactional
    public User assignRole(Long userId, Role role) {
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        // user.addRole(role);
        user.getRoles().add(role);
        role.setUser(user);
        roleDAO.save(role);
        return userDAO.save(user);
    }

    // Remove a role from a user using the helper method.
    @Override
    @Transactional
    public User removeRole(Long userId, Long roleId) {
        return userDAO.findById(userId)
                .map(user -> {
                    Role roleToRemove = user.getRoles().stream()
                            .filter(role -> role.getRoleId().equals(roleId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Role not found with id " + roleId));
                    
                    // Leverage the helper method for removal.
                    // user.removeRole(roleToRemove);
                    user.getRoles().remove(roleToRemove);
                    roleToRemove.setUser(null);
                    // remove the role from the database.
                    roleDAO.delete(roleToRemove);
                    return userDAO.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }
}
