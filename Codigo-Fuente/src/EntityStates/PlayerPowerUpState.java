package EntityStates;

import Enemies.Enemy;
import Game.PlayerCharacter;
import Platforms.Platform;
import PowerUps.Coin;
import PowerUps.GreenMushroom;
import PowerUps.PowerUp;

public abstract class PlayerPowerUpState {

	PlayerCharacter playerCharacter;
	protected String stateTexturePath;
	
	public PlayerPowerUpState(PlayerCharacter playerCharacter) {
		this.playerCharacter = playerCharacter;
	}
	
	public void hitPlatformFromBelow(Platform platform) {
		platform.interactWithPlayer(playerCharacter, false);
	}
	
	public void applyPowerUpScore(PowerUp powerUp) {
		playerCharacter.addScore(getScoreForCurrentState(powerUp));
		powerUp.markForDestruction();
	}
	
	public abstract int getScoreForCurrentState(PowerUp powerUp);
	
	public abstract void initialize();

	public abstract void hitEnemyFromAbove(Enemy enemy);

	public abstract void ranIntoEnemy(Enemy enemy);
	
	public abstract void applySuperMushroom();
	
	public abstract void applyStar();
	
	public abstract void applyFireFlower();
	
	public void applyGreenMushroom(GreenMushroom greenMushroom) {
		playerCharacter.addLife();
	}
	
	public void applyCoin(Coin coin) {
		
	}

	public void spaceAction() {
		
	}
	
	public void setRunningRightSprite() {
		if (playerCharacter != null)
			playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioRunningRight.gif");
	}

	public void setRunningLeftSprite() {
		if (playerCharacter != null)
			playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioRunningLeft.gif");
	}

	public void setJumpingSprite() {
		if (playerCharacter != null)
			playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioJumping" + playerCharacter.getLastFacingDirection() + ".png");
	}
	
	public void setStandingSprite() {
		if (playerCharacter != null)
			playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioStanding" + playerCharacter.getLastFacingDirection() + ".png");
	}

	public void setPlayerDeadSprite() {
		playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioDying.png");
	}
	
	public abstract void unInitialize();
	
}
