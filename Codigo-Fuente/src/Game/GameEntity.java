package Game;

import java.awt.geom.Rectangle2D;

import Platforms.Platform;
import Visitors.Visitor;
import Visuals.Observer;
import Visuals.Sprite;

public abstract class GameEntity implements Visitable {
    protected float xPosition, yPosition;
    protected float XVelocity, YVelocity;
    
    protected float height, width;
    protected Collider collider;
    protected Rectangle2D.Float hitbox;
    protected boolean usesGravity;
    protected boolean hasCollision;
	
	protected boolean markedForDestruction;
	protected boolean visible;
	
    protected Sprite entitySprite;
    protected Observer observer;
    
    protected double destroyTime;
    protected boolean destroyed;

	protected String lastFacingDirection;

    public GameEntity(Sprite entitySprite, float xPosition, float yPosition) {
    	initialize();
    	this.entitySprite = entitySprite;
    	this.xPosition = xPosition;
    	this.yPosition = yPosition;
		initializeImagePath();
    }
    
    private void initialize() {
		this.hitbox = new Rectangle2D.Float(getXPosition(), getYPosition(), getWidth(), getHeight());
		this.markedForDestruction = false;
		this.visible = true;
		this.usesGravity = true;
		this.hasCollision = true;
		this.destroyed = false;
	}

	public void initializeImagePath() {
    	
    }
    
    public boolean getDestroyed() {
    	return this.destroyed;
    }
    
    public double getDestroyTime() {
    	return destroyTime;
    }
    
    public boolean getHasCollision() {
    	return hasCollision;
    }
    
    public void setHasCollision(boolean canBeCollidedWith) {
    	this.hasCollision = canBeCollidedWith;
    }
    
    public boolean getUsesGravity() {
		return usesGravity;
	}

	public void setUsesGravity(boolean usesGravity) {
		this.usesGravity = usesGravity;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void stopDownwardsMovement() {
    	if (YVelocity > 0)
    		setYVelocity(0);
	}
    
    public void stopUpwardsMovement() {
    	if (YVelocity < 0)
			setYVelocity(0);
	}

    public void stopLeftMovement() {
    	if (XVelocity < 0)
    		setXVelocity(0);
	}
    
    public void stopRightMovement() {
    	if (XVelocity > 0)
    		setXVelocity(0);
	}
    
    public void updatePosition() {
    	updateXPosition();
    	updateYPosition();
    }

	public void notifyObserver() {
		if (observer != null)
			observer.update();
    }

	public void markForDestruction() {
    	markedForDestruction = true;
	}
	
    public void destroy() {
    	this.destroyed = true;
    	notifyObserver();
    	this.entitySprite = null;
    	this.collider = null;
    	this.hitbox = null;
    	this.observer = null;
    }
    
    public boolean getMarkedForDestruction() {
		return markedForDestruction;
	}

    public void attachObserver(Observer observer){
    	this.observer = observer;
    	notifyObserver();
    }

    public void removeObserver(){
    	this.observer = null;
    }

    public void addXOffset(float XOffset){
        this.xPosition += XOffset;
    }
    
    public void addYOffset(float YOffset){
        this.yPosition += YOffset;
    }

	public float getXPosition() {
		return xPosition;
	}

	public void setXPosition(float xPos) {
		xPosition = xPos;
	}

	public float getYPosition() {
		return yPosition;
	}

	public void setYPosition(float yPos) {
		yPosition = yPos;
	}

	public float getXVelocity() {
		return XVelocity;
	}

	public void setXVelocity(float xVelocity) {
		XVelocity = xVelocity;
	}

	public float getYVelocity() {
		return YVelocity;
	}

	public void setYVelocity(float yVelocity) {
		YVelocity = yVelocity;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public Observer getObserver() {
		return observer;
	}

	public Sprite getSprite() {
		return entitySprite;
	}
	
    public Collider getCollider() {
    	return this.collider;
    }
    
    public void updateXPosition() {
    	if (XVelocity != 0) {
    		addXOffset(XVelocity);
    	}
    }
    
    public void updateYPosition() {
    	if (YVelocity != 0) {
    		if (yPosition + YVelocity >= 36 * 20) {
        		markForDestruction();
        	} else {
        		addYOffset(YVelocity);
        	}
    	}
    }
    
    public Rectangle2D.Float getHitbox() {
    	updateHitbox();
        return hitbox;
    }

    public void updateHitbox() {
    	if (hitbox != null)
    		this.hitbox.setRect(getXPosition(), getYPosition(), getWidth(), getHeight());
	}
    
    protected void clampPositionToTopOfPlatform(Platform platform) {
    	float platformTopY = (float) (platform.getHitbox().getCenterY() - platform.getHitbox().getHeight() / 2);
    	float feetY = (float) (hitbox.getCenterY() + hitbox.getHeight() / 2);
    	
    	addYOffset(platformTopY - feetY + 0.1f);
    	
    	notifyObserver();
    }

	public abstract void accept(Visitor v);

    public float getTopY() {
    	return yPosition - height / 2;
    }
    
    public float getBottomY() {
    	return yPosition + height / 2;
    }
    
}
