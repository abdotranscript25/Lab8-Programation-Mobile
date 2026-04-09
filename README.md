# Lab8-Threads-AsyncTask - Programmation asynchrone

##   Description
Application Android réalisée dans le cadre du Lab 8 de développement mobile.  
Elle permet de comprendre la différence entre UI Thread et Worker Thread, et d'exécuter des traitements longs (chargement d'image, calculs lourds) sans bloquer l'interface utilisateur.

##   Fonctionnalités
- **Charger image (Thread)** : Simule un chargement long avec Thread classique + Handler
- **Calcul dynamique (AsyncTask)** : Effectue des calculs sur de très grands nombres (addition, soustraction, multiplication, division)
- **Afficher Toast** : Teste la réactivité de l'UI pendant les traitements
- **Barre de progression** : Visualisation de l'avancement des traitements
- **UI réactive** : L'application reste responsive même pendant les calculs longs

##   Technologies utilisées
- **Langage** : Java
- **SDK minimum** : API 24 (Android 7.0)
- **Thread classique** : Pour les traitements en arrière-plan
- **Handler** : Pour revenir sur l'UI Thread
- **AsyncTask** : Pour les calculs longs avec progression
- **BigInteger** : Pour les calculs sur très grands nombres

##   Captures d'écran

# activity_main.xml
<img width="875" height="847" alt="1" src="https://github.com/user-attachments/assets/b100b04a-564d-4aca-b3a0-7d6fa903bec0" />
<img width="879" height="836" alt="1_1" src="https://github.com/user-attachments/assets/854cce15-4a4f-4c82-b6cd-46a38f219e97" />
<img width="891" height="850" alt="1_2" src="https://github.com/user-attachments/assets/b6f28192-eb1e-40db-bf2a-c995f5f79882" />
<img width="867" height="846" alt="1_3" src="https://github.com/user-attachments/assets/18c32c46-f0e1-4531-86ea-58c6cbd08a65" />
<img width="877" height="833" alt="1_4" src="https://github.com/user-attachments/assets/d6a609ca-060c-42fd-a3f4-9fce50d303a9" />


### MainActivity.java (partie Thread)
<img width="909" height="846" alt="2" src="https://github.com/user-attachments/assets/ee46f72b-56bc-41e4-94a3-77fdf300d2de" />
<img width="951" height="836" alt="2_1" src="https://github.com/user-attachments/assets/289eec5f-1dce-48ec-b005-e566100922e4" />
<img width="910" height="839" alt="2_2" src="https://github.com/user-attachments/assets/cda35064-461d-4115-a479-b32634f08fe4" />
<img width="936" height="837" alt="2_3" src="https://github.com/user-attachments/assets/17bdde9c-3942-41ff-95f8-1f4708576f7c" />
<img width="905" height="836" alt="2_4" src="https://github.com/user-attachments/assets/8fbfa63e-80b0-40c2-bf04-eaed00c3dec2" />
<img width="853" height="526" alt="2_5" src="https://github.com/user-attachments/assets/b03f0712-c886-4f14-a605-0bf51fc31e92" />


## 🎥 Démonstration vidéo


https://github.com/user-attachments/assets/147adab3-826f-454b-8af8-7745f7d59b6d



##   Structure du projet
- `app/src/main/java/com/example/labthreadsasynctask/MainActivity.java` : Activité principale (Thread + AsyncTask)
- `app/src/main/res/layout/activity_main.xml` : Interface utilisateur
- `app/src/main/res/drawable/` : Images éventuelles
- `Screenshots/` : Captures d'écran et vidéo

##   Concepts clés abordés

| Concept | Rôle |
|---------|------|
| **UI Thread (Main Thread)** | Thread qui gère l'affichage et les interactions |
| **Worker Thread** | Thread de fond pour les traitements longs |
| **ANR (Application Not Responding)** | Erreur quand l'UI est bloquée trop longtemps |
| **Handler** | Permet de poster des actions sur l'UI Thread |
| **View.post()** | Alternative à Handler pour revenir sur l'UI Thread |
| **AsyncTask** | Classe simplifiant le travail asynchrone |
| **publishProgress()** | Envoie la progression du traitement |
| **onProgressUpdate()** | Met à jour l'UI pendant le traitement |
| **BigInteger** | Permet des calculs sur des nombres de taille illimitée |

## 🔧 Méthodes importantes

| Méthode | Rôle |
|---------|------|
| `new Thread(() -> {...}).start()` | Crée et démarre un thread de fond |
| `Handler.post(Runnable)` | Exécute un code sur l'UI Thread |
| `AsyncTask.execute()` | Lance l'AsyncTask |
| `onPreExecute()` | UI Thread - avant traitement |
| `doInBackground()` | Worker Thread - traitement long |
| `publishProgress()` | Worker Thread - envoie la progression |
| `onProgressUpdate()` | UI Thread - affiche la progression |
| `onPostExecute()` | UI Thread - après traitement |

## 👤 Auteur
**abdotranscrip25** El Hachimi Abdelhamid - Lab8 Programation Mobile

## 📅 Date de réalisation
Avril 2026
