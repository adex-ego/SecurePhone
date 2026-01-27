# 📋 **LISTE DES FICHIERS À ÉDITER POUR TFLOW**

## 🟢 **PEUT FAIRE IMMÉDIATEMENT** (Sans dépendance)

### **1. CLIENT AUDIO** (`client/src/main/java/com/securephone/client/audio/`)
- [ok] **`AudioCapture.java`** *Tflow seule*
- [ok] **`AudioPlayer.java`** *Tflow seule*
- [ok] **`AudioBuffer.java`** *Tflow seule*
- [ok] **`OpusCodec.java`** *Tflow seule*

### **2. CLIENT VIDÉO** (`client/src/main/java/com/securephone/client/video/`)
- [ok] **`VideoCapture.java`** *Tflow seule*
- [ok] **`VideoPlayer.java`** *Tflow seule*
- [ok] **`H264Codec.java`** *Tflow seule*

### **3. MODÈLES CLIENT** (`client/src/main/java/com/securephone/client/models/`)
- [ok] **`AudioDevice.java`** *Tflow seule*
- [ok] **`UserSession.java`** *Tflow seule*

---

## 🟡 **DOIT ATTENDRE LES AUTRES** (Dépendances)

### **1. CLIENT RÉSEAU** (`client/src/main/java/com/securephone/client/network/`)
- [ ] **`AudioClient.java`** 🟡 *Attend AudioPacket.java défini avec Hailey*
- [ ] **`VideoClient.java`** 🟡 *Attend VideoPacket.java défini avec Hailey*
- [ ] **`WebSocketClient.java`** 🟡 *Attend protocole défini avec Hailey*
- [ ] **`ConnectionManager.java`** 🟡 *Attend SocketManager.java de Hailey*

### **2. COMPOSANTS UI AUDIO** (`client/src/main/java/com/securephone/client/ui/components/`)
- [ ] **`PTTButton.java`** 🟡 *Coordination avec Hatsu pour design*
- [ ] **`AudioControls.java`** 🟡 *Coordination avec Hatsu pour UI*

### **3. PROTOCOLE PARTAGÉ** (`shared/src/main/java/com/securephone/shared/protocol/`)
- [ ] **`AudioPacket.java`** 🟡 *Réunion d'équipe nécessaire*
- [ ] **`VideoPacket.java`** 🟡 *Réunion d'équipe nécessaire*
- [ ] **`ChatPacket.java`** 🟡 *Réunion d'équipe nécessaire*
- [ ] **`MessageType.java`** 🟡 *Réunion d'équipe nécessaire*
- [ ] **`PacketHeader.java`** 🟡 *Réunion d'équipe nécessaire*

### **4. MODÈLES PARTAGÉS** (`shared/src/main/java/com/securephone/shared/models/`)
- [ ] **`Message.java`** 🟡 *Synchronisation avec Hailey/Hatsu*
- [ ] **`Room.java`** 🟡 *Synchronisation avec Hailey/Hatsu*
- [ ] **`User.java`** 🟡 *Synchronisation avec Hailey/Hatsu*

---

## 🔵 **FINALISATION** (Dernière étape)

### **1. CLIENT PRINCIPAL**
- [ ] **`MainClient.java`** 🔵 *Intégration finale*

### **2. TESTS AUDIO/VIDÉO**
- [ ] **Tests latence audio** 🔵 *Phase finale*
- [ ] **Tests qualité vidéo** 🔵 *Phase finale*
- [ ] **Tests bande passante** 🔵 *Phase finale*

### **3. OPTIMISATION**
- [ ] **Adaptive bitrate** 🔵 *Performance*
- [ ] **Jitter buffers** 🔵 *Qualité*
- [ ] **Compression optimale** 🔵 *Bande passante*

---

## 📅 **ORDRE RECOMMANDÉ D'EXÉCUTION**

### **Jour 1-2 : Travail indépendant**
1. Implémenter **`AudioCapture.java`** et **`AudioPlayer.java`**
2. Implémenter **`OpusCodec.java`** (encodage/décodage)
3. Implémenter **`VideoCapture.java`** et **`VideoPlayer.java`**
4. Implémenter **`H264Codec.java`** (codec vidéo)

### **Jour 3 : Coordination avec Hailey**
1. Réunion pour définir **protocole** (AudioPacket.java, VideoPacket.java)
2. Synchroniser **modèles partagés**
3. Définir **ports UDP** et formats

### **Jour 4 : Implémentation réseau**
1. **`AudioClient.java`** (après protocole défini)
2. **`VideoClient.java`** (après protocole défini)
3. **`WebSocketClient.java`** (après protocole défini)

### **Jour 5 : Coordination avec Hatsu**
1. **`PTTButton.java`** (intégration UI)
2. **`AudioControls.java`** (controles audio)
3. **Indicateurs qualité réseau** (UI)

### **Jour 6 : Intégration client**
1. **`ConnectionManager.java`** (gestion connexions)
2. **`UserSession.java`** (état session)
3. **`MainClient.java`** (intégration)

### **Jour 7 : Tests et optimisation**
1. Tests performance audio/vidéo
2. Optimisation latence
3. Tests multi-utilisateurs

---

## 🤝 **Dépendances critiques**

| Fichier | Dépend de | Statut |
|---------|-----------|---------|
| `AudioClient.java` | `AudioPacket.java` (protocole) | 🟡 En attente |
| `VideoClient.java` | `VideoPacket.java` (protocole) | 🟡 En attente |
| `WebSocketClient.java` | `ChatPacket.java` (protocole) | 🟡 En attente |
| `PTTButton.java` | `AudioControls.java` + UI Hatsu | 🟡 Coordination nécessaire |
| `ConnectionManager.java` | `SocketManager.java` (Hailey) | 🟡 En attente |

---

## 💡 **Conseil pour Tflow**
**Commence par les fichiers 🟢 "Peut faire immédiatement"** :
1. Les composants audio/vidéo de capture et lecture
2. Les codecs Opus et H264
3. Les modèles de périphériques

Cela représente **50% de ton travail** et ne dépend de personne. Pendant que Hailey et Hatsu travaillent sur leurs parties, tu auras les composants média prêts.