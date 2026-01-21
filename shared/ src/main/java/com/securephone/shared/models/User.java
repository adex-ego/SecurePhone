package com.securephone.shared.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String status; // online, offline, away, busy
    
    // Constructeurs
    public User() {}
    
    public User(int id, String username, String status) {
        this.id = id;
        this.username = username;
        this.status = status;
    }
    
    // Getters/Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', status='%s'}", 
                           id, username, status);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}