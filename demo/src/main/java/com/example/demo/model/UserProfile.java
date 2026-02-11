package com.example.demo.model;

public class UserProfile {

    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private int age;
    private String country;
    private String bio;
    private boolean active;

    public UserProfile() {}

    public UserProfile(Long userId, String username, String email,
                       String fullName, int age, String country,
                       String bio, boolean active) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.country = country;
        this.bio = bio;
        this.active = active;
    }

    // Getters & Setters (generate normally)
    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getBio() {
        return bio;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}