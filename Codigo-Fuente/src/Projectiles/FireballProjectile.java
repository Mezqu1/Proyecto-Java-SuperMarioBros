package Projectiles;

import Enemies.Enemy;
import Game.PlayerCharacter;
import Platforms.Platform;
import Visuals.Sprite;

public class FireballProjectile extends Projectile {
	
	private int bouncesLeft;
	private PlayerCharacter playerCharacter;

    public FireballProjectile(Sprite entitySprite, float xPosition, float yPosition, PlayerCharacter playerCharacter) {
		super(entitySprite, xPosition, yPosition);
		this.height = this.width = 16;
		this.bouncesLeft = 2;
		this.playerCharacter = playerCharacter;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("Fireball0Deg.png"));
	}

	@Override
	public void hitPlatform(Platform platform) {
		String direction = collider.getCollisionDirection(platform);
		if (direction == "above")
			clampPositionToTopOfPlatform(platform);
			addYOffset(-10);
		bounce(direction);
	}
	
	public void bounce(String direction) {
		if (bouncesLeft == 0)
			markForDestruction();
		else {
			bouncesLeft--;
			if (direction == "above")
				this.YVelocity *= -1;
			else
				this.XVelocity *= -1;
		}
	}
	
	public void markForDestruction() {
		super.markForDestruction();
		this.entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("FireballExplosion3.png"));
		destroyTime = System.nanoTime() + 100000000;
	}

	public void hitEnemy(Enemy enemy) {
		super.hitEnemy(enemy);
		playerCharacter.addScore(enemy.getScoreForDestroying());
		markForDestruction();
	}
	
}
