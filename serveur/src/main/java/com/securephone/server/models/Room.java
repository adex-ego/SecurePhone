package com.securephone.server.models;

import java.sql.Timestamp;

public class Room {
    private int id;
    private String name;
    private int creatorId;
    private String roomType;
    private boolean isPrivate;
    private String passwordHash;
    private Timestamp createdAt;
    
    public Room() {}
    
    public Room(String name, int creatorId, String roomType) {
        this.name = name;
        this.creatorId = creatorId;
        this.roomType = roomType;
        this.isPrivate = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getCreatorId() { return creatorId; }
    public void setCreatorId(int creatorId) { this.creatorId = creatorId; }
    
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    
    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return String.format("Room{id=%d, name='%s', type='%s', private=%b}", 
                           id, name, roomType, isPrivate);
    }
}