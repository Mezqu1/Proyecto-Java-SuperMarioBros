package Platforms;

import Visuals.Sprite;

public class Flag extends Platform {

	public Flag(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
		this.width = 36;
		this.height = 288;
		this.hasCollision = false;
	}

	@Override
	public int getTypeForLevelCreator() {
		return 6;
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("Flag.png"));
	}

}
