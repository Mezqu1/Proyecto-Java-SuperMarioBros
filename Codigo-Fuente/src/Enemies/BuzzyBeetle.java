package Enemies;

import Game.PlayerCharacter;
import Visuals.Sprite;

public class BuzzyBeetle extends Enemy {

	public BuzzyBeetle(Sprite entitySprite, float XPos, float YPos) {
		super(entitySprite, XPos, YPos);
		this.width = this.height = 36;
		this.maxXVelocity = 1;
		this.startAutomaticMovement(getRandomDirection());
	}
	
	public void receiveHitFromAbove(PlayerCharacter playerCharacter) {
		playerCharacter.addScore(getScoreForDestroying());
		markForDestruction();
	}

	@Override
	protected void setAnimatedSprite() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("BuzzyBeetleWalking" + lastFacingDirection + ".gif"));
	}

	@Override
	public int getScoreForDestroying() {
		return 30;
	}

	@Override
	public int getScoreForDyingTo() {
		return -15;
	}
	
}
