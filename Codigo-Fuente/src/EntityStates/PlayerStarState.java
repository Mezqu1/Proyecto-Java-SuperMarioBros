package EntityStates;

import Enemies.Enemy;
import Game.PlayerCharacter;
import PowerUps.PowerUp;
import Visuals.Sprite;
import Game.SoundManager;

public class PlayerStarState extends PlayerPowerUpState {

	private PlayerPowerUpState previousPlayerPowerUpState;
	

	public PlayerStarState(PlayerCharacter playerCharacter) {
		super(playerCharacter);
		this.previousPlayerPowerUpState = playerCharacter.getCurrentPowerUpState();
	}


	private class StarTimer extends Thread {
		
		public void run() {
			try {
				sleep(10000);
				revertPlayerState();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void revertPlayerState() {
			if (playerCharacter != null && previousPlayerPowerUpState != null) {
				playerCharacter.swapState(previousPlayerPowerUpState, true);
				SoundManager.getInstance().stopSound("star");
				SoundManager.getInstance().playSound("background",true);
			}
		}
	}

	
	@Override
	public void initialize() {
		playerCharacter.setWidth(36);
		playerCharacter.setHeight(36);
		Sprite playerSprite = playerCharacter.getSprite();
		this.stateTexturePath = playerSprite.getBaseImagePath() + "StarState/";
		setStandingSprite();
		StarTimer timer = new StarTimer();
		timer.start();
	}

	@Override
	public void hitEnemyFromAbove(Enemy enemy) {
		playerCharacter.stopDownwardsMovement();
		playerCharacter.startUpMovement();
		playerCharacter.addScore(enemy.getScoreForDestroying());
		enemy.markForDestruction();
	}



	public void ranIntoEnemy(Enemy enemy) {
		playerCharacter.addScore(enemy.getScoreForDestroying());
		enemy.markForDestruction();
		if (playerCharacter.getLastFacingDirection() == "Right")
			playerCharacter.setXVelocity(5);
		else
			playerCharacter.setXVelocity(-5);
	}

	@Override
	public int getScoreForCurrentState(PowerUp powerUp) {
		return powerUp.getScoreForStarState();
	}

	@Override
	public void applySuperMushroom() {
		
	}

	@Override
	public void applyStar() {
		
	}

	@Override
	public void applyFireFlower() {
		
	}
	
	public void setStandingSprite() {
		playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioStandingRight.gif");
	}
	
	public void setJumpingSprite() {
		if (playerCharacter.getXVelocity() >= 0)
			playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioJumpingRight.gif");
		else

			playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioJumpingLeft.gif");
	}

	@Override
	public void unInitialize() {
		this.playerCharacter = null;
		this.previousPlayerPowerUpState = null;
	}
	
}
