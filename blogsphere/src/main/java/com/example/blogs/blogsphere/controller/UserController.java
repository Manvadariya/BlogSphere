package com.example.blogs.blogsphere.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogs.blogsphere.entity.Role;
import com.example.blogs.blogsphere.entity.User;
import com.example.blogs.blogsphere.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users - Retrieve all users.
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/{id} - Retrieve a user by ID.
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    // POST /api/users - Create a new user.
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT /api/users/{id} - Update an existing user.
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE /api/users/{id} - Delete a user.
    @DeleteMapping("/{id}")
    public Void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return null;
    }

    // POST /api/users/{id}/roles - Assign a role to a user.
    @PostMapping("/{id}/roles")
    public User assignRole(@PathVariable("id") Long userId, @RequestBody Role role) {
        return userService.assignRole(userId, role);
    }

    // DELETE /api/users/{id}/roles/{roleId} - Remove a role from a user.
    @DeleteMapping("/{id}/roles/{roleId}")
    public User removeRole(@PathVariable("id") Long userId, @PathVariable("roleId") Long roleId) {
        return userService.removeRole(userId, roleId);
    }
}