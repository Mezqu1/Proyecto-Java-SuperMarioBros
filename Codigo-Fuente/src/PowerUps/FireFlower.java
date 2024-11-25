package PowerUps;

import Game.PlayerCharacter;
import Visuals.Sprite;

public class FireFlower extends PowerUp {

	public FireFlower(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
	}

	@Override
	public int getScoreForNormalState() {
		return 5;
	}

	@Override
	public int getScoreForSuperState() {
		return 30;
	}

	@Override
	public int getScoreForFireFlowerState() {
		return 50;
	}

	@Override
	public int getScoreForStarState() {
		return 5;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("FireFlower.png"));
	}

	@Override
	public void apply(PlayerCharacter playerCharacter) {
		super.apply(playerCharacter);
		playerCharacter.getCurrentPowerUpState().applyFireFlower();
	}
}
