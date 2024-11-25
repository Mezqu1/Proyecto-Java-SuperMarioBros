package Enemies;

import Game.Collider;
import Game.GameEntity;
import Game.PlayerCharacter;
import Platforms.Platform;
import Projectiles.FireballProjectile;
import Visitors.EnemyCollisionVisitor;
import Visitors.Visitor;
import Visuals.Sprite;

public abstract class Enemy extends GameEntity {
	
	protected float maxXVelocity;
	
	protected String lastFacingDirection;
	
    public Enemy(Sprite entitySprite, float xPos, float yPos) {
		super(entitySprite, xPos, yPos);
		this.collider = new Collider(this, new EnemyCollisionVisitor(this));
		this.maxXVelocity = 0.01f;
		this.startAutomaticMovement(getRandomDirection());
		if (XVelocity > 0)
			lastFacingDirection = "Right";
		if (XVelocity < 0)
			lastFacingDirection = "Left";
		RandomMovementTimer timer = new RandomMovementTimer();
		timer.start();
	}

	public String getRandomDirection() {
		double randomValue =  Math.random();
		String randomDirection = "";
		if (randomValue > 0.5)
			randomDirection = "Right";
		else
			randomDirection = "Left";
		return randomDirection;
	}
    
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
				switchMovementDirection();
				if (Math.abs(platform.getYPosition() - yPosition) > 5)
					addXOffset(1);
				break;
			}
			case "left": {
				switchMovementDirection();
				if (Math.abs(platform.getYPosition() - yPosition) > 5)
					addXOffset(-1);
				break;
			}
		}
    }

	protected abstract void setAnimatedSprite();

	protected void switchMovementDirection() {
		this.XVelocity *= -1;
		if (XVelocity > 0)
			this.lastFacingDirection = "Right";
		else
			this.lastFacingDirection = "Left";
		setAnimatedSprite();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public abstract void receiveHitFromAbove(PlayerCharacter playerCharacter);
	
	public void receiveFireballHit(FireballProjectile fireballProjectile) {
		fireballProjectile.markForDestruction();
		this.markForDestruction();
	}
    
	public void startAutomaticMovement(String direction) {
		if (direction == "Right")
			this.XVelocity = maxXVelocity;
		else
			this.XVelocity = -maxXVelocity;
		setAnimatedSprite();
	}
	
	public abstract int getScoreForDestroying();
	
	public abstract int getScoreForDyingTo();
	
	private class RandomMovementTimer extends Thread {
		@Override
		public void run() {
			while (!destroyed && !markedForDestruction)
				try {
					long random = (long) (Math.random() * 500);
					sleep(3000 + random);
					if (!destroyed && !markedForDestruction)
						if (Math.random() < 0.5)
							switchMovementDirection();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
}
