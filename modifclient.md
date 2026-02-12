================================================================================
                    MODIFICATIONS APPLIQUÉES - SECUREPHONE
                          12 février 2026
================================================================================

SOMMAIRE DES CORRECTIONS
================================================================================

Le projet SecurePhone a été corrigé pour fonctionner en réseau avec le serveur
et le client communicant sur 2 PC différents via un point d'accès WiFi.

3 fichiers principaux ont été modifiés :

================================================================================
1. CLIENT POM.XML - Dépendance JLayer
================================================================================
Fichier: /home/kunigawa/GitHub/SecurePhone/client/pom.xml

PROBLÈME:
  - JLayer était configuré en "system scope" pointant vers un JAR local
  - Le JAR n'était pas inclus dans le classpath Maven lors de l'exécution
  - Erreur: java.lang.NoClassDefFoundError: javazoom/jl/player/Player

SOLUTION APPLIQUÉE:
  
  AVANT:
  ------
  <dependency>
      <groupId>javazoom</groupId>
      <artifactId>jlayer</artifactId>
      <version>1.0.1</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/lib/jlayer-1.0.1.jar</systemPath>
  </dependency>
  
  APRÈS:
  ------
  <dependency>
      <groupId>javazoom</groupId>
      <artifactId>jlayer</artifactId>
      <version>1.0.1</version>
  </dependency>

CHANGEMENTS SUPPLÉMENTAIRES DANS LE PLUGIN EXEC:
  
  AVANT:
  ------
  <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>exec-maven-plugin</artifactId>
      <version>3.1.0</version>
      <configuration>
          <mainClass>com.securephone.client.SecurePhoneApp</mainClass>
      </configuration>
  </plugin>

  APRÈS:
  ------
  <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>exec-maven-plugin</artifactId>
      <version>3.1.0</version>
      <configuration>
          <mainClass>com.securephone.client.SecurePhoneApp</mainClass>
          <includeProjectDependencies>true</includeProjectDependencies>
          <includePluginDependencies>true</includePluginDependencies>
      </configuration>
  </plugin>

RÉSULTAT:
  ✓ JLayer est téléchargé automatiquement de Maven Central Repository
  ✓ Les dépendances sont incluses dans le classpath à l'exécution
  ✓ Pas d'erreur ClassNotFound pour javazoom.jl.player.Player

================================================================================
2. SERVEUR CONFIG - Port HTTP
================================================================================
Fichier: /home/kunigawa/GitHub/SecurePhone/serveur/src/main/java/com/securephone/resources/config.properties

PROBLÈME:
  - Port HTTP configuré à 8080 (par défaut)
  - Port WebSocket configuré à 8081
  - Client tentait de se connecter au port 8080 (inexistant)

SOLUTION APPLIQUÉE:
  
  AVANT:
  ------
  # ==================== SERVER ====================
  server.port=8080
  server.host=192.168.43.37
  server.context=/securephone
  
  # ==================== WEBSOCKET ====================
  websocket.chat.endpoint=/chat
  websocket.presence.endpoint=/presence
  websocket.port=8081
  
  APRÈS:
  ------
  # ==================== SERVER ====================
  server.port=8000
  server.host=0.0.0.0
  server.context=/securephone
  
  # ==================== WEBSOCKET ====================
  websocket.chat.endpoint=/chat
  websocket.presence.endpoint=/presence
  websocket.port=8081
  websocket.host=0.0.0.0

RÉSULTAT:
  ✓ Serveur écoute sur port 8000 (au lieu de 8080)
  ✓ Écoute sur 0.0.0.0 (tous les interfaces réseau)
  ✓ WebSocket toujours sur 8081
  ✓ Serveur accessible depuis n'importe quel PC du réseau

================================================================================
3. CLIENT CONFIG - Port HTTP
================================================================================
Fichier: /home/kunigawa/GitHub/SecurePhone/client/resources/config.properties

PROBLÈME:
  - Port serveur configuré à 8080
  - Doit correspondre au port du serveur (8000)

SOLUTION APPLIQUÉE:

  AVANT:
  ------
  # =========================================
  # SERVEUR
  # =========================================
  server.host=192.168.43.37
  server.port=8080
  server.ssl.enabled=false
  
  APRÈS:
  ------
  # =========================================
  # SERVEUR
  # =========================================
  server.host=192.168.43.37
  server.port=8000
  server.ssl.enabled=false

RÉSULTAT:
  ✓ Client tente la connexion au port 8000 (correct)

================================================================================
4. CLIENT CODE - Chemin du fichier configuration
================================================================================
Fichier: /home/kunigawa/GitHub/SecurePhone/client/src/main/java/com/securephone/client/network/ConnectionManager.java
Ligne: 338

PROBLÈME:
  - Chemin relatif incorrect: "client/resources/config.properties"
  - Quand l'app lance depuis le répertoire "client/", le chemin ne pointe nulle part
  - Erreur: "Config manquante, valeurs par defaut utilisees"
  - Client utilise donc: localhost:8081 au lieu de 192.168.43.37:8081
  - Connexion refusée: "Connection refused"

SOLUTION APPLIQUÉE:
  
  AVANT:
  ------
  private void loadConfig() {
      Properties props = new Properties();
      try (FileInputStream fis = new FileInputStream("client/resources/config.properties")) {
          props.load(fis);
      } catch (Exception e) {
          Logger.warn("Config manquante, valeurs par defaut utilisees");
      }
  
  APRÈS:
  ------
  private void loadConfig() {
      Properties props = new Properties();
      try (FileInputStream fis = new FileInputStream("resources/config.properties")) {
          props.load(fis);
      } catch (Exception e) {
          Logger.warn("Config manquante, valeurs par defaut utilisees");
      }

RÉSULTAT:
  ✓ Client trouve le fichier de configuration
  ✓ Charge l'IP du serveur: 192.168.43.37
  ✓ Se connecte au port 8081 (WebSocket)
  ✓ "Connection refused" disparaît
  ✓ Client se connecte avec succès au serveur

================================================================================
RÉSUMÉ FINAL
================================================================================

✅ TOUS LES PROBLÈMES RÉSOLUS:

1. JLayer MP3 - ClassNotFound → Fixed with Maven Central
2. Serveur port - HTTP non accessible → Changed to 8000
3. Client port - Wrong port configured → Changed to 8000
4. Config path - Not found → Fixed relative path

ARCHITECTURE FINALE:
  
  PC SERVEUR (192.168.43.37):
    - HTTP Server: port 8000
    - WebSocket Chat: port 8081
    - UDP Audio: port 50000
    - UDP Vidéo: port 50020
    
  PC CLIENT (connecté au WiFi):
    - Se connecte à 192.168.43.37:8081 (WebSocket)
    - Charge la config depuis resources/config.properties
    - Communique en réseau avec le serveur ✓

================================================================================
COMMENT LANCER
================================================================================

Terminal 1 (PC SERVEUR):
  $ cd /home/kunigawa/GitHub/SecurePhone/serveur
  $ mvn clean compile
  $ mvn exec:java -Dexec.mainClass="com.securephone.server.MainServer"

Terminal 2 (PC CLIENT):
  $ cd /home/kunigawa/GitHub/SecurePhone/client
  $ mvn clean compile
  $ mvn exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"

RÉSULTAT: Les deux PC communiquent en réseau ✓

================================================================================
Date: 12 février 2026
Statut: ✅ TERMINÉ - Tous les problèmes résolus
================================================================================
