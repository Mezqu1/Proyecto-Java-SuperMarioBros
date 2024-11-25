package Game;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Platforms.Platform;
import Visitors.CollisionVisitor;

public class Collider {
	
	private GameEntity myEntity;
	
	private CollisionVisitor visitor;

	
	List<Platform> currentlyCheckingPlatforms;

	private boolean isStandingOnEntity;
	
	
	
	public Collider(GameEntity entity, CollisionVisitor visitor) {
		this.myEntity = entity;
		this.visitor = visitor;
		currentlyCheckingPlatforms = new ArrayList<>();
	}
	

	
	public void checkPlatformCollisions(Platform[][] platforms) {
		
		setPlatformsToCheck(platforms);
		
		for (Platform platform : currentlyCheckingPlatforms) {
			if (platform != null) {
				checkForCollision(platform);
			}
		}
	}
	private void setPlatformsToCheck(Platform[][] platforms) {
		currentlyCheckingPlatforms.clear();
		
		float xPosFloat = myEntity.getXPosition() / Game.TILE_SIZE;
		float yPosFloat = myEntity.getYPosition() / Game.TILE_SIZE;
		
		int currentXTile = (int) (myEntity.getXPosition() / Game.TILE_SIZE);
		int currentYTile = (int) (myEntity.getYPosition() / Game.TILE_SIZE);
		
		if ((xPosFloat / 36.0f) % 1.0 > 0.5f)
			currentXTile++;
		if ((yPosFloat / 36.0f) % 1.0 > 0.5f)
			currentYTile++;
		
		int XTileExtent = (int) (myEntity.getWidth() / Game.TILE_SIZE);
		int YTileExtent = (int) (myEntity.getHeight() / Game.TILE_SIZE);
		
		for (int j = -XTileExtent - 1; j <= XTileExtent + 1; j++) {
			for (int i = -YTileExtent; i <= YTileExtent; i++) {
				int XTile = i + currentXTile;
				int YTile = j + currentYTile;
				if (XTile >= 0 && YTile >= 0) {
					Platform nextPlatform = platforms[XTile][YTile];
					currentlyCheckingPlatforms.add(nextPlatform);
				}
			}
		}
	}
	
	public void checkForCollision(GameEntity entityToCheck) {
		if (!entityToCheck.getDestroyed()) {
			Rectangle2D.Float myCollisionBox = myEntity.getHitbox();
			Rectangle2D.Float receiverCollisionBox = entityToCheck.getHitbox();
			
			boolean hasCollided = myCollisionBox.intersects(receiverCollisionBox);
			if (hasCollided) {
				beginCollisionEvent(entityToCheck);
			}
		}
	}
	
	public String getCollisionDirection(GameEntity collidedEntity) {
		
		String direction = " ";
		
		Rectangle2D entityHitbox = collidedEntity.getHitbox();
		Rectangle2D intersection = myEntity.getHitbox().createIntersection(entityHitbox);
		
		float XDifference = (float) (intersection.getCenterX() - entityHitbox.getCenterX());
		float YDifference = (float) (intersection.getCenterY() - entityHitbox.getCenterY());
		float XAbsDifference = Math.abs(XDifference);
		float YAbsDifference = Math.abs(YDifference);

		if (XAbsDifference > YAbsDifference){
			if (XDifference > 0) {
				direction = "right";
			} else {
				direction = "left";
			}
		} else {
			if (YDifference > 0) {
				direction = "below";
			} else {
				direction = "above";
				isStandingOnEntity = true;
			}
		}
		return direction;
	}
	
	
	private void beginCollisionEvent(GameEntity collidedEntity) {
		collidedEntity.accept(visitor);
	}

	public GameEntity getEntity() {
		return myEntity;
	}

	public void setEntity(GameEntity entity) {
		this.myEntity = entity;
	}
	
	public boolean getIsStandingOnEntity() {
		return isStandingOnEntity;
	}

	public void resetStandingOnEntity() {
		this.isStandingOnEntity = false;
	}
}
