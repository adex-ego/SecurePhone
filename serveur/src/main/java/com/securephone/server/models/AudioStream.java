package com.securephone.server.models;

import java.net.InetAddress;

public class AudioStream {
    private int userId;
    private InetAddress address;
    private int port;
    private long lastPacketTime;
    private boolean isTalking;
    
    public AudioStream() {}
    
    public AudioStream(int userId, InetAddress address, int port) {
        this.userId = userId;
        this.address = address;
        this.port = port;
        this.lastPacketTime = System.currentTimeMillis();
        this.isTalking = false;
    }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public InetAddress getAddress() { return address; }
    public void setAddress(InetAddress address) { this.address = address; }
    
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    
    public long getLastPacketTime() { return lastPacketTime; }
    public void setLastPacketTime(long lastPacketTime) { this.lastPacketTime = lastPacketTime; }
    
    public boolean isTalking() { return isTalking; }
    public void setTalking(boolean isTalking) { this.isTalking = isTalking; }
    
    public void updateActivity() {
        this.lastPacketTime = System.currentTimeMillis();
    }
    
    public boolean isActive(long timeout) {
        return (System.currentTimeMillis() - lastPacketTime) < timeout;
    }
    
    @Override
    public String toString() {
        return String.format("AudioStream{user=%d, %s:%d, talking=%b}", 
                           userId, address.getHostAddress(), port, isTalking);
    }
}