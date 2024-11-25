package Enemies;

import Game.PlayerCharacter;
import Visuals.Sprite;

public class Spiny extends Enemy {

	public Spiny(Sprite entitySprite, float XPos, float YPos) {
		super(entitySprite, XPos, YPos);
		this.width = this.height = 36;
		this.maxXVelocity = 1;
		this.startAutomaticMovement(getRandomDirection());
	}
	
	public void receiveHitFromAbove(PlayerCharacter playerCharacter) {
		playerCharacter.getCurrentPowerUpState().ranIntoEnemy(this);
	}
	
	@Override
	protected void setAnimatedSprite() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("SpinyWalking" + lastFacingDirection + ".gif"));
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
