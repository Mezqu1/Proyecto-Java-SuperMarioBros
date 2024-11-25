package Projectiles;

import Enemies.Enemy;
import Game.Collider;
import Game.PlayerCharacter;
import Platforms.Platform;
import Visitors.ProjectileCollisionVisitor;
import Visuals.Sprite;

public class ShellProjectile extends Projectile {
	
	protected PlayerCharacter playerCharacter;

    public ShellProjectile(Sprite entitySprite, float xPos, float yPos, PlayerCharacter playerCharacter) {
		super(entitySprite, xPos, yPos);
		this.width = this.height = 36;
		this.collider = new Collider(this, new ProjectileCollisionVisitor(this));
		this.playerCharacter = playerCharacter;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("KoopaShell.png"));
		notifyObserver();
	}

	@Override
	public void hitPlatform(Platform platform) {
		String direction = collider.getCollisionDirection(platform);
		switch (direction) {
		
			case "above": {
				stopDownwardsMovement();
				clampPositionToTopOfPlatform(platform);
				break;
			}
			case "below": {
				stopUpwardsMovement();
				break;
			}
			case "right": {
				bounce();
				if (Math.abs(platform.getYPosition() - yPosition) > 5)
					addXOffset(10);
				break;
			}
			case "left": {
				bounce();
				if (Math.abs(platform.getYPosition() - yPosition) > 5)
					addXOffset(-10);
				break;
			}
		}
	}
	
	public void bounce() {
		this.XVelocity *= -1;
	}
	
	public void hitByPlayer(PlayerCharacter playerCharacter) {
		if (xPosition - playerCharacter.getXPosition() > 0)
			XVelocity = 6;
		else {
			XVelocity = -6;
		}
	}
	
	public void hitEnemy(Enemy enemy) {
		super.hitEnemy(enemy);
		playerCharacter.addScore(enemy.getScoreForDestroying());
	}
	
}
