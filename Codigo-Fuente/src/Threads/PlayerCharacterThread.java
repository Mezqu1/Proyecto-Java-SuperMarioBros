package Threads;

import Enemies.Enemy;
import Game.Collider;
import Game.Game;
import Game.GameEntity;
import Game.PlayerCharacter;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public class PlayerCharacterThread extends UpdateThread {

	public PlayerCharacterThread(Game game,PlayerCharacter playerCharacter) {
		super(game);
	}

	protected void update() {
		updatePlayer();
	}

	private void updatePlayer() {
		PlayerCharacter character = game.getLevel().getPlayerCharacter();
		if (character != null) {
			if (character.getMarkedForDestruction()) {
				game.getLevel().destroyPlayerCharacter();
			} else {
				if (character.getUsesGravity())
					applyGravity(character);
				checkPlayerCollisions();
				character.updatePosition();
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

	private void checkPlayerCollisions() {
		PlayerCharacter playerCharacter = game.getLevel().getPlayerCharacter();
		if (playerCharacter != null) {
			Collider playerCollider = playerCharacter.getCollider();
			playerCollider.resetStandingOnEntity();
			playerCollider.checkPlatformCollisions(game.getLevel().getPlatforms());
			
			for (Enemy enemy : game.getEnemiesCopy()) {
				playerCollider.checkForCollision(enemy);
			}
			for (PowerUp powerUp : game.getPowerUpsCopy()) {
				playerCollider.checkForCollision(powerUp);
			}
			
			for (Projectile projectile : game.getProjectilesCopy()) {
				playerCollider.checkForCollision(projectile);
			}
		}
	}
}
