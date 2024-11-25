package Enemies;

import Game.PlayerCharacter;
import Visuals.Sprite;

public class Goomba extends Enemy {

	
	private boolean squished;
	
	public Goomba(Sprite entitySprite, float XPos, float YPos) {
		super(entitySprite, XPos, YPos);
		this.width = this.height = 36;
		squished = false;
		this.maxXVelocity = 1;
		this.startAutomaticMovement(getRandomDirection());
	}

	
	
	public void receiveHitFromAbove(PlayerCharacter playerCharacter) {
		if (!markedForDestruction && !squished) {
			setSquished();
			squished = true;
			playerCharacter.addScore(getScoreForDestroying());
			setHasCollision(false);
			markForDestruction();
		}
	}

	private void setSquished() {
		if (!squished) {
			this.entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("GoombaSquished.png"));
			this.width = 36;
			this.height = 18;
			this.yPosition += 18;
		}
	}
	
	public double getDestroyTime() {
		return destroyTime;
	}
	
	public void markForDestruction() {
		super.markForDestruction();
		if (squished)
			destroyTime = System.nanoTime() + 500000000.0;
	}

	@Override
	protected void setAnimatedSprite() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("GoombaWalking.gif"));
	}

	@Override
	public int getScoreForDestroying() {
		return 60;
	}

	@Override
	public int getScoreForDyingTo() {
		return -30;
	}
}
