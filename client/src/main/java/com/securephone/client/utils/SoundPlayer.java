package com.securephone.client.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * SoundPlayer - Gestionnaire de sons de l'interface
 * 
 * Responsabilités:
 * - Charger et jouer les sons d'interface
 * - Gérer le volume global
 * - Cache des sons pour performance
 * 
 * Types de sons:
 * - MESSAGE_RECEIVED: Notification de message reçu
 * - MESSAGE_SENT: Confirmation d'envoi
 * - CALL_INCOMING: Appel entrant
 * - CALL_CONNECTED: Appel connecté
 * - CALL_ENDED: Appel terminé
 * - ERROR: Son d'erreur
 * - BUTTON_CLICK: Clic de bouton
 * 
 * @author Hatsu
 */
public class SoundPlayer {
    
    // ========== CHEMINS DES SONS ==========
    private static final String SOUND_DIR = "client/resources/sounds/";
    
    public enum SoundType {
        MESSAGE_RECEIVED("message_received.wav"),
        MESSAGE_SENT("message_sent.wav"),
        CALL_INCOMING("call_incoming.wav"),
        CALL_CONNECTED("call_connected.wav"),
        CALL_ENDED("call_ended.wav"),
        ERROR("error.wav"),
        BUTTON_CLICK("button_click.wav"),
        NOTIFICATION("notification.wav");
        
        private final String filename;
        
        SoundType(String filename) {
            this.filename = filename;
        }
        
        public String getFilename() {
            return filename;
        }
    }
    
    // ========== SINGLETON ==========
    private static SoundPlayer instance;
    
    // ========== VARIABLES D'INSTANCE ==========
    private boolean soundEnabled = true;
    private float masterVolume = 0.8f; // Volume global (0.0 à 1.0)
    private Map<SoundType, Clip> soundCache = new HashMap<>();
    
    // ========== CONSTRUCTEUR PRIVÉ ==========
    private SoundPlayer() {
        preloadSounds();
    }
    
    /**
     * Retourne l'instance unique du SoundPlayer
     */
    public static synchronized SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
        }
        return instance;
    }
    
    // ========== MÉTHODES PUBLIQUES ==========
    
    /**
     * Joue un son
     * @param soundType Type de son à jouer
     */
    public void playSound(SoundType soundType) {
        if (!soundEnabled) {
            return;
        }
        
        try {
            Clip clip = soundCache.get(soundType);
            
            if (clip == null) {
                // Son pas en cache, le charger
                clip = loadSound(soundType);
                if (clip == null) {
                    System.err.println("Impossible de charger le son: " + soundType);
                    return;
                }
            }
            
            // Variable final pour utilisation dans le thread
            final Clip finalClip = clip;
            
            // Jouer le son dans un thread séparé pour ne pas bloquer l'UI
            new Thread(() -> {
                try {
                    // Si le son est déjà en cours, le redémarrer
                    if (finalClip.isRunning()) {
                        finalClip.stop();
                    }
                    finalClip.setFramePosition(0);
                    
                    // Appliquer le volume
                    setClipVolume(finalClip, masterVolume);
                    
                    finalClip.start();
                } catch (Exception e) {
                    System.err.println("Erreur lors de la lecture du son: " + e.getMessage());
                }
            }).start();
            
        } catch (Exception e) {
            System.err.println("Erreur SoundPlayer: " + e.getMessage());
        }
    }
    
    /**
     * Active ou désactive les sons
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled) {
            stopAllSounds();
        }
    }
    
    /**
     * Vérifie si les sons sont activés
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
    
    /**
     * Définit le volume global (0.0 à 1.0)
     */
    public void setMasterVolume(float volume) {
        this.masterVolume = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
    /**
     * Retourne le volume global
     */
    public float getMasterVolume() {
        return masterVolume;
    }
    
    /**
     * Arrête tous les sons en cours
     */
    public void stopAllSounds() {
        for (Clip clip : soundCache.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        }
    }
    
    /**
     * Libère toutes les ressources audio
     */
    public void dispose() {
        stopAllSounds();
        for (Clip clip : soundCache.values()) {
            if (clip != null) {
                clip.close();
            }
        }
        soundCache.clear();
    }
    
    // ========== MÉTHODES PRIVÉES ==========
    
    /**
     * Précharge tous les sons en mémoire
     */
    private void preloadSounds() {
        System.out.println("Préchargement des sons...");
        for (SoundType type : SoundType.values()) {
            Clip clip = loadSound(type);
            if (clip != null) {
                soundCache.put(type, clip);
                System.out.println("  ✓ " + type.name() + " chargé");
            } else {
                System.out.println("  ✗ " + type.name() + " introuvable (sera ignoré)");
            }
        }
    }
    
    /**
     * Charge un fichier son
     */
    private Clip loadSound(SoundType soundType) {
        try {
            String filepath = SOUND_DIR + soundType.getFilename();
            File soundFile = new File(filepath);
            
            if (!soundFile.exists()) {
                // Fichier n'existe pas encore, retourner null silencieusement
                // (utile pendant le développement avant d'avoir tous les sons)
                return null;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            return clip;
            
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Format audio non supporté: " + soundType.getFilename());
        } catch (IOException e) {
            System.err.println("Erreur IO lors du chargement: " + soundType.getFilename());
        } catch (LineUnavailableException e) {
            System.err.println("Ligne audio indisponible: " + soundType.getFilename());
        }
        
        return null;
    }
    
    /**
     * Applique le volume à un clip
     */
    private void setClipVolume(Clip clip, float volume) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            // Conversion du volume linéaire (0.0-1.0) en décibels
            // Formule: dB = 20 * log10(volume)
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            
            // S'assurer que le volume est dans les limites du contrôle
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            dB = Math.max(min, Math.min(max, dB));
            
            gainControl.setValue(dB);
        } catch (IllegalArgumentException e) {
            // Le contrôle de volume n'est pas disponible, ignorer
        }
    }
    
    // ========== MÉTHODES DE COMMODITÉ ==========
    
    /**
     * Joue le son de message reçu
     */
    public void playMessageReceived() {
        playSound(SoundType.MESSAGE_RECEIVED);
    }
    
    /**
     * Joue le son de message envoyé
     */
    public void playMessageSent() {
        playSound(SoundType.MESSAGE_SENT);
    }
    
    /**
     * Joue le son d'appel entrant
     */
    public void playCallIncoming() {
        playSound(SoundType.CALL_INCOMING);
    }
    
    /**
     * Joue le son d'appel connecté
     */
    public void playCallConnected() {
        playSound(SoundType.CALL_CONNECTED);
    }
    
    /**
     * Joue le son d'appel terminé
     */
    public void playCallEnded() {
        playSound(SoundType.CALL_ENDED);
    }
    
    /**
     * Joue le son d'erreur
     */
    public void playError() {
        playSound(SoundType.ERROR);
    }
    
    /**
     * Joue le son de clic de bouton
     */
    public void playButtonClick() {
        playSound(SoundType.BUTTON_CLICK);
    }
    
    /**
     * Joue le son de notification générique
     */
    public void playNotification() {
        playSound(SoundType.NOTIFICATION);
    }
}