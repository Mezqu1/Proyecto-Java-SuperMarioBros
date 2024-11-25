package Visitors;

import Enemies.Enemy;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public class PowerUpCollisionVisitor implements CollisionVisitor {
	
	protected PowerUp powerUp;
	
	public PowerUpCollisionVisitor(PowerUp powerUp) {
		this.powerUp = powerUp;
	}

	public void visit(Object toVisit) {
		
	}

	public void visit(Platform platform) {
		if (powerUp.getHasCollision()) {
			powerUp.hitPlatform(platform);
		}
	}

	public void visit(Enemy enemy) {
		
	}

	public void visit(PowerUp powerUp) {
		
	}

	public void visit(Projectile projectile) {
		
	}
}
