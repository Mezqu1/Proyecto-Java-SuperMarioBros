package PowerUps;

import Game.PlayerCharacter;
import Visuals.Sprite;
import Game.SoundManager;

public class Star extends PowerUp {

	public Star(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
	}

	@Override
	public int getScoreForNormalState() {
		return 20;
	}

	@Override
	public int getScoreForSuperState() {
		return 30;
	}

	@Override
	public int getScoreForFireFlowerState() {
		return 30;
	}

	@Override
	public int getScoreForStarState() {
		return 35;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("Star.png"));
	}

	@Override
	public void apply(PlayerCharacter playerCharacter) {
		super.apply(playerCharacter);
		SoundManager.getInstance().stopSound("background");
		SoundManager.getInstance().playSound("star",true);
		playerCharacter.getCurrentPowerUpState().applyStar();
	}
	
	@Override
	public void setMovement() {
		this.maxXVelocity = 2;
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
