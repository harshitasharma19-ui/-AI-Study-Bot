package models;

import interfaces.Displayable;
import interfaces.FileStorable;

public abstract class User implements Displayable, FileStorable {
    private String userId;
    private String username;
    private String password;
    private String email;

    // Constructor
    public User() {}

    public User(String userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Encapsulation - Getters & Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public abstract String getRole();

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return "User{" + "userId='" + userId + "', username='" + username + "'}";
    }
}