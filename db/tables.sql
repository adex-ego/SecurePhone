-- ============================================
-- tables_simple.sql
-- Base de données SIMPLIFIÉE pour SecurePhone
-- Parfait pour un TP étudiant
-- ============================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS securephone;
USE securephone;

-- ============================================
-- 1. TABLE utilisateurs
-- Stocke les comptes utilisateurs
-- ============================================
CREATE TABLE utilisateurs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,  -- Mot de passe hashé
    email VARCHAR(100),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 2. TABLE contacts  
-- Qui est ami avec qui
-- ============================================
CREATE TABLE contacts (
    utilisateur_id INT NOT NULL,
    contact_id INT NOT NULL,
    statut VARCHAR(20) DEFAULT 'ami',  -- 'ami', 'bloque'
    PRIMARY KEY (utilisateur_id, contact_id),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
    FOREIGN KEY (contact_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- ============================================
-- 3. TABLE messages
-- Historique des conversations
-- ============================================
CREATE TABLE messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    expediteur_id INT NOT NULL,
    destinataire_id INT NULL,           -- NULL = message de groupe
    groupe_id INT NULL,                 -- NULL = message privé
    contenu TEXT NOT NULL,              -- Message (peut être chiffré plus tard)
    est_lu BOOLEAN DEFAULT FALSE,
    date_envoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (expediteur_id) REFERENCES utilisateurs(id),
    FOREIGN KEY (destinataire_id) REFERENCES utilisateurs(id)
);

-- ============================================
-- 4. TABLE groupes
-- Pour les conversations de groupe
-- ============================================
CREATE TABLE groupes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    createur_id INT NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (createur_id) REFERENCES utilisateurs(id)
);

-- ============================================
-- 5. TABLE groupe_membres
-- Qui est dans quel groupe
-- ============================================
CREATE TABLE groupe_membres (
    groupe_id INT NOT NULL,
    utilisateur_id INT NOT NULL,
    PRIMARY KEY (groupe_id, utilisateur_id),
    FOREIGN KEY (groupe_id) REFERENCES groupes(id) ON DELETE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- ============================================
-- 6. TABLE sessions_audio (OPTIONNEL)
-- Pour le talkie-walkie
-- ============================================
CREATE TABLE sessions_audio (
    id INT PRIMARY KEY AUTO_INCREMENT,
    groupe_id INT NOT NULL,
    utilisateur_id INT NOT NULL,
    ip_client VARCHAR(45),
    port_client INT,
    en_train_de_parler BOOLEAN DEFAULT FALSE,
    date_connexion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (groupe_id) REFERENCES groupes(id),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);