package com.securephone.server.models;

import java.sql.Timestamp;

public class Contact {
    private int id;
    private int userId;
    private int contactId;
    private String nickname;
    private boolean favorite;
    private Timestamp addedAt;
    
    public Contact() {}
    
    public Contact(int userId, int contactId) {
        this.userId = userId;
        this.contactId = contactId;
        this.favorite = false;
        this.addedAt = new Timestamp(System.currentTimeMillis());
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }
    
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    
    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
    
    public Timestamp getAddedAt() { return addedAt; }
    public void setAddedAt(Timestamp addedAt) { this.addedAt = addedAt; }
    
    @Override
    public String toString() {
        return String.format("Contact{id=%d, user=%d, contact=%d, nickname='%s'}", 
                           id, userId, contactId, nickname);
    }
}