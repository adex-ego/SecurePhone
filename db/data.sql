-- Données de test pour SecurePhone
-- Mots de passe : "password123" (bcrypt hash)

-- Utilisateurs de test
INSERT INTO users (username, password_hash, email, totp_secret, status) VALUES
('alice', '$2a$10$N9qo8uLOickgx2ZMRZoMye7Z7p6c5Q8Qq9q5J8q5N8q5', 'alice@securephone.com', 'JBSWY3DPEHPK3PXP', 'online'),
('bob', '$2a$10$N9qo8uLOickgx2ZMRZoMye7Z7p6c5Q8Qq9q5J8q5N8q5', 'bob@securephone.com', 'JBSWY3DPEHPK3PXP', 'online'),
('charlie', '$2a$10$N9qo8uLOickgx2ZMRZoMye7Z7p6c5Q8Qq9q5J8q5N8q5', 'charlie@securephone.com', 'JBSWY3DPEHPK3PXP', 'offline'),
('diana', '$2a$10$N9qo8uLOickgx2ZMRZoMye7Z7p6c5Q8Qq9q5J8q5N8q5', 'diana@securephone.com', 'JBSWY3DPEHPK3PXP', 'away');

-- Contacts de test
INSERT INTO contacts (user_id, contact_id, nickname) VALUES
(1, 2, 'Bob le bricoleur'),
(1, 3, 'Charlie'),
(2, 1, 'Alice'),
(2, 4, 'Diana'),
(3, 1, 'Alice');

-- Messages de test
INSERT INTO messages (sender_id, receiver_id, message_type, content, timestamp) VALUES
(1, 2, 'text', 'Salut Bob, ça va ?', '2024-01-15 10:30:00'),
(2, 1, 'text', 'Oui Alice, et toi ?', '2024-01-15 10:31:00'),
(1, 2, 'text', 'Super, prêt pour la réunion audio ?', '2024-01-15 10:32:00'),
(2, 1, 'text', 'Oui, je te rejoins dans la salle générale', '2024-01-15 10:33:00'),
(1, 3, 'text', 'Charlie, tu es là ?', '2024-01-15 11:00:00'),
(4, 1, 'text', 'Alice, peux-tu m''appeler ?', '2024-01-15 14:20:00');

-- Messages de groupe (room_id = 'general')
INSERT INTO messages (sender_id, receiver_id, room_id, message_type, content, timestamp) VALUES
(1, 2, 'general', 'text', 'Bienvenue dans la salle générale !', '2024-01-15 09:00:00'),
(2, 1, 'general', 'text', 'Merci Alice', '2024-01-15 09:01:00'),
(3, 1, 'general', 'text', 'Je suis là aussi', '2024-01-15 09:02:00');

-- Tokens push de test (exemple)
INSERT INTO device_tokens (user_id, device_token, platform) VALUES
(1, 'token_alice_android_123', 'android'),
(2, 'token_bob_ios_456', 'ios'),
(1, 'token_alice_web_789', 'web');