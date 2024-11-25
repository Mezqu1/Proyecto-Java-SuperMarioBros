package Game;
public class SoundLoader {
	public void loadAllSounds() {
        SoundManager soundManager = SoundManager.getInstance();
        soundManager.loadSound("jump", "src/Sounds/mario-bros-jump.wav");
        soundManager.loadSound("coin", "src/Sounds/mario-bros-coin.wav");
        soundManager.loadSound("background", "src/Sounds/ringtones-super-mario-bros.wav");
        soundManager.loadSound("click", "src/Sounds/button-sound.wav");
        soundManager.loadSound("level completed", "src/Sounds/mario-bros-nivel-completado.wav");
        soundManager.loadSound("touch enemy", "src/Sounds/mario-touch-enemy.wav");
        soundManager.loadSound("break block", "src/Sounds/breakblock.wav");
        soundManager.loadSound("pipe", "src/Sounds/pipe.wav");
        soundManager.loadSound("star", "src/Sounds/star.wav");
        soundManager.loadSound("mario fireball", "src/Sounds/mario-fireball.wav");
        soundManager.loadSound("game over", "src/Sounds/mario-bros-game-over.wav");
    }
}