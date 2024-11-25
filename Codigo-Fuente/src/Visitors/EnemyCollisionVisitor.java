package Visitors;

import Enemies.Enemy;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public class EnemyCollisionVisitor implements CollisionVisitor {

	private Enemy myEnemy;
	
	public EnemyCollisionVisitor(Enemy enemy) {
		this.myEnemy = enemy;
	}
	
    public void visit(Object toVisit) {
        
    }

	public void visit(Platform platform) {
		if (platform.getHasCollision() && myEnemy.getHasCollision())
			myEnemy.hitPlatform(platform);
	}

	public void visit(Enemy enemy) {
		
	}

	public void visit(PowerUp powerUp) {
		
	}

	public void visit(Projectile projectile) {
		
	}
}
