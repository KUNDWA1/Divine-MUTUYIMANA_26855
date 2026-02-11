package com.example.demo.controller;

import com.example.demo.model.UserProfile;
import com.example.demo.model.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private List<UserProfile> users = new ArrayList<>();

    public UserProfileController() {
        users.add(new UserProfile(1L, "john_doe", "john@example.com",
                "John Doe", 25, "USA", "Software Developer", true));
    }

    // CREATE
    @PostMapping
    public ApiResponse<UserProfile> createUser(@RequestBody UserProfile user) {
        users.add(user);
        return new ApiResponse<>(true, "User profile created successfully", user);
    }

    // GET ALL
    @GetMapping
    public ApiResponse<List<UserProfile>> getAllUsers() {
        return new ApiResponse<>(true, "Users retrieved successfully", users);
    }

    // SEARCH by username
    @GetMapping("/search")
    public ApiResponse<List<UserProfile>> searchByUsername(@RequestParam String username) {
        List<UserProfile> result = new ArrayList<>();
        for (UserProfile u : users) {
            if (u.getUsername().contains(username)) {
                result.add(u);
            }
        }
        return new ApiResponse<>(true, "Search completed", result);
    }

    // ACTIVATE / DEACTIVATE
    @PatchMapping("/{userId}/toggle")
    public ApiResponse<UserProfile> toggleStatus(@PathVariable Long userId) {
        for (UserProfile u : users) {
            if (u.getUserId().equals(userId)) {
                u.setActive(!u.isActive());
                return new ApiResponse<>(true, "User status updated", u);
            }
        }
        return new ApiResponse<>(false, "User not found", null);
    }
}
