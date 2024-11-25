package Threads;

import java.util.List;

import Enemies.Enemy;
import Game.Game;
import Game.PlayerCharacter;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public class RenderThread extends UpdateThread {
	private List<Enemy> enemies;
	private List<PowerUp> powerUps;
	private List<Projectile> projectiles;
	
	public RenderThread(Game game) {
		super(game);
		this.updatesPerSecond = 90;
	}
	
	public void update() {
		this.enemies = game.getEnemiesCopy();
		this.powerUps = game.getPowerUpsCopy();
		this.projectiles = game.getProjectilesCopy();
		updatePlayerCharacter();
		updateEnemies();
		updatePowerUps();
		updateProjectiles();
	}
	
	private void updatePlayerCharacter() {
		PlayerCharacter playerCharacter = game.getLevel().getPlayerCharacter();
		if (playerCharacter != null && (playerCharacter.getXVelocity() != 0 || playerCharacter.getYVelocity() != 0))
			playerCharacter.notifyObserver();
	}

	private void updateEnemies() {
		for (Enemy enemy : enemies) {
			enemy.notifyObserver();
		}
	}

	private void updatePowerUps() {
		for (PowerUp powerUp : powerUps) {
			if ((powerUp.getXVelocity() != 0 || powerUp.getYVelocity() != 0))
				powerUp.notifyObserver();
		}
	}

	private void updateProjectiles() {
		for (Projectile projectile : projectiles) {
			projectile.notifyObserver();
		}
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void setPowerUps(List<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}

	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}
	
}
