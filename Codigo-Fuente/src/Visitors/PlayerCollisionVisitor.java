package Visitors;

import Enemies.Enemy;
import Game.PlayerCharacter;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public class PlayerCollisionVisitor implements CollisionVisitor {

	protected PlayerCharacter playerCharacter;
	
	public PlayerCollisionVisitor(PlayerCharacter playerCharacter) {
		this.playerCharacter = playerCharacter;
	}
	
    public void visit(Platform platform) {
    	if (platform.getHasCollision())
    		playerCharacter.hitPlatform(platform);
    }

	public void visit(Object toVisit) {
	}
	
	public void visit(Enemy enemy) {
		if(enemy.getHasCollision())
			playerCharacter.hitEnemy(enemy);
	}
	
	public void visit(PowerUp powerUp) {
		if (powerUp.getHasCollision())
			playerCharacter.hitPowerUp(powerUp);
		
	}

	public void visit(Projectile projectile) {
		if(projectile.getHasCollision())
			playerCharacter.hitProjectile(projectile);
	}
}
