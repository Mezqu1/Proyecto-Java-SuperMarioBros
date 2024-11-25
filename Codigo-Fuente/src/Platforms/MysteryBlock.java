package Platforms;

import Game.Game;
import Game.GameEntity;
import Game.PlayerCharacter;
import PowerUps.PowerUp;
import Visuals.Sprite;

public class MysteryBlock extends Platform {
	protected PowerUp containedPowerUp;
	
    public MysteryBlock(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
	}

	public PowerUp getContainedPowerUp() {
		return containedPowerUp;
	}

	public void setContainedPowerUp(PowerUp containedPowerUp) {
		this.containedPowerUp = containedPowerUp;
	}


	@Override
	public void doCollision(GameEntity collidedEntity) {
		
	}

	public void interactWithPlayer(PlayerCharacter playerCharacter, boolean shouldDestroy) {
		if (containedPowerUp != null) {
			containedPowerUp.addYOffset(-Game.TILE_SIZE);
			containedPowerUp.setVisible(true);
			containedPowerUp.setHasCollision(true);
			containedPowerUp.setUsesGravity(true);
			containedPowerUp.notifyObserver();
			containedPowerUp.setMovement();
			setUsedSprite();
			containedPowerUp = null;
		}
	}
	
	private void setUsedSprite() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("MysteryBlockUsed.png"));
		notifyObserver();
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("MysteryBlock.png"));
	}

	@Override
	public int getTypeForLevelCreator() {
		return 4;
	}
}
