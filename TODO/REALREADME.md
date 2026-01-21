# 📱 **SECUREPHONE - Application de Communication Sécurisée**

## 🎯 **RÉSUMÉ DU PROJET**

**SecurePhone** est une application de communication temps-réel sécurisée combinant :
- 🎤 **Talkie-walkie audio** (PTT - Push-To-Talk)
- 📹 **Vidéoconférence** en temps réel
- 💬 **Messagerie texte chiffrée**
- 🔐 **Authentification à deux facteurs (A2F)**
- 📱 **Notifications push**

**Stack technique** : Java Swing (client) + Tomcat/JSP (serveur) + MySQL + WebSocket/UDP

---

## 🏗️ **ARCHITECTURE GÉNÉRALE**

```
SecurePhone/
├── CLIENT (Swing)
│   ├── Interface utilisateur complète
│   ├── Capture audio/vidéo
│   ├── Codecs Opus/H264
│   └── Communication réseau
│
├── SERVEUR (Tomcat)
│   ├── API REST (Servlets)
│   ├── WebSocket pour chat
│   ├── Serveurs UDP audio/vidéo
│   └── Gestion sessions/BBD
│
└── SHARED
    ├── Modèles communs
    └── Protocole réseau
```

---

## 🔄 **WORKFLOW COMPLET DE L'APPLICATION**

### **1. INSCRIPTION & CONFIGURATION A2F**
```
┌─────────┐     ┌─────────┐     ┌─────────────┐
│  Client │     │ Serveur │     │  Console    │
│  Swing  │     │ Tomcat  │     │   Tomcat    │
└────┬────┘     └────┬────┘     └──────┬──────┘
     │ 1. Register    │                 │
     │───────────────>│                 │
     │                │                 │
     │                │ 2. Crée user    │
     │                │ + secret A2F    │
     │                │                 │
     │ 3. Success     │                 │
     │<───────────────│                 │
     │                │                 │
     │                │                 │ 4. Affiche QR
     │                │                 │    code/secret
     │                │                 │    (console)
     │                │                 │
```

### **2. CONNEXION AVEC A2F**
```
┌─────────┐     ┌─────────┐     ┌─────────────┐
│  Client │     │ Serveur │     │  Console    │
│  Swing  │     │ Tomcat  │     │   Tomcat    │
└────┬────┘     └────┬────┘     └──────┬──────┘
     │ 1. Login       │                 │
     │ (user/pass)    │                 │
     │───────────────>│                 │
     │                │                 │
     │                │ 2. Valide creds │
     │                │ Génère code A2F │
     │                │                 │
     │                │                 │ 3. Affiche code
     │                │                 │  ╔══════════╗
     │                │                 │  ║ Code:1234║
     │                │                 │  ╚══════════╝
     │ 4. Demande     │                 │
     │    code A2F    │                 │
     │<───────────────│                 │
     │                │                 │
     │ 5. Envoie code │                 │
     │───────────────>│                 │
     │                │                 │
     │                │ 6. Vérifie code │
     │                │ Crée session    │
     │                │                 │
     │ 7. Token session│                │
     │<───────────────│                 │
     │                │                 │
```

### **3. MESSAGERIE TEXTE**
```
┌─────────┐     ┌─────────┐     ┌─────────┐
│ Client A│     │ Serveur │     │ Client B│
│         │     │         │     │         │
└────┬────┘     └────┬────┘     └────┬────┘
     │ 1. Message    │                │
     │───────────────>│                │
     │                │                │
     │                │ 2. Chiffre &   │
     │                │    stocke BDD  │
     │                │                │
     │                │ 3. Envoie via  │
     │                │    WebSocket   │
     │                │───────────────>│
     │                │                │
     │                │                │ 4. Déchiffre
     │                │                │    & affiche
     │                │                │
     │ 5. ACK        │                │
     │<───────────────│                │
```

### **4. APPEL AUDIO/VIDÉO (PTT)**
```
┌─────────┐     ┌─────────┐     ┌─────────┐
│ Client A│     │ Serveur │     │ Client B│
│         │     │  UDP    │     │         │
└────┬────┘     └────┬────┘     └────┬────┘
     │                │                │
     │ 1. Appui PTT   │                │
     │───────────────>│                │
     │                │                │
     │                │ 2. Notifie B   │
     │                │───────────────>│
     │                │                │
     │                │                │ 3. Accepte appel
     │                │<───────────────│
     │                │                │
     │ 4. Start stream│                │
     │═══════════════>│════════════════>│
     │   (UDP audio)  │                │
     │                │                │
     │ 5. Relâche PTT │                │
     │───────────────>│                │
     │                │                │
```

---

## 📊 **FONCTIONNALITÉS DÉTAILLÉES**

### **🔐 SÉCURITÉ**
- **Authentification** : Login/mot de passe
- **A2F** : Code à 4 chiffres affiché console serveur
- **Chiffrement** : AES pour messages texte
- **Sessions** : Gestion avec tokens JWT-like
- **Base de données** : MySQL avec hash bcrypt

### **🎤 AUDIO**
- **Codec** : Opus (compression efficace)
- **Transport** : UDP pour faible latence
- **PTT** : Bouton Push-To-Talk
- **Qualité** : Adaptive bitrate
- **Périphériques** : Sélection micro/casque

### **📹 VIDÉO**
- **Codec** : H264/VP8
- **Résolution** : Adaptative (320p-720p)
- **FPS** : 15-30 images/seconde
- **Transport** : UDP avec correction erreurs

### **💬 MESSAGERIE**
- **Texte** : Messages instantanés
- **Groupes** : Salles de discussion
- **Historique** : Stockage BDD
- **Notifications** : Popups + sons
- **Statuts** : En ligne/hors ligne/occupé

### **📱 NOTIFICATIONS**
- **Desktop** : Popups système
- **Sons** : Différents par type
- **Push** : Firebase Cloud Messaging (optionnel)
- **Présence** : Mise à jour automatique

---

## 🗂️ **STRUCTURE DES DONNÉES**

### **Base de données (MySQL)**
```sql
-- Tables principales
users (id, username, password_hash, email, totp_secret, status)
messages (id, sender_id, receiver_id, content, timestamp, encrypted)
contacts (user_id, contact_id, nickname)
device_tokens (user_id, token, platform) -- Pour push

-- Tables optionnelles
rooms (id, name, creator_id)
room_members (room_id, user_id)
calls (id, caller_id, receiver_id, start_time, end_time, type)
```

### **Fichiers de configuration**
```
├── client/
│   ├── config.properties
│   └── client_config.json
│
├── serveur/
│   ├── config.properties
│   ├── log4j2.xml
│   └── totp_secrets.key
│
└── db/
    ├── tables.sql
    └── data.sql
```

---

## 🔧 **INSTALLATION & DÉPLOIEMENT**

### **Prérequis**
```bash
# Client
- Java 11+
- Bibliothèques audio (PortAudio/JavaSound)
- Libs vidéo (JavaCV/FFmpeg)

# Serveur
- Tomcat 9+
- MySQL 5.7+
- Java 11+
```

### **Installation rapide**
```bash
1. Cloner le projet
2. Importer BDD: mysql < db/tables.sql
3. Données test: mysql < db/data.sql
4. Déployer serveur sur Tomcat
5. Lancer client: java -jar securephone-client.jar
```

---

## 🚀 **SCÉNARIOS D'UTILISATION**

### **Cas 1 : Communication d'équipe**
```
1. Alice se connecte avec A2F
2. Rejoint la salle "Projet-X"
3. Appuie sur PTT pour parler à l'équipe
4. Envoie un document dans le chat
5. Démarre une vidéoconférence
```

### **Cas 2 : Appel urgent**
```
1. Bob reçoit notification d'appel
2. Accepte l'appel audio
3. Passe en mode vidéo si besoin
4. Partage son écran (optionnel)
5. Termine l'appel, garde historique
```

### **Cas 3 : Mode discret**
```
1. Met son statut "Ne pas déranger"
2. Reçoit messages en silencieux
3. Consulte historique hors ligne
4. Répond quand disponible
```

---

## ⚙️ **CONFIGURATION AVANCÉE**

### **Paramètres audio**
```properties
# config.properties
audio.sample_rate=48000
audio.bitrate=64000
audio.channels=1
audio.buffer_size=960
audio.codec=opus
```

### **Paramètres réseau**
```properties
server.host=localhost
server.port=8080
websocket.port=8081
audio.udp.port=50000-50010
video.udp.port=50020-50030
```

### **Sécurité**
```properties
encryption.algorithm=AES/CBC/PKCS5Padding
session.timeout=3600
2fa.enabled=true
2fa.method=console  # console/email/sms
```

---

## 🧪 **TESTS & DÉMO**

### **Tests à effectuer**
```bash
# Test unitaires
- Authentification avec A2F
- Chiffrement/déchiffrement
- Codecs audio/vidéo
- Performance réseau

# Tests d'intégration
- 2 clients + 1 serveur
- Appel audio PTT
- Chat texte groupe
- Notifications push

# Tests de charge
- 10 utilisateurs simultanés
- Bandwidth monitoring
- Latence audio/vidéo
```

### **Script de démo**
```bash
./demo.sh
# 1. Démarre serveur
# 2. Lance 2 clients
# 3. Simule conversation
# 4. Génère rapport
```

---

## 📈 **ROADMAP & ÉVOLUTIONS**

### **Phase 1 (Basique)**
- ✅ Authentification simple
- ✅ Chat texte
- ✅ Audio PTT basique
- ✅ Interface Swing

### **Phase 2 (Avancé)**
- ✅ A2F par console
- ✅ Vidéo 1-to-1
- ✅ Notifications desktop
- ✅ Gestion contacts

### **Phase 3 (Production)**
- 🔄 A2F par email/SMS
- 🔄 Vidéoconférence groupe
- 🔄 Partage d'écran
- 🔄 Chiffrement bout-à-bout

### **Phase 4 (Enterprise)**
- 🔄 Audit logs
- 🔄 Support LDAP/AD
- 🔄 API REST externe
- 🔄 Dashboard admin

---

## 🛠️ **DÉPANNAGE RAPIDE**

### **Problèmes courants**
```
1. A2F non affiché → Vérifier console Tomcat
2. Audio coupé → Augmenter buffer_size
3. Vidéo lag → Réduire résolution
4. Connexion perdue → Vérifier firewall UDP
```

### **Logs importants**
```bash
# Serveur
tail -f catalina.out | grep SECUREPHONE

# Client
java -jar client.jar --debug

# Base de données
mysql> SELECT * FROM messages ORDER BY timestamp DESC LIMIT 10;
```

---

## 📚 **DOCUMENTATION TECHNIQUE**

### **API Endpoints**
```
GET    /api/auth/status
POST   /api/auth/login
POST   /api/auth/register
POST   /api/auth/request-2fa
POST   /api/auth/verify-2fa
GET    /api/contacts
POST   /api/messages
GET    /api/messages/{userId}
DELETE /api/sessions/{token}
```

### **WebSocket Events**
```json
{
  "type": "message",
  "from": "alice",
  "to": "bob",
  "content": "Hello",
  "timestamp": 1234567890
}
```

### **Packet UDP Audio**
```
[Header:4 bytes][Sequence:4 bytes][Timestamp:8 bytes][Audio data:...]
```

---

## 👥 **ÉQUIPE & RÔLES**

### **Tflow** - Expert Audio/Réseau
- Architecture réseau TCP/UDP
- Codecs audio/vidéo
- Performance temps-réel
- Gestion buffers

### **Hailey** - Expert Sécurité/Backend
- Authentification A2F
- Chiffrement messages
- Base de données
- API Serveur

### **Hatsu** - Expert UI/Client
- Interface Swing
- UX/Design
- Notifications
- Tests utilisateur

---

## 🎓 **POUR LES CORRECTEURS**

### **Points clés à évaluer**
```
✓ Architecture client/serveur propre
✓ Communication temps-réel (audio/vidéo)
✓ Sécurité (A2F + chiffrement)
✓ Gestion erreurs réseau
✓ Expérience utilisateur
✓ Documentation complète
```

### **Données de test**
```
Utilisateurs: alice / bob / charlie
Mot de passe: password123
Code A2F: affiché console serveur
Email: [any]@test.com
```

---

## 📞 **SUPPORT**

### **En cas de problème**
1. Consulter `README.md`
2. Vérifier logs (`logs/` directory)
3. Tester connexion réseau
4. Réinitialiser configuration

### **Contact projet**
- Repository: `https://github.com/[team]/securephone`
- Documentation: `docs/` folder
- Issues: GitHub Issues tracker

---

**SecurePhone** - Communication sécurisée pour équipes exigeantes  
*Projet académique - Systèmes Répartis & Sécurité*