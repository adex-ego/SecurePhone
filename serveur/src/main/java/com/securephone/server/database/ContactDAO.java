package com.securephone.server.database;

import com.securephone.server.models.Contact;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    
    private final Connection connection;
    
    public ContactDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean addContact(int userId, int contactId, String nickname) throws SQLException {
        String sql = "INSERT INTO contacts (user_id, contact_id, nickname) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, contactId);
            stmt.setString(3, nickname);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean removeContact(int userId, int contactId) throws SQLException {
        String sql = "DELETE FROM contacts WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, contactId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Contact> getUserContacts(int userId) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE user_id = ?";
        
        List<Contact> contacts = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setUserId(rs.getInt("user_id"));
                contact.setContactId(rs.getInt("contact_id"));
                contact.setNickname(rs.getString("nickname"));
                contact.setFavorite(rs.getBoolean("favorite"));
                contact.setAddedAt(rs.getTimestamp("added_at"));
                contacts.add(contact);
            }
        }
        return contacts;
    }
    
    public boolean isContact(int userId, int contactId) throws SQLException {
        String sql = "SELECT 1 FROM contacts WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, contactId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}