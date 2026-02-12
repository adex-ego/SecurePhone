88oj98p0h0'# SecurePhone - Deploiement REST avec Tomcat 9 (LAN)

Ce guide resume les etapes effectuees pour activer l'API REST sur le port 8000 et permettre l'inscription depuis un client sur un autre PC.

---

## 1) Prerequis

- Java 11+
- Maven 3.8+
- Tomcat 9 (pas Tomcat 10)

---

## 2) Cote serveur (PC hebergeant)

### A. Preparation du projet (WAR)

Modifier le module serveur pour produire un WAR:

- Dans `serveur/pom.xml`:
  - Ajouter `<packaging>war</packaging>` sous `<name>`
  - Ajouter le plugin `maven-war-plugin` version recente

Build du WAR:

```bash
cd serveur
mvn clean package -DskipTests
```

WAR attendu:
```
serveur/target/securephone-server-1.0-SNAPSHOT.war
```

### B. Tomcat 9 sur le port 8000

Dans `TOMCAT9/conf/server.xml`, changer le port du connecteur HTTP:

```xml
<Connector port="8000" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
```

### C. Deploiement

Copier et renommer le WAR:

```bash
cp serveur/target/securephone-server-1.0-SNAPSHOT.war /path/to/tomcat9/webapps/securephone.war
```

Redemarrer Tomcat:

```bash
/path/to/tomcat9/bin/shutdown.sh
/path/to/tomcat9/bin/startup.sh
```

### D. Firewall

```bash
sudo ufw allow 8000/tcp
sudo ufw allow 8081/tcp
sudo ufw allow 50000/udp
sudo ufw allow 50020/udp
```

---

## 3) Verifications cote serveur

Verifier que Tomcat ecoute sur 8000:

```bash
ss -ltnp | grep 8000
```

Tester l'API localement:

```bash
curl -v http://127.0.0.1:8000/securephone/api/
```

Verifier le deploiement:

```bash
ls /path/to/tomcat9/webapps/
```

Logs Tomcat si besoin:

```bash
tail -n 50 /path/to/tomcat9/logs/catalina.out
```

---

## 4) Cote client (autre PC LAN)

### A. Config client

Dans `client/resources/config.properties`:

```properties
server.host=IP_DU_SERVEUR
server.port=8000
websocket.url=ws://IP_DU_SERVEUR:8081/chat
```

### B. Verifications reseau

```bash
nc -zv IP_DU_SERVEUR 8000
nc -zv IP_DU_SERVEUR 8081
```

Test API depuis le client:

```bash
curl -v http://IP_DU_SERVEUR:8000/securephone/api/
```

### C. Lancer le client

```bash
cd client
mvn exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"
```

---

## 5) Lancer TCP/UDP (chat/audio/video)

Le serveur TCP/UDP reste separe de Tomcat:

```bash
cd serveur
mvn exec:java -Dexec.mainClass="com.securephone.server.MainServer"
```

Ports:
- Chat TCP: 8081
- Audio UDP: 50000
- Video UDP: 50020
