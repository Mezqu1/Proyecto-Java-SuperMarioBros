package Enemies;

import Game.PlayerCharacter;
import Game.SoundManager;
import Visuals.Sprite;

public class PiranhaPlant extends Enemy {
	
	private EmergeTimer emergeTimer;
	private HideTimer hideTimer;

	public PiranhaPlant(Sprite entitySprite, float XPos, float YPos) {
		super(entitySprite, XPos, YPos);
		this.width = 34;
		this.height = 54;
		this.emergeTimer = new EmergeTimer();
		this.hideTimer = new HideTimer();
		this.usesGravity = false;
		this.maxXVelocity = 0;
		this.startAutomaticMovement(getRandomDirection());
	}

	public void receiveHitFromAbove(PlayerCharacter playerCharacter) {
		playerCharacter.getCurrentPowerUpState().ranIntoEnemy(this);
	}

	public void show() {
		SoundManager.getInstance().playSound("pipe", false);
		emergeTimer.run();
	}

	public void hide() {
		hideTimer.run();
	}
	
	private class EmergeTimer extends Thread {
		@Override
		public void run() {
			try {
				setVisible(true);
				YVelocity = -1;
				sleep(1000);
				YVelocity = 0;
				setHasCollision(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class HideTimer extends Thread {
		@Override
		public void run() {
			try {
				setHasCollision(false);
				YVelocity = 1;
				sleep(1000);
				YVelocity = 0;
				setVisible(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	protected void setAnimatedSprite() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("PiranhaPlant.gif"));
	}

	@Override
	public int getScoreForDestroying() {
		return 30;
	}

	@Override
	public int getScoreForDyingTo() {
		return -30;
	}
	
}
