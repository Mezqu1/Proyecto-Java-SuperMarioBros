package PowerUps;

import Game.PlayerCharacter;
import Visuals.Sprite;
import Game.SoundManager;

public class Coin extends PowerUp {

	public Coin(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
	}
	
	@Override
	public int getScoreForNormalState() {
		return 5;
	}

	@Override
	public int getScoreForSuperState() {
		return 5;
	}

	@Override
	public int getScoreForFireFlowerState() {
		return 5;
	}

	@Override
	public int getScoreForStarState() {
		return 5;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("Coin.png"));
	}
	
	public void apply(PlayerCharacter playerCharacter) {
		super.apply(playerCharacter);
		SoundManager.getInstance().playSound("coin",false);
		playerCharacter.getCurrentPowerUpState().applyCoin(this);
	}
}
