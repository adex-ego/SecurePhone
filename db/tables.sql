-- ============================================
-- tables_simple.sql
-- Base de données SIMPLIFIÉE pour SecurePhone
-- Parfait pour un TP étudiant
-- ============================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS securephone;
USE securephone;

-- Base de données SecurePhone
-- Tables minimales : users et messages

-- Table des utilisateurs
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    totp_secret VARCHAR(32), -- Secret TOTP pour A2F
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    status ENUM('online', 'offline', 'away') DEFAULT 'offline',
    avatar_url VARCHAR(255) DEFAULT NULL -- Chemin vers l'avatar
);

-- Table des messages
CREATE TABLE messages (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    sender_id INTEGER NOT NULL,
    receiver_id INTEGER NOT NULL, -- Pour messages privés
    room_id VARCHAR(50) DEFAULT NULL, -- Pour messages de groupe
    message_type ENUM('text', 'image', 'file') DEFAULT 'text',
    content TEXT NOT NULL,
    encrypted BOOLEAN DEFAULT FALSE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_status BOOLEAN DEFAULT FALSE,
    
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Table des contacts (optionnel - si gestion contacts en SQL)
CREATE TABLE contacts (
    user_id INTEGER NOT NULL,
    contact_id INTEGER NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nickname VARCHAR(50),
    
    PRIMARY KEY (user_id, contact_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (contact_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Table pour tokens push (optionnel - si notifications push)
CREATE TABLE device_tokens (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    device_token VARCHAR(255) NOT NULL,
    platform ENUM('android', 'ios', 'web') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_used TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_device (user_id, device_token)
);

-- Index pour performances
CREATE INDEX idx_messages_sender ON messages(sender_id);
CREATE INDEX idx_messages_receiver ON messages(receiver_id);
CREATE INDEX idx_messages_room ON messages(room_id);
CREATE INDEX idx_messages_timestamp ON messages(timestamp);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);