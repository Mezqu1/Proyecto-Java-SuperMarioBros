package Game;


import Enemies.Enemy;
import EntityStates.PlayerNormalState;
import EntityStates.PlayerPowerUpState;
import Factories.EntityFactory;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;
import Visitors.PlayerCollisionVisitor;
import Visitors.Visitor;
import Visuals.Sprite;

public class PlayerCharacter extends GameEntity {

	protected PlayerPowerUpState currentPowerUpState;
	protected Level level;
	protected EntityFactory entityFactory;
	
	private int currentScore;
	private int lives;
	
	private boolean collidesWithEnemies;
	private boolean dead;
	private boolean inputEnabled;
	private boolean playingAnimation;
	
	public PlayerCharacter(Sprite entitySprite, float XPos, float YPos, Level level, EntityFactory entityFactory) {
		super(entitySprite, XPos, YPos);
		this.level = level;
		this.entityFactory = entityFactory;
		initializePlayerCharacter();
	}
	  
	private void initializePlayerCharacter() {
		this.width = this.height = 36;
		this.collider = new Collider(this, new PlayerCollisionVisitor(this));
		this.lastFacingDirection = "Right";
		swapState(new PlayerNormalState(this), false);
		this.currentScore = 0;
		this.lives = 5;
		this.collidesWithEnemies = true;
		this.inputEnabled = true;
		this.playingAnimation = false;
	}

	public Level getLevel() {
		return level;
	}

	public EntityFactory getEntityFactory() {
		return entityFactory;
	}
	
	 public boolean isPlayingAnimation() {
			return playingAnimation;
	}

	public void setPlayingAnimation(boolean playingAnimation) {
		this.playingAnimation = playingAnimation;
	}
	
	public void hitPlatform(Platform platform) {
		String direction = collider.getCollisionDirection(platform);
		switch (direction) {
		
			case "above": {
				stopDownwardsMovement();
				clampPositionToTopOfPlatform(platform);
				if (!playingAnimation)
					resetSprite();
				break;
			}
			case "below": {
				currentPowerUpState.hitPlatformFromBelow(platform);
				stopUpwardsMovement();
				break;
			}
			case "right": {
				stopLeftMovement();
					addXOffset(5);
				break;
			}
			case "left": {
				stopRightMovement();
					addXOffset(-5);
				break;
			}
		}
	}

	private void resetSprite() {
		if (XVelocity == 0)
			currentPowerUpState.setStandingSprite();
		else if (XVelocity > 0) {
			currentPowerUpState.setRunningRightSprite();
		} else {
			currentPowerUpState.setRunningLeftSprite();
		}
	}

	public void hitPowerUp(PowerUp powerUp) {
		powerUp.apply(this);
	}
	
	public void hitProjectile(Projectile projectile) {
		projectile.hitByPlayer(this);
	}
	
	public void hitEnemy(Enemy enemy) {
		if (collidesWithEnemies) {
			String direction = collider.getCollisionDirection(enemy);
			SoundManager.getInstance().playSound("touch enemy",false);
			switch (direction) {
			
				case "above": {
					if (Math.abs(yPosition - enemy.getYPosition()) >= height - 10)
						currentPowerUpState.hitEnemyFromAbove(enemy);
					break;
				}
				case "below": {
					stopUpwardsMovement();
					currentPowerUpState.ranIntoEnemy(enemy);
					break;
				}
				case "right": {
					currentPowerUpState.ranIntoEnemy(enemy);
					stopLeftMovement();
					if (Math.abs(enemy.getYPosition() - yPosition) > 5)
						addXOffset(1);
					break;
				}
				case "left": {
					currentPowerUpState.ranIntoEnemy(enemy);
					stopRightMovement();
					if (Math.abs(enemy.getYPosition() - yPosition) > 5)
						addXOffset(-1);
					break;
				}
			
			}
		}
	}

	public void startUpMovement() {
		if (inputEnabled && collider.getIsStandingOnEntity()) {
			currentPowerUpState.setJumpingSprite();
			setYVelocity(-15);
			addYOffset(-5);
			SoundManager.getInstance().playSound("jump",false);
		}
	}
	
	public void startRightMovement() {
		if (inputEnabled) {
			if (YVelocity <= 0)
				currentPowerUpState.setRunningRightSprite();
			setXVelocity(5);
			lastFacingDirection = "Right";
		}
	}

	public void startLeftMovement() {
		if (inputEnabled) {
			if (YVelocity <= 0)
				currentPowerUpState.setRunningLeftSprite();
			setXVelocity(-5);
			lastFacingDirection = "Left";
		}
	}
	
	public void stopRightMovement() {
		super.stopRightMovement();
		if (XVelocity == 0)
			currentPowerUpState.setStandingSprite();
	}
	
	public void stopLeftMovement() {
		super.stopLeftMovement();
		if (XVelocity == 0)
			currentPowerUpState.setStandingSprite();
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public PlayerPowerUpState getCurrentPowerUpState() {
		return currentPowerUpState;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getScore() {
		return currentScore;
	}
	
	public void addScore(int scoreToAdd) {
		currentScore += scoreToAdd;
		if (currentScore < 0)
			currentScore = 0;
	}
	
	public void setScore(int newScore) {
		this.currentScore = newScore;
	}
	
	public void updateXPosition() {
		if (XVelocity != 0) {
			if (XVelocity > 0)
				addXOffset(XVelocity);
			else if (XVelocity < 0)
				if (xPosition > Game.currentMinX)
					addXOffset(XVelocity);
				if (!level.getGame().getLoadingLevel() && xPosition >= Game.LEVEL_END) {
					xPosition = Game.LEVEL_END;
					level.getGame().startLevelLoadingThread();
				}
    	}
	}

	public void updateYPosition() {
		if (YVelocity != 0) {
    		if (yPosition + YVelocity >= 36 * 15) {
    			reduceLives();
    			swapState(new PlayerNormalState(this), true);
    			resetPlayer();
    			SoundManager.getInstance().stopSound("star");
				SoundManager.getInstance().playSound("background",true);
        	} else {
        		addYOffset(YVelocity);
        	}
    	}
	}

	public void swapState(PlayerPowerUpState newState, boolean uninitialize) {
		if (uninitialize && this.currentPowerUpState != null)
			this.currentPowerUpState.unInitialize();
		this.currentPowerUpState = newState;
		this.currentPowerUpState.initialize();
	}
	public void doSpaceAction() {
		currentPowerUpState.spaceAction();
	}

	public void reduceLives() {
		lives--;
		if (lives == 0)
			level.getGame().endGame(false);
		else
			resetPlayer();
	}
	
	private void resetPlayer() {
		level.getGame().resetPlayerPosition();
	}
	
	public void markForDestruction() {
		super.markForDestruction();
		if (dead)
			this.destroyTime = System.nanoTime() + 1000000000.0; 
	}

	public String getLastFacingDirection() {
		return lastFacingDirection;
	}

	public void hitByEnemy(Enemy enemy) {
		collidesWithEnemies = false;
		inputEnabled = false;
		addYOffset(-5);
		YVelocity = -10;
		if (xPosition > enemy.getXPosition())
			XVelocity = 3;
		else
			XVelocity = -3;
		new EnemyHitTimer().start();
	}
	
	public void addLife() {
		this.lives++;
	}
	
	public void setInputEnabled(boolean inputEnabled) {
		this.inputEnabled = inputEnabled;
	}
	
	private class EnemyHitTimer extends Thread {
		public void run() {
			try {
				sleep(500);
				stopRightMovement();
				stopLeftMovement();
				collidesWithEnemies = true;
				inputEnabled = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
