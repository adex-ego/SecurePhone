USE securephone;

-- Utilisateurs de test
INSERT INTO users (username, password_hash, email, status) VALUES
('alice', '$2a$10$V4C5H6J7K8L9M0N1O2P3Q4R5S6T7U8V9W0X1Y2Z3A4B5C6D7E8F9G0H1', 'alice@securephone.com', 'online'),
('bob', '$2a$10$V4C5H6J7K8L9M0N1O2P3Q4R5S6T7U8V9W0X1Y2Z3A4B5C6D7E8F9G0H1', 'bob@securephone.com', 'online'),
('charlie', '$2a$10$V4C5H6J7K8L9M0N1O2P3Q4R5S6T7U8V9W0X1Y2Z3A4B5C6D7E8F9G0H1', 'charlie@securephone.com', 'away'),
('diana', '$2a$10$V4C5H6J7K8L9M0N1O2P3Q4R5S6T7U8V9W0X1Y2Z3A4B5C6D7E8F9G0H1', 'diana@securephone.com', 'offline');

-- Contacts
INSERT INTO contacts (user_id, contact_id) VALUES
(1, 2), (1, 3), (2, 1), (2, 3), (3, 1), (3, 2), (4, 1);

-- Messages
INSERT INTO messages (sender_id, receiver_id, content) VALUES
(1, 2, 'Salut Bob, prêt pour le test audio ?'),
(2, 1, 'Oui Alice, je lance l''appli'),
(1, 2, 'Parfait, test PTT dans 5 minutes'),
(1, 3, 'Charlie, tu rejoins la salle vidéo ?'),
(3, 1, 'Oui, je suis là dans 2 minutes');

-- Tokens push fictifs
INSERT INTO device_tokens (user_id, device_token, platform) VALUES
(1, 'fcm_token_alice_123', 'android'),
(2, 'fcm_token_bob_456', 'ios'),
(3, 'fcm_token_charlie_789', 'web');

-- Salles
INSERT INTO rooms (name, creator_id, room_type) VALUES
('Général', 1, 'audio'),
('Projet SecurePhone', 1, 'video'),
('Support', 1, 'conference');

-- Membres salles
INSERT INTO room_members (room_id, user_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 2),
(3, 1), (3, 4);