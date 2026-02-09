# ⚡ COMMANDS RAPIDES - Développement SecurePhone

## 🚀 **LANCER L'APP**

```bash
# Méthode 1: Script (recommandée)
./run_app.sh

# Méthode 2: Maven direct
cd client && mvn clean compile && mvn exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"

# Méthode 3: Une seule commande
cd client && mvn -e clean compile exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"
```

---

## 🔨 **COMPILATION**

```bash
# Compiler le projet entier
mvn clean compile

# Compiler + tester
mvn clean test

# Compiler + package JAR
mvn clean package

# Compiler avec debug
mvn -X clean compile
```

---

## 📦 **BUILD & RELEASE**

```bash
# Créer un JAR exécutable
cd client
mvn clean package -DskipTests
java -jar target/SecurePhone-1.0.0-jar-with-dependencies.jar

# Build avec tous les modules
mvn clean install

# Build et installer localement
mvn -T 1C clean install
```

---

## 🧪 **TESTS**

```bash
# Exécuter tous les tests
mvn test

# Exécuter un test spécifique
mvn test -Dtest=TestLoginFrame

# Exécuter avec coverage
mvn test jacoco:report

# Voir le coverage
open target/site/jacoco/index.html
```

---

## 🐛 **DEBUG**

```bash
# Compiler avec plus d'infos
mvn -e clean compile

# Lancer en debug mode (avec breakpoints)
mvn -Dmaven.surefire.debug -X clean compile exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"

# Print les dépendances
mvn dependency:tree

# Vérifier les conflicts
mvn enforcer:enforce
```

---

## 🧹 **NETTOYAGE**

```bash
# Supprimer target/
mvn clean

# Supprimer tous les fichiers générés
mvn clean && rm -rf .idea target

# Réinitialiser complètement
mvn clean && mvn initialize
```

---

## 📊 **VÉRIFICATIONS**

```bash
# Vérifier la syntaxe
mvn compile

# Checker les bugs potentiels (FindBugs)
mvn findbugs:findbugs

# Analyzer le code (SonarQube)
mvn sonar:sonar

# Formater le code
mvn com.googlecode.maven-java-formatter-plugin:maven-java-formatter-plugin:format
```

---

## 🔗 **DÉPENDANCES**

```bash
# Lister toutes les dépendances
mvn dependency:tree

# Vérifier les versions
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin

# Télécharger les sources
mvn dependency:sources

# Actualiser les dépendances
mvn dependency:purge-local-repository
```

---

## 🎯 **DÉVELOPPEMENT ACTIF**

```bash
# Recompiler automatiquement (watch mode)
# À exécuter dans un terminal séparé
cd client
while inotifywait -r src/main; do mvn compile; done

# OU sur macOS
cd client
fswatch -r src/main | xargs -I {} mvn compile

# Lancer tests automatiquement
cd client
while inotifywait -r src; do mvn test; done
```

---

## 📝 **AVEC ALIAS**

Ajoute à ton `.bashrc` ou `.zshrc`:

```bash
# Alias pour les commandes courantes
alias mcs='mvn clean compile'
alias mct='mvn clean test'
alias mcr='mvn clean -e compile exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"'
alias mp='mvn clean package'
alias mclean='mvn clean && rm -rf target'

# Alias pour naviguer
alias cdsp='cd ~/Prog-sys/projet/SecurePhone'
alias cdc='cd ~/Prog-sys/projet/SecurePhone/client'

# Usage:
# cdsp                  # Aller au projet
# mcs                   # Compiler
# mcr                   # Compiler et lancer
# mct                   # Tests
```

Puis faire `source ~/.bashrc` ou redémarrer le terminal.

---

## 🚀 **ONE-LINERS UTILES**

```bash
# Compiler, tester et lancer
mvn clean compile && mvn test && mvn exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"

# Compiler et créer JAR
mvn clean package && echo "✅ JAR créé: $(ls -1 client/target/*.jar | head -1)"

# Vérifier qu'il compile sans erreur
mvn -q clean compile && echo "✅ Compilation OK" || echo "❌ Erreur"

# Afficher le chemin du JAR
mvn exec:exec -DexecOnlyIf="true"

# Lancer avec variable d'environnement
JAVA_OPTS="-Xmx512m -Xms256m" mvn exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"
```

---

## 💡 **TIPS PRODUCTIVITÉ**

```bash
# Terminal divisé (tmux/screen)
# Terminal 1: Watch le code et recompile
watch -n 2 'mvn compile 2>&1 | tail -20'

# Terminal 2: Lancer l'app
mvn exec:java -Dexec.mainClass="com.securephone.client.SecurePhoneApp"

# IDE: VS Code avec Extension Maven
# - Install "Maven for Java"
# - Explorer Maven dans la sidebar
# - Click play pour compiler/lancer
```

---

## 🔍 **TROUVER DES ERREURS**

```bash
# Voir tous les warnings
mvn clean compile -Wall

# Voir les TOUS les logs
mvn -X clean compile

# Logs dans un fichier
mvn clean compile > build.log 2>&1

# Voir les errors seulement
mvn clean compile 2>&1 | grep -E "(ERROR|error)"

# Afficher le dernier error
mvn clean compile 2>&1 | tail -50
```

---

## ✅ **BEFORE COMMITTING**

```bash
# Vérifier que tout compile
mvn clean compile && echo "✅ OK"

# Vérifier les tests
mvn test && echo "✅ Tests OK"

# Formater le code
mvn spotless:apply

# Checker les violations
mvn checkstyle:check

# Everything
mvn clean compile test checkstyle:check && echo "✅ Prêt pour commit!"
```

---

## 📚 **RESOURCES**

```
Maven Docs: https://maven.apache.org/
Plugins: https://maven.apache.org/plugins/
POM Reference: https://maven.apache.org/pom.html
Repository: https://mvnrepository.com/
```

---

**Bon développement!** 🎯

