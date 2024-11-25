package Visitors;

import Enemies.Enemy;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public class ProjectileCollisionVisitor implements CollisionVisitor {

	Projectile projectile;
 	
    public ProjectileCollisionVisitor(Projectile projectile) {
		this.projectile = projectile;
	}


	@Override
    public void visit(Object toVisit) {
        
    }

	@Override
	public void visit(Platform platform) {
		projectile.hitPlatform(platform);
	}

	@Override
	public void visit(Enemy enemy) {
		projectile.hitEnemy(enemy);
	}

	@Override
	public void visit(PowerUp powerUp) {
		
	}

	@Override
	public void visit(Projectile projectile) {
		
	}

}
