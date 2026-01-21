package com.securephone.shared.protocol;

import org.json.JSONObject;

public class ChatPacket {
    private MessageType type;
    private JSONObject data;
    private long timestamp;
    
    // Constructeurs
    public ChatPacket() {
        this.timestamp = System.currentTimeMillis();
        this.data = new JSONObject();
    }
    
    public ChatPacket(MessageType type) {
        this();
        this.type = type;
    }
    
    public ChatPacket(MessageType type, JSONObject data) {
        this();
        this.type = type;
        this.data = data;
    }
    
    // Getters/Setters
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
    
    public JSONObject getData() { return data; }
    public void setData(JSONObject data) { this.data = data; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    /**
     * Convert to JSON string
     */
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type.name());
        json.put("data", data);
        json.put("timestamp", timestamp);
        return json.toString();
    }
    
    /**
     * Parse from JSON string
     */
    public static ChatPacket fromJson(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        ChatPacket packet = new ChatPacket();
        packet.type = MessageType.valueOf(json.getString("type"));
        packet.data = json.getJSONObject("data");
        packet.timestamp = json.getLong("timestamp");
        return packet;
    }
    
    /**
     * Create a simple text message packet
     */
    public static ChatPacket createTextMessage(int senderId, int receiverId, String content) {
        ChatPacket packet = new ChatPacket(MessageType.TEXT_MESSAGE);
        JSONObject data = new JSONObject();
        data.put("sender_id", senderId);
        data.put("receiver_id", receiverId);
        data.put("content", content);
        data.put("message_type", "text");
        packet.setData(data);
        return packet;
    }
    
    /**
     * Create a presence update packet
     */
    public static ChatPacket createPresenceUpdate(int userId, String status) {
        ChatPacket packet = new ChatPacket(MessageType.PRESENCE_UPDATE);
        JSONObject data = new JSONObject();
        data.put("user_id", userId);
        data.put("status", status);
        packet.setData(data);
        return packet;
    }
    
    /**
     * Create an error packet
     */
    public static ChatPacket createError(String errorCode, String message) {
        ChatPacket packet = new ChatPacket(MessageType.ERROR);
        JSONObject data = new JSONObject();
        data.put("error_code", errorCode);
        data.put("message", message);
        packet.setData(data);
        return packet;
    }
    
    @Override
    public String toString() {
        return String.format("ChatPacket{type=%s, timestamp=%d}", type, timestamp);
    }
}