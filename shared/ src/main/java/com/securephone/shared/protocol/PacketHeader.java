package com.securephone.shared.protocol;

import java.nio.ByteBuffer;

public class PacketHeader {
    public static final int HEADER_SIZE = 16; // bytes
    
    private MessageType messageType;
    private int sequenceNumber;
    private int payloadLength;
    private int checksum;
    
    // Constructeurs
    public PacketHeader() {}
    
    public PacketHeader(MessageType messageType, int payloadLength) {
        this.messageType = messageType;
        this.sequenceNumber = 0;
        this.payloadLength = payloadLength;
        this.checksum = 0;
    }
    
    // Getters/Setters
    public MessageType getMessageType() { return messageType; }
    public void setMessageType(MessageType messageType) { this.messageType = messageType; }
    
    public int getSequenceNumber() { return sequenceNumber; }
    public void setSequenceNumber(int sequenceNumber) { this.sequenceNumber = sequenceNumber; }
    
    public int getPayloadLength() { return payloadLength; }
    public void setPayloadLength(int payloadLength) { this.payloadLength = payloadLength; }
    
    public int getChecksum() { return checksum; }
    public void setChecksum(int checksum) { this.checksum = checksum; }
    
    /**
     * Serialize header to byte array
     */
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(HEADER_SIZE);
        buffer.putInt(messageType.ordinal());
        buffer.putInt(sequenceNumber);
        buffer.putInt(payloadLength);
        buffer.putInt(checksum);
        return buffer.array();
    }
    
    /**
     * Deserialize header from byte array
     */
    public static PacketHeader fromBytes(byte[] data) {
        if (data.length < HEADER_SIZE) {
            throw new IllegalArgumentException("Header data too short");
        }
        
        ByteBuffer buffer = ByteBuffer.wrap(data);
        PacketHeader header = new PacketHeader();
        header.messageType = MessageType.values()[buffer.getInt()];
        header.sequenceNumber = buffer.getInt();
        header.payloadLength = buffer.getInt();
        header.checksum = buffer.getInt();
        return header;
    }
    
    /**
     * Calculate checksum for data integrity
     */
    public static int calculateChecksum(byte[] data) {
        int checksum = 0;
        for (byte b : data) {
            checksum += b & 0xFF;
        }
        return checksum & 0xFFFFFFFF;
    }
    
    @Override
    public String toString() {
        return String.format("Header{type=%s, seq=%d, len=%d, checksum=%08X}", 
                           messageType, sequenceNumber, payloadLength, checksum);
    }
}