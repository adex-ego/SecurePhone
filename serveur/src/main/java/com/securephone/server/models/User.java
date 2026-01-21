package com.securephone.server.models;

import java.sql.Timestamp;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private boolean twofaEnabled;
    private String status; 
    private Timestamp lastSeen;
    private String avatarUrl;
    private Timestamp createdAt;
    
    public User() {}
    
    public User(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.twofaEnabled = true;
        this.status = "offline";
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isTwofaEnabled() { return twofaEnabled; }
    public void setTwofaEnabled(boolean twofaEnabled) { this.twofaEnabled = twofaEnabled; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Timestamp getLastSeen() { return lastSeen; }
    public void setLastSeen(Timestamp lastSeen) { this.lastSeen = lastSeen; }
    
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', status='%s'}", 
                           id, username, status);
    }
}