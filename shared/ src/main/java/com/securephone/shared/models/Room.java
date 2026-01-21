package com.securephone.shared.models;

import java.io.Serializable;
import java.util.Date;

public class Room implements Serializable {
    private int id;
    private String name;
    private int creatorId;
    private String roomType; // audio, video, conference
    private boolean isPrivate;
    private Date createdAt;
    
    // Constructeurs
    public Room() {}
    
    public Room(int id, String name, String roomType) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
        this.isPrivate = false;
        this.createdAt = new Date();
    }
    
    // Getters/Setters
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
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return String.format("Room{id=%d, name='%s', type='%s'}", id, name, roomType);
    }
}