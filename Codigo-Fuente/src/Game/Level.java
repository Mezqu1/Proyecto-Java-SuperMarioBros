package Game;

import java.util.ArrayList;
import java.util.List;

import Projectiles.Projectile;
import Platforms.Platform;
import PowerUps.PowerUp;
import Enemies.Enemy;
import Parser.LevelGenerator;

public class Level {

	protected PlayerCharacter playerCharacter;
	protected Platform [][] platforms;
	protected List<Enemy> enemies;
	protected List<PowerUp> powerUps;
	protected List<Projectile> projectiles;
	private LevelGenerator levelGenerator;
	private Game game;
	private LevelTimer levelTimer;
	
	
	public Level(Game game) {
		this.game = game;
		platforms = new Platform [300][30];
		enemies = new ArrayList<>();
		powerUps = new ArrayList<>();
		projectiles = new ArrayList<>();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
		
	}

	public void addPlayerCharacter(PlayerCharacter playerCharacter) {
		this.playerCharacter = playerCharacter;
			
	}
	public void addPlatform(Platform platform) {
		int platformXIndex = (int) platform.getXPosition() / 36;
		int platformYIndex = (int) platform.getYPosition() /36;
		int platformXExtent = (int) platform.getWidth() / 36;
		int platformYExtent = (int) platform.getHeight() / 36;
		
		for (int i = 0; i < platformXExtent; i++) {
			for (int j = 0; j < platformYExtent; j++) {
				platforms[platformXIndex + i][platformYIndex + j] = platform;
			}
		}
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public void addPowerUp(PowerUp powerUp) {
		powerUps.add(powerUp);
	}
	
	public void addProjectile(Projectile projectile ) {
		projectiles.add(projectile);
	}

	public Platform [][] getPlatforms() {
		return platforms;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<PowerUp> getPowerUps() {
		return powerUps;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setLevelGenerator(LevelGenerator levelGenerator) {
		this.levelGenerator = levelGenerator;
	}
	
	public LevelGenerator getLevelGenerator() {
		return this.levelGenerator;
	}

	public void destroyPlayerCharacter() {
		this.playerCharacter = null;
		game.endGame(false);
	}

	public void destroyEnemy(Enemy enemy) {
		enemy.destroy();
		enemies.remove(enemy);
	}

	public void destroyPowerUp(PowerUp powerUp) {
		powerUp.destroy();
		powerUps.remove(powerUp);
	}

	public void destroyProjectile(Projectile projectile) {
		projectile.destroy();
		projectiles.remove(projectile);
	}

	public void destroyPlatform(Platform platformToDestroy) {
		for (int i = 0; i < platforms.length; i++) {
			for (int j = 0; j < platforms[0].length; j++) {
				if (platforms[i][j] == platformToDestroy) {
					platforms[i][j].destroy();
					platforms[i][j] = null;
				}
			}
		}
	}

	public void destroyLevel() {
		destroyEnemies();
		destroyPowerUps();
		destroyProjectiles();
		destroyPlatforms();
	}

	private void destroyPlatforms() {
		for (int i = 0; i < platforms.length; i++) {
			for (int j = 0; j < platforms[0].length; j++) {
				if (platforms[i][j] != null) {
					platforms[i][j].markForDestruction();
				}
			}
		}
	}

	private void destroyProjectiles() {
		for (Projectile projectile : projectiles) {
			if (projectile != null)
				projectile.markForDestruction();
		}
	}

	private void destroyPowerUps() {
		for (PowerUp powerUp : powerUps) {
			if (powerUp != null)
			powerUp.markForDestruction();
		}
	}

	private void destroyEnemies() {
		for (Enemy enemy :  enemies) {
			if (enemy != null)
			enemy.markForDestruction();
		}
	}

	public void startLevelTimer(int levelNumber) {
		int levelTimeAmount = 5 - levelNumber;
		
		levelTimer = new LevelTimer(levelTimeAmount, this);
		levelTimer.start();
	}
	
	public int getRemainingTime() {
		return levelTimer.getRemainingTime();
	}

	public void levelTimerStopped() {
		game.endGame(false);
	}
}
