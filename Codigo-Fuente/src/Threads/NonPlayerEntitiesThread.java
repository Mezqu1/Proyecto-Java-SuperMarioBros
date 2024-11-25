package Threads;

import Enemies.Enemy;
import Game.Game;
import Game.GameEntity;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public class NonPlayerEntitiesThread extends UpdateThread {

	public NonPlayerEntitiesThread(Game game) {
		super(game);
	}

	protected void update() {
		checkForPlatformsToRemove();
		updateEnemies();
		updatePowerUps();
		updateProjectiles();
	}

	private void updateEnemies() {
		for (Enemy enemy : game.getEnemiesCopy()) {
			if (enemy.getMarkedForDestruction()) {
				if (System.nanoTime() > enemy.getDestroyTime())
					game.getLevel().destroyEnemy(enemy);
			} else {
				if (enemy.getUsesGravity())
					applyGravity(enemy);
				checkEnemyCollisions(enemy);
				enemy.updatePosition();
			}
		}
	}

	private void updatePowerUps() {
		for (PowerUp powerUp : game.getPowerUpsCopy()) {
			if (powerUp.getMarkedForDestruction()) {
				game.getLevel().destroyPowerUp(powerUp);
			} else {
				if (powerUp.getUsesGravity())
					applyGravity(powerUp);
				checkPowerUpCollisions(powerUp);
				powerUp.updatePosition();
			}
		}
	}

	private void updateProjectiles() {
		for (Projectile projectile : game.getProjectilesCopy()) {
			if (projectile.getMarkedForDestruction()) {
				if (System.nanoTime() > projectile.getDestroyTime())
					game.getLevel().destroyProjectile(projectile);
			} else {
				if (projectile.getUsesGravity())
					applyGravity(projectile);
				checkProjectileCollisions(projectile);
				projectile.updatePosition();
			}
		}
	}

	private void checkProjectileCollisions(Projectile projectile) {
		projectile.getCollider().checkPlatformCollisions(game.getLevel().getPlatforms());
		
		for (Enemy enemy : game.getEnemiesCopy()) {
			projectile.getCollider().checkForCollision(enemy);
		}
	}

	private void checkPowerUpCollisions(PowerUp powerUp) {
		powerUp.getCollider().checkPlatformCollisions(game.getLevel().getPlatforms());
	}
	
	private void checkEnemyCollisions(Enemy enemy) {
		enemy.getCollider().checkPlatformCollisions(game.getLevel().getPlatforms());
	}
	
	public void checkForPlatformsToRemove() {
		Platform [][] platformsToCheck = game.getLevel().getPlatforms();
		for (int i = 0; i < platformsToCheck.length; i++) {
			for (int j = 0; j < platformsToCheck[0].length; j++) {
				Platform platform = platformsToCheck[i][j];
				if (platform != null && platform.getMarkedForDestruction() && System.nanoTime() > platform.getDestroyTime())
					game.getLevel().destroyPlatform(platform);
			}
		}
	}
	
	private void applyGravity(GameEntity entity) {
		if (entity.getYVelocity() + 1 < Game.MAX_GRAVITY) {
			entity.setYVelocity(entity.getYVelocity() + 1);
		} else {
			entity.setYVelocity(Game.MAX_GRAVITY);
		}
	}
}
