# 📘 Git & GitHub – Commandes Essentielles (Pense-bête)

1) Configuration de base (une seule fois)
git config --global user.name "unfollowhailey"        # Définit ton nom pour les commits  
git config --global user.email "manantsoaranjha@gmail.com" # Définit ton email pour les commits  
git config --list                            # Affiche la config Git actuelle  

2) Initialiser un dépôt local
git init                                     # Crée un dépôt Git dans le dossier courant  

3) Lier un dépôt local à un dépôt GitHub
git remote add origin https://github.com/unfollowhailey/ProgSysUI.git  # Ajoute le repo distant nommé origin  
git remote -v                                # Affiche les dépôts distants configurés  

4) Cloner un dépôt existant
git clone https://github.com/USER/REPO.git   # Télécharge un repo GitHub en local  
cd REPO                                      # Entre dans le dossier du projet  

5) Vérifier l’état des fichiers
git status                                   # Montre les fichiers modifiés, ajoutés, non suivis  

6) Ajouter des fichiers au commit
git add .                                    # Ajoute tous les fichiers modifiés au staging  
git add fichier.txt                          # Ajoute seulement un fichier précis  

7) Créer un commit
git commit -m "Message clair et court"       # Enregistre les changements avec un message  

8) Envoyer les commits vers GitHub (push)
git push -u origin main                      # Envoie les commits et définit la branche par défaut  
git push                                     # Envoie les nouveaux commits vers GitHub  

9) Récupérer les changements du dépôt distant (pull)
git pull                                     # Récupère et fusionne les changements distants  

10) Voir les commits
git log                                      # Affiche l’historique détaillé des commits  
git log --oneline                            # Affiche l’historique en version courte  

11) Branches
git branch                                   # Liste toutes les branches locales  
git branch nouvelle-branche                  # Crée une nouvelle branche  
git checkout nouvelle-branche                # Change vers une autre branche  
git checkout -b nouvelle-branche             # Crée et change de branche en une fois  

12) Fusionner une branche dans main
git checkout main                            # Se place sur la branche main  
git merge nouvelle-branche                   # Fusionne nouvelle-branche dans main  

13) Changer de dépôt distant (changer de repo GitHub)
git remote -v                                # Vérifie l’URL du repo distant actuel  
git remote remove origin                     # Supprime l’ancien dépôt distant  
git remote add origin https://github.com/USER/NOUVEAU_REPO.git # Ajoute le nouveau repo distant  
git push -u origin main                      # Envoie tout vers le nouveau repo  

14) Annuler des trucs
git reset                                    # Retire les fichiers du staging  
git checkout -- fichier.txt                  # Annule les modifs locales d’un fichier  
git commit --amend -m "Nouveau message"      # Modifie le dernier commit  

15) Workflow basique recommandé
git status                                   # Vérifier l’état des fichiers  
git add .                                    # Ajouter tous les fichiers modifiés  
git commit -m "message"                      # Créer un commit  
git pull                                     # Mettre à jour depuis GitHub  
git push                                     # Envoyer vers GitHub  

Notes rapides
- main peut aussi s’appeler master  
- Toujours faire git pull avant git push  
- Messages de commit = clairs, courts, utiles  
- Un commit = une idée  

Exemple réel complet
git status                                   # Vérifie ce qui a changé  
git add .                                    # Ajoute tous les changements  
git commit -m "Ajout page accueil"           # Enregistre le commit  
git pull                                     # Synchronise avec GitHub  
git push                                     # Envoie le commit  


git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/unfollowhailey/ProgSysUI.git
git push -u origin main