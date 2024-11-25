package Platforms;

import Visuals.Sprite;

public class SolidBlock extends Platform {
	
    public SolidBlock(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
	}

	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("SolidBlock.png"));
	}

	@Override
	public int getTypeForLevelCreator() {
		return 3;
	}


}
