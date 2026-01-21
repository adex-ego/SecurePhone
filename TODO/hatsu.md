# 📋 **LISTE DES FICHIERS À ÉDITER POUR HATSU**

## 🟢 **PEUT FAIRE IMMÉDIATEMENT** (Sans dépendance)

### **1. DESIGN & ASSETS** (`client/resources/`)
- [ ] **`images/`** *Hatsu seule* (icônes, logos, backgrounds)
- [ ] **`sounds/`** *Hatsu seule* (sons interface)

### **2. UTILITAIRES UI** (`client/src/main/java/com/securephone/client/utils/`)
- [ ] **`SoundPlayer.java`** *Hatsu seule*
- [ ] **`NotificationManager.java`** *Hatsu seule*

### **3. GESTION UI** (`client/src/main/java/com/securephone/client/ui/`)
- [ ] **`UIManager.java`** *Hatsu seule* (thèmes, styles)

### **4. CONFIGURATION CLIENT**
- [ ] **`client_config.json`** *Hatsu seule*
- [ ] **`config.properties`** *Hatsu seule* (client side)

---

## 🟡 **DOIT ATTENDRE LES AUTRES** (Dépendances)

### **1. FRAMES UI** (`client/src/main/java/com/securephone/client/ui/frames/`)
- [ ] **`LoginFrame.java`** 🟡 *Attend AuthServlet.java de Hailey*
- [ ] **`ChatFrame.java`** 🟡 *Attend ChatWebSocket.java de Hailey + protocole Tflow*
- [ ] **`ContactFrame.java`** 🟡 *Attend ContactServlet.java de Hailey*
- [ ] **`MainFrame.java`** 🟡 *Intégration finale*
- [ ] **`SettingsFrame.java`** 🟡 *Attend API configuration de Hailey*

### **2. COMPOSANTS UI** (`client/src/main/java/com/securephone/client/ui/components/`)
- [ ] **`MessageBubble.java`** 🟡 *Attend format Message.java défini*
- [ ] **`ContactList.java`** 🟡 *Attend Contact.java modèle*
- [ ] **`PTTButton.java`** 🟡 *Coordination avec Tflow pour fonctionnalité*
- [ ] **`AudioControls.java`** 🟡 *Coordination avec Tflow pour API audio*

### **3. MODÈLES CLIENT** (`client/src/main/java/com/securephone/client/models/`)
- [ ] **`ChatMessage.java`** 🟡 *Synchronisation avec modèle partagé*
- [ ] **`Contact.java`** 🟡 *Synchronisation avec modèle partagé*

### **4. WEB PUSH** (`client/src/main/java/com/securephone/client/webpush/`)
- [ ] **`PushManager.java`** 🟡 *Attend FCMService.java de Hailey*
- [ ] **`PushClient.java`** 🟡 *Attend API push de Hailey*
- [ ] **`ServiceWorker.java`** 🟡 *Si web app*

### **5. RÉSEAU CLIENT** (`client/src/main/java/com/securephone/client/network/`)
- [ ] **`ApiClient.java`** 🟡 *Attend API REST de Hailey*

---

## 🔵 **FINALISATION** (Dernière étape)

### **1. UX & ACCESSIBILITÉ**
- [ ] **Raccourcis clavier** 🔵 *Phase finale*
- [ ] **Thème sombre/clair** 🔵 *Phase finale*
- [ ] **Support accessibilité** 🔵 *Phase finale*

### **2. TESTS UTILISATEUR**
- [ ] **Tests d'utilisabilité** 🔵 *Phase finale*
- [ ] **Feedback UI** 🔵 *Phase finale*
- [ ] **Documentation utilisateur** 🔵 *Phase finale*

---

## 📅 **ORDRE RECOMMANDÉ D'EXÉCUTION**

### **Jour 1-2 : Design et assets**
1. Créer **assets graphiques** (images/, icônes)
2. Implémenter **`SoundPlayer.java`** (sons interface)
3. Créer **`UIManager.java`** (système de thèmes)
4. Préparer **maquettes UI** (wireframes)

### **Jour 3 : Coordination avec Hailey**
1. Définir **flux d'authentification** (LoginFrame → AuthServlet)
2. Synchroniser **modèles de données** (Message, Contact)
3. Valider **endpoints API** pour UI

### **Jour 4 : Coordination avec Tflow**
1. Définir **interface audio/vidéo** (PTTButton, AudioControls)
2. Synchroniser **indicateurs réseau**
3. Valider **protocole messages** pour ChatFrame

### **Jour 5 : Implémentation UI texte**
1. **`LoginFrame.java`** (après API auth)
2. **`ChatFrame.java`** (après WebSocket)
3. **`MessageBubble.java`** et **`ContactList.java`**

### **Jour 6 : Implémentation UI audio/vidéo**
1. **`PTTButton.java`** (après API audio Tflow)
2. **`AudioControls.java`** (controles audio/vidéo)
3. **`MainFrame.java`** (intégration)

### **Jour 7 : Notifications & finitions**
1. **`PushManager.java`** (après FCM Hailey)
2. Raccourcis clavier et thèmes
3. Tests utilisateur et documentation

---

## 🤝 **Dépendances critiques**

| Fichier | Dépend de | Statut |
|---------|-----------|---------|
| `LoginFrame.java` | `AuthServlet.java` (Hailey) | 🟡 En attente |
| `ChatFrame.java` | `ChatWebSocket.java` (Hailey) + protocole (Tflow) | 🟡 En attente |
| `PTTButton.java` | `AudioClient.java` (Tflow) | 🟡 En attente |
| `PushManager.java` | `FCMService.java` (Hailey) | 🟡 En attente |
| `ApiClient.java` | API REST (Hailey) | 🟡 En attente |

---

## 💡 **Conseil pour Hatsu**
**Commence par les fichiers 🟢 "Peut faire immédiatement"** :
1. Les assets graphiques et sons
2. Le système de thèmes (`UIManager.java`)
3. La configuration client

Cela représente **40% de ton travail** et ne dépend de personne. Pendant que Tflow et Hailey travaillent sur leurs parties, tu auras tous les assets et l'infrastructure UI prête.