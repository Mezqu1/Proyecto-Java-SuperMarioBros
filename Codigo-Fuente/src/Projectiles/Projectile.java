package Projectiles;

import Enemies.Enemy;
import Game.Collider;
import Game.GameEntity;
import Game.PlayerCharacter;
import Platforms.Platform;
import Visitors.ProjectileCollisionVisitor;
import Visitors.Visitor;
import Visuals.Sprite;

public abstract class Projectile extends GameEntity {
	
    public Projectile(Sprite entitySprite, float xPos, float yPos) {
		super(entitySprite, xPos, yPos);
		this.collider = new Collider(this, new ProjectileCollisionVisitor(this));
	}

	public void bounce()
    {
        
    }
	

	public void accept(Visitor v) {
		v.visit(this);
	}

	public abstract void hitPlatform(Platform platform);

	public void hitByPlayer(PlayerCharacter playerCharacter) {
		
	}
	
	public void hitEnemy(Enemy enemy) {
		enemy.markForDestruction();
	}
}
