package Platforms;

import Game.PlayerCharacter;
import Visuals.Sprite;
import Game.SoundManager;

public class BrickBlock extends Platform {
	private boolean brokenByPlayer;
	
    public BrickBlock(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
		this.destructible = true;
		this.brokenByPlayer = false;
	}
	public void interactWithPlayer(PlayerCharacter playerCharacter, boolean shouldDestroy) {
		if (shouldDestroy)
			destroyBrick(playerCharacter);
	}
	
	private void destroyBrick(PlayerCharacter playerCharacter) {
		if (Math.abs(xPosition - playerCharacter.getXPosition()) <= 10 && !brokenByPlayer) {
			SoundManager.getInstance().playSound("break block",false);
			brokenByPlayer = true;
			setBrokenSprite();
			markForDestruction();
		}
	}
	private void setBrokenSprite() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("BrickBroken.png"));
		notifyObserver();
	}
	
	public void markForDestruction() {
		super.markForDestruction();
		if (brokenByPlayer)
			destroyTime = System.nanoTime() + 100000000.0;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("Brick.png"));
	}
	
	@Override
	public int getTypeForLevelCreator() {
		return 2;
	}
}
