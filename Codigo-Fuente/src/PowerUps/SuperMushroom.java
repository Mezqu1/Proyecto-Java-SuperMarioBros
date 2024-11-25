package PowerUps;

import Game.PlayerCharacter;
import Visuals.Sprite;

public class SuperMushroom extends PowerUp {

	public SuperMushroom(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
	}
	@Override
	public int getScoreForNormalState() {
		return 10;
	}

	@Override
	public int getScoreForSuperState() {
		return 50;
	}

	@Override
	public int getScoreForFireFlowerState() {
		return 50;
	}

	@Override
	public int getScoreForStarState() {
		return 10;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("SuperMushroom.png"));
	}
	
	@Override
	public void apply(PlayerCharacter playerCharacter) {
		super.apply(playerCharacter);
		playerCharacter.getCurrentPowerUpState().applySuperMushroom();
	}
	
	@Override
	public void setMovement() {
		this.maxXVelocity = 1.6f;
		this.startAutomaticMovement(getRandomDirection());
	}
	
	private String getRandomDirection() {
		double randomValue =  Math.random();
		String randomDirection = "";
		if (randomValue > 0.5)
			randomDirection = "Right";
		else
			randomDirection = "Left";
		return randomDirection;
	}
    
	private void startAutomaticMovement(String direction) {
		if (direction == "Right")
			this.XVelocity = maxXVelocity;
		else
			this.XVelocity = -maxXVelocity;
	}
}
