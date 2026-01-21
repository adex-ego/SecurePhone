package com.securephone.server.push;

import java.util.HashMap;
import java.util.Map;

public class PushNotification {
    private String title;
    private String body;
    private String type; 
    private Map<String, String> data;
    private String userId;
    private long timestamp;
    
    public PushNotification() {
        this.data = new HashMap<>();
        this.timestamp = System.currentTimeMillis();
    }
    
    public PushNotification(String title, String body, String type) {
        this();
        this.title = title;
        this.body = body;
        this.type = type;
    }
    
    // Getters/Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Map<String, String> getData() { return data; }
    public void setData(Map<String, String> data) { this.data = data; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    // Méthodes utilitaires
    public PushNotification addData(String key, String value) {
        this.data.put(key, value);
        return this;
    }
    
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"title\":\"").append(escapeJson(title)).append("\",");
        json.append("\"body\":\"").append(escapeJson(body)).append("\",");
        json.append("\"type\":\"").append(type).append("\",");
        json.append("\"timestamp\":").append(timestamp).append(",");
        json.append("\"data\":{");
        
        boolean first = true;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":\"")
                .append(escapeJson(entry.getValue())).append("\"");
            first = false;
        }
        
        json.append("}}");
        return json.toString();
    }
    
    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    @Override
    public String toString() {
        return String.format("PushNotification{title='%s', type='%s', userId='%s'}", 
                           title, type, userId);
    }
}