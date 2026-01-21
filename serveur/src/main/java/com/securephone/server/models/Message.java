package com.securephone.server.models;

import java.sql.Timestamp;

public class Message {
    private int id;
    private int senderId;
    private int receiverId;
    private String roomId;
    private String messageType; // text, image, file, audio, video
    private String content;
    private boolean encrypted;
    private Timestamp timestamp;
    private boolean readStatus;
    
    public Message() {}
    
    public Message(int senderId, int receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.messageType = "text";
        this.encrypted = false;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.readStatus = false;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }
    
    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }
    
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    
    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public boolean isEncrypted() { return encrypted; }
    public void setEncrypted(boolean encrypted) { this.encrypted = encrypted; }
    
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    
    public boolean isReadStatus() { return readStatus; }
    public void setReadStatus(boolean readStatus) { this.readStatus = readStatus; }
    
    @Override
    public String toString() {
        return String.format("Message{id=%d, from=%d, to=%d, type='%s'}", 
                           id, senderId, receiverId, messageType);
    }
}