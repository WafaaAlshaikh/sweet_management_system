package com.example.sweetsystem;

public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String role;
    private String contactInfo;
    private String Address;
    private String image;

    public User(int userId, String username, String password, String email, String role, String contactInfo,String Address, String image) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.contactInfo = contactInfo;
        this.Address = Address;
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return Address;
    }

    public String getRole() {
        return role;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getImage() {
        return image;
    }
}
