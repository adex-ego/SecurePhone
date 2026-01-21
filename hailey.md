# 📋 **LISTE DES FICHIERS À ÉDITER POUR HAILEY**

## 🟢 **PEUT FAIRE IMMÉDIATEMENT** (Sans dépendance)

### **1. MODÈLES DE DONNÉES** (`serveur/src/main/java/com/securephone/server/models/`)
- [ ] **`User.java`** ✅ *Hailey seule*
- [ ] **`Message.java`** ✅ *Hailey seule*
- [ ] **`Contact.java`** ✅ *Hailey seule*
- [ ] **`Room.java`** ✅ *Hailey seule*
- [ ] **`AudioStream.java`** ✅ *Coordination avec Tflow*

### **2. SÉCURITÉ - UTILITAIRES** (`serveur/src/main/java/com/securephone/server/security/`)
- [ ] **`PasswordHasher.java`** ✅ *Hailey seule*
- [ ] **`TOTPGenerator.java`** ✅ *Hailey seule*
- [ ] **`CryptoUtils.java`** ✅ *Hailey seule*
- [ ] **`SessionManager.java`** ✅ *Hailey seule*

### **3. CONFIGURATION** (`serveur/src/main/java/com/securephone/resources/`)
- [ ] **`config.properties`** ✅ *Hailey seule*
- [ ] **`log4j2.xml`** ✅ *Hailey seule*
- [ ] **`totp_secrets.key`** ✅ *Hailey seule* (template vide)

### **4. BASE DE DONNÉES** (`serveur/src/main/java/com/securephone/server/database/`)
- [ ] **`DatabaseManager.java`** ✅ *Hailey seule*
- [ ] **`UserDAO.java`** ✅ *Hailey seule* (dépend des modèles)
- [ ] **`MessageDAO.java`** ✅ *Hailey seule* (dépend des modèles)
- [ ] **`ContactDAO.java`** ✅ *Hailey seule* (dépend des modèles)

---

## 🟡 **DOIT ATTENDRE LES AUTRES** (Dépendances)

### **1. API SERVEUR** (`serveur/src/main/java/com/securephone/server/api/`)
- [ ] **`AuthServlet.java`** 🟡 *Attend UI login de Hatsu*
- [ ] **`MessageServlet.java`** 🟡 *Attend protocole de Tflow + UI de Hatsu*
- [ ] **`ContactServlet.java`** 🟡 *Attend UI contacts de Hatsu*
- [ ] **`RoomServlet.java`** 🟡 *Attend audio de Tflow + UI de Hatsu*

### **2. GESTION RÉSEAU** (`serveur/src/main/java/com/securephone/server/network/`)
- [ ] **`SocketManager.java`** 🟡 *Coordination avec Tflow*
- [ ] **`PacketRouter.java`** 🟡 *Attend protocole défini avec Tflow*

### **3. AUDIO UDP** (`serveur/src/main/java/com/securephone/server/udp/`)
- [ ] **`AudioPacketHandler.java`** 🟡 *Attend format audio de Tflow*
- [ ] **`AudioServer.java`** 🟡 *Coordination avec Tflow*
- [ ] **`RoomAudioManager.java`** 🟡 *Coordination avec Tflow*

### **4. WEBSOCKET** (`serveur/src/main/java/com/securephone/server/websocket/`)
- [ ] **`ChatWebSocket.java`** 🟡 *Attend UI chat de Hatsu*
- [ ] **`PresenceWebSocket.java`** 🟡 *Attend UI présence de Hatsu*

### **5. MODÈLES PARTAGÉS** (`shared/src/main/java/com/securephone/shared/models/`)
- [ ] **`User.java`** 🟡 *Synchronisation avec Tflow/Hatsu*
- [ ] **`Message.java`** 🟡 *Synchronisation avec Tflow/Hatsu*
- [ ] **`Room.java`** 🟡 *Synchronisation avec Tflow/Hatsu*

### **6. PROTOCOLE** (`shared/src/main/java/com/securephone/shared/protocol/`)
- [ ] **`MessageType.java`** 🟡 *Réunion d'équipe nécessaire*
- [ ] **`PacketHeader.java`** 🟡 *Coordination avec Tflow*
- [ ] **`ChatPacket.java`** 🟡 *Coordination avec Tflow*
- [ ] **`AudioPacket.java`** 🟡 *Coordination avec Tflow*

---

## 🔵 **FINALISATION** (Dernière étape)

### **1. SERVEUR PRINCIPAL**
- [ ] **`MainServer.java`** 🔵 *Intégration finale*

### **2. TESTS**
- [ ] **Tests unitaires** 🔵 *Après implémentation*
- [ ] **Tests d'intégration** 🔵 *Avec client complet*

### **3. SÉCURITÉ AVANCÉE**
- [ ] **Audit sécurité** 🔵 *Phase finale*
- [ ] **Tests de pénétration** 🔵 *Phase finale*

### **4. CONFIGURATION PRODUCTION**
- [ ] **Configuration TLS** 🔵 *Déploiement*
- [ ] **Optimisation BDD** 🔵 *Performance*

---

## 📅 **ORDRE RECOMMANDÉ D'EXÉCUTION**

### **Jour 1-2 : Travail indépendant**
1. Créer les **modèles** (`User.java`, `Message.java`, etc.)
2. Implémenter **PasswordHasher.java** (bcrypt)
3. Implémenter **TOTPGenerator.java**
4. Créer **DatabaseManager.java** (SQLite)
5. Implémenter **CryptoUtils.java** (AES, HMAC)

### **Jour 3 : Coordination avec Tflow**
1. Réunion pour définir **protocole** (MessageType.java, PacketHeader.java)
2. Synchroniser **modèles partagés**
3. Définir format **paquets audio**

### **Jour 4 : Coordination avec Hatsu**
1. Définir **flux UI** d'authentification
2. Synchroniser **endpoints API**
3. Valider **format des données**

### **Jour 5-6 : Implémentation API**
1. **AuthServlet.java** (login/register/logout)
2. **SessionManager.java** (gestion sessions)
3. **MessageServlet.java** (si protocole défini)

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
| `MessageDAO.java` | `Message.java` (modèle) | 🟢 Prêt |
| `PacketRouter.java` | `MessageType.java` (protocole) | 🟡 Réunion nécessaire |

---

## 💡 **Conseil pour Hailey**
**Commence par les fichiers 🟢 "Peut faire immédiatement"** :
1. Les modèles de données
2. Les utilitaires de sécurité (hash, TOTP, crypto)
3. La configuration et la base de données

Cela représente **70% de ton travail** et ne dépend de personne. Pendant que Tflow et Hatsu travaillent sur leurs parties, tu auras une base solide déjà prête.