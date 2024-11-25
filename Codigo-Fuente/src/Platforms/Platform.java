package Platforms;

import Game.GameEntity;
import Game.PlayerCharacter;
import Visitors.Visitor;
import Visuals.Sprite;

public abstract class Platform extends GameEntity {
	
	protected boolean destructible;
	
    public Platform(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
		this.width = this.height = 36;
		this.destructible = false; 
	}

    public void doCollision(GameEntity collidedEntity) {
    	
    }


	public void interactWithPlayer(PlayerCharacter playerCharacter, boolean shouldDestroy) {
		
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}


	public boolean isDestructible() {
		return destructible;
	}
	
	public abstract int getTypeForLevelCreator();
}
