package PowerUps;

import Game.Collider;
import Game.Game;
import Game.GameEntity;
import Game.PlayerCharacter;
import Platforms.Platform;
import Visitors.PowerUpCollisionVisitor;
import Visitors.Visitor;
import Visuals.Sprite;

public abstract class PowerUp extends GameEntity{
	
	protected float maxXVelocity;
	
    public PowerUp(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
		this.width = this.height = Game.TILE_SIZE;
		this.collider = new Collider(this, new PowerUpCollisionVisitor(this));
	}

    @Override
    public void accept(Visitor v) {
    	v.visit(this);
    }

    public void apply(PlayerCharacter playerCharacter) {
    	playerCharacter.getCurrentPowerUpState().applyPowerUpScore(this);
    }
    
    public void doCollision(GameEntity collidedEntity) {
    	collidedEntity.accept(null);
    }
    
    public void hitPlatform(Platform platform) {
    	String direction = collider.getCollisionDirection(platform);
		switch (direction) {
			case "above": {
				stopDownwardsMovement();
				clampPositionToTopOfPlatform(platform);
				break;
			}
			case "right": {
				switchMovementDirection();
				if (Math.abs(platform.getYPosition() - yPosition) > 5)
					addXOffset(1);
				break;
			}
			case "left": {
				switchMovementDirection();
				if (Math.abs(platform.getYPosition() - yPosition) > 5)
					addXOffset(-1);
				break;
			}
		}
    }
    
    public abstract int getScoreForNormalState();

    public abstract int getScoreForSuperState();

    public abstract int getScoreForFireFlowerState();

    public abstract int getScoreForStarState();
    
    public void setMovement() {
    	
    }
    
    private void switchMovementDirection() {
		this.XVelocity *= -1;
		if (XVelocity > 0)
			this.lastFacingDirection = "Right";
		else
			this.lastFacingDirection = "Left";
	}
    
}
