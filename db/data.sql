-- ============================================
-- donnees_simple.sql
-- Données de test POUR ÉTUDIANT
-- Mots de passe : tous = "etu123"
-- ============================================

USE securephone;

-- 1. Insérer des utilisateurs de test
INSERT INTO utilisateurs (username, password_hash, email) VALUES
-- admin / admin123
('admin', '$2a$12$1234567890123456789012345678901234567890123456789012', 'admin@ecole.fr'),
-- etu1 / etu123
('etu1', '$2a$12$1234567890123456789012345678901234567890123456789012', 'etu1@ecole.fr'),
-- etu2 / etu123  
('etu2', '$2a$12$1234567890123456789012345678901234567890123456789012', 'etu2@ecole.fr'),
-- etu3 / etu123
('etu3', '$2a$12$1234567890123456789012345678901234567890123456789012', 'etu3@ecole.fr');

-- 2. Créer des contacts (liste d'amis)
INSERT INTO contacts (utilisateur_id, contact_id, statut) VALUES
(1, 2, 'ami'),  -- admin est ami avec etu1
(1, 3, 'ami'),  -- admin est ami avec etu2
(2, 3, 'ami'),  -- etu1 est ami avec etu2
(2, 4, 'ami');  -- etu1 est ami avec etu3

-- 3. Créer un groupe de discussion
INSERT INTO groupes (nom, createur_id) VALUES
('TP Progsys S3', 1),
('Gaming', 2);

-- 4. Ajouter des membres aux groupes
INSERT INTO groupe_membres (groupe_id, utilisateur_id) VALUES
-- Groupe "TP Progsys S3"
(1, 1),  -- admin
(1, 2),  -- etu1
(1, 3),  -- etu2
-- Groupe "Gaming"
(2, 2),  -- etu1
(2, 3),  -- etu2
(2, 4);  -- etu3

-- 5. Insérer des messages de démonstration
INSERT INTO messages (expediteur_id, destinataire_id, groupe_id, contenu, est_lu) VALUES
-- Messages privés
(2, 3, NULL, 'Salut etu2, ça va ?', TRUE),            -- etu1 à etu2 (privé)
(3, 2, NULL, 'Oui et toi ? On fait le TP ensemble ?', FALSE), -- etu2 à etu1

-- Messages de groupe
(1, NULL, 1, 'Bienvenue dans le TP Progsys !', TRUE),  -- admin dans groupe TP
(2, NULL, 1, 'Qui veut coder la partie audio ?', TRUE), -- etu1 dans groupe TP
(3, NULL, 1, 'Moi je prends le chat texte', TRUE),     -- etu2 dans groupe TP

-- Plus de messages pour avoir un historique
(2, NULL, 2, 'Qui joue ce soir ?', TRUE),              -- etu1 dans groupe Gaming
(3, NULL, 2, 'Moi à 20h !', TRUE),                     -- etu2 dans groupe Gaming
(4, NULL, 2, '+1', FALSE);                             -- etu3 dans groupe Gaming (non lu)

-- 6. OPTIONNEL : Sessions audio actives
INSERT INTO sessions_audio (groupe_id, utilisateur_id, ip_client, port_client, en_train_de_parler) VALUES
(2, 2, '192.168.1.10', 6000, TRUE),   -- etu1 parle dans Gaming
(2, 3, '192.168.1.11', 6001, FALSE);  -- etu2 écoute dans Gaming