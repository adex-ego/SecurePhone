# 📋 **LISTE DES FICHIERS À ÉDITER POUR HAILEY**

## 🟢 **PEUT FAIRE IMMÉDIATEMENT** (Sans dépendance)

### **1. MODÈLES DE DONNÉES** (`serveur/src/main/java/com/securephone/server/models/`)
- [OK] **`User.java`**
- [OK] **`Message.java`**
- [OK] **`Contact.java`**
- [OK] **`Room.java`**
- [OK] **`AudioStream.java`**

### **2. SÉCURITÉ - UTILITAIRES** (`serveur/src/main/java/com/securephone/server/security/`)
- [OK] **`PasswordHasher.java`**
- [OK] **`SimpleA2F.java`**
- [OK] **`CryptoUtils.java`**
- [OK] **`SessionManager.java`**

### **3. CONFIGURATION** (`serveur/src/main/java/com/securephone/resources/`)
- [OK] **`config.properties`**
- [OK] **`log4j2.xml`**
- [OK] **`totp_secrets.key`** (template vide)
# ↑ Fichier de stockage TOTP sécurisé (base ou fichier chiffré)

### **Base de données** → SEULEMENT users et messages en SQL
- [OK] **`UserDAO.java`** (CRUD users)
- [OK] **`MessageDAO.java`** (CRUD messages)
- [OK] **`ContactDAO.java`**
- [OK] **`DeviceTokenDAO.java`** 

### **Images/files** → Stockage fichier dans `/serveur/images/`
- [OK] API upload/download (si besoin)

### **4. BASE DE DONNÉES** (`serveur/src/main/java/com/securephone/server/database/`)
- [OK] **`DatabaseManager.java`**
- [OK] **`UserDAO.java`**
- [OK] **`MessageDAO.java`**
- [OK] **`ContactDAO.java`**

---

## 🟡 **DOIT ATTENDRE LES AUTRES** (Dépendances)

### **1. API SERVEUR** (`serveur/src/main/java/com/securephone/server/api/`)
- [ ] **`AuthServlet.java`** (Attend UI login de Hatsu)
- [ ] **`MessageServlet.java`** (Attend protocole de Tflow + UI de Hatsu)
- [ ] **`ContactServlet.java`** (Attend UI contacts de Hatsu)
- [ ] **`RoomServlet.java`** (Attend audio de Tflow + UI de Hatsu)

### **2. GESTION RÉSEAU** (`serveur/src/main/java/com/securephone/server/network/`)
- [ ] **`SocketManager.java`** (Coordination avec Tflow)
- [ ] **`PacketRouter.java`** (Attend protocole défini avec Tflow)

### **3. AUDIO UDP** (`serveur/src/main/java/com/securephone/server/udp/`)
- [ ] **`AudioPacketHandler.java`** (Attend format audio de Tflow)
- [ ] **`AudioServer.java`** (Coordination avec Tflow)
- [ ] **`RoomAudioManager.java`** (Coordination avec Tflow)

### **4. VIDÉO UDP** (`serveur/src/main/java/com/securephone/server/udp/`)
- [ ] **`VideoServer.java`** (Coordination avec Tflow pour format vidéo)
- [ ] **`VideoPacketHandler.java`** (Attend format vidéo de Tflow)
- [ ] **`RoomVideoManager.java`** (Coordination avec Tflow)

### **5. PUSH NOTIFICATIONS** (`serveur/src/main/java/com/securephone/server/push/`)
- [ ] **`FCMService.java`** (Attend UI notifications de Hatsu)
- [ ] **`DeviceTokenDAO.java`** (Dépend de modèle DeviceToken)
- [ ] **`PushNotification.java`** (Modèle de notification)

### **6. WEBSOCKET** (`serveur/src/main/java/com/securephone/server/websocket/`)
- [ ] **`ChatWebSocket.java`** (Attend UI chat de Hatsu)
- [ ] **`PresenceWebSocket.java`** (Attend UI présence de Hatsu)

### **7. MODÈLES PARTAGÉS** (`shared/src/main/java/com/securephone/shared/models/`)
- [ ] **`User.java`** (Synchronisation avec Tflow/Hatsu)
- [ ] **`Message.java`** (Synchronisation avec Tflow/Hatsu)
- [ ] **`Room.java`** (Synchronisation avec Tflow/Hatsu)

### **8. PROTOCOLE** (`shared/src/main/java/com/securephone/shared/protocol/`)
- [ ] **`MessageType.java`** (Réunion d'équipe nécessaire)
- [ ] **`PacketHeader.java`** (Coordination avec Tflow)
- [ ] **`ChatPacket.java`** (Coordination avec Tflow)
- [ ] **`AudioPacket.java`** (Coordination avec Tflow)
- [ ] **`VideoPacket.java`** (Coordination avec Tflow pour vidéo)

---

## 🔵 **FINALISATION** (Dernière étape)

### **1. SERVEUR PRINCIPAL**
- [ ] **`MainServer.java`** (Intégration finale)

### **2. TESTS**
- [ ] **Tests unitaires sécurité**
- [ ] **Tests d'intégration API**
- [ ] **Tests performance audio/vidéo**

### **3. SÉCURITÉ AVANCÉE**
- [ ] **Audit sécurité** (Phase finale)
- [ ] **Tests de pénétration** (Phase finale)

### **4. CONFIGURATION PRODUCTION**
- [ ] **Configuration TLS/SSL** (Déploiement)
- [ ] **Optimisation BDD** (Performance)
- [ ] **Configuration FCM** (Clés API Firebase)

---

## 📅 **ORDRE RECOMMANDÉ D'EXÉCUTION**

### **Jour 1-2 : Travail indépendant**
1. Créer les **modèles** (`User.java`, `Message.java`, etc.)
2. Implémenter **PasswordHasher.java** (bcrypt)
3. Implémenter **TOTPGenerator.java**
4. Créer **DatabaseManager.java** (SQLite/MySQL)
5. Implémenter **CryptoUtils.java** (AES, HMAC)

### **Jour 3 : Coordination avec Tflow**
1. Réunion pour définir **protocole** (MessageType.java, PacketHeader.java)
2. Synchroniser **modèles partagés**
3. Définir format **paquets audio/vidéo**

### **Jour 4 : Implémentation UDP**
1. **AudioServer.java** et **VideoServer.java** (après format défini)
2. **RoomAudioManager.java** et **RoomVideoManager.java**
3. **PacketRouter.java** (routage paquets)

### **Jour 5 : Implémentation API & WebSocket**
1. **AuthServlet.java** (login/register/logout)
2. **SessionManager.java** (gestion sessions)
3. **ChatWebSocket.java** et **PresenceWebSocket.java**

### **Jour 6 : Push Notifications**
1. **FCMService.java** (intégration Firebase)
2. **DeviceTokenDAO.java** (gestion tokens)
3. **PushNotification.java** (modèle)

### **Jour 7 : Tests et intégration**
1. Tests unitaires sécurité
2. Intégration avec base de données
3. Premier démo interne

---

## 🤝 **Dépendances critiques**

| Fichier | Dépend de | Statut |
|---------|-----------|---------|
| `AuthServlet.java` | `LoginFrame.java` (Hatsu) | 🟡 En attente |
| `ChatWebSocket.java` | `ChatFrame.java` (Hatsu) | 🟡 En attente |
| `AudioServer.java` | `AudioPacket.java` (Tflow) | 🟡 En attente |
| `VideoServer.java` | `VideoPacket.java` (Tflow) | 🟡 En attente |
| `FCMService.java` | `NotificationManager.java` (Hatsu) | 🟡 En attente |

---
