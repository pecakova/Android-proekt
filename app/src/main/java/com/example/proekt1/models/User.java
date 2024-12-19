package com.example.proekt1.models;

public class User {
    private String name;
    private String age;
    private String gender;
    private String email;

    private String role;
    // Default constructor (required for Firestore)
    public User() {}

    // Constructor with parameters
    public User(String name, String age, String gender, String email, String role) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.role = role;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
