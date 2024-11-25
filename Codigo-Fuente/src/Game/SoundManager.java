package Game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> sounds = new HashMap<>();

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void loadSound(String name, String filepath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            sounds.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error: No se pudo cargar el sonido desde " + filepath);
            e.printStackTrace();
        }
    }

    public void playSound(String name, boolean loop) {
        Clip clip = sounds.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();  
            }
            clip.setFramePosition(0);  
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);  
            } else {
                clip.start();  
            }
        } 
    }

    public void stopSound(String name) {
        Clip clip = sounds.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    
    
}
