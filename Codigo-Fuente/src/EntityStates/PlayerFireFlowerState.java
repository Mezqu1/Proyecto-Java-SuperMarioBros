package EntityStates;

import Enemies.Enemy;
import Game.PlayerCharacter;
import Game.SoundManager;
import Platforms.Platform;
import Projectiles.FireballProjectile;
import Visuals.Sprite;

public class PlayerFireFlowerState extends PlayerSuperState {

	private FireballCooldownTimer timer;
	private boolean cooldownActive;
	
	public PlayerFireFlowerState(PlayerCharacter playerCharacter) {
		super(playerCharacter);
		this.cooldownActive = false;
	}

	public void hitPlatformFromBelow(Platform platform) {
		platform.interactWithPlayer(playerCharacter, true);
	}

	@Override
	public void initialize() {
		super.initialize();
		Sprite playerSprite = playerCharacter.getSprite();
		this.stateTexturePath = playerSprite.getBaseImagePath() + "FireFlowerState/";
		setStandingSprite();
	}

	
	public void spaceAction() {
		if (!cooldownActive) {
			FireballProjectile fireball = createFireball();
			setFireballDirectionAndVelocity(fireball);
			SoundManager.getInstance().playSound("mario fireball", false);
			cooldownActive = true;
			timer = new FireballCooldownTimer();
			timer.start();
		}
	}
	
	private void setFireballDirectionAndVelocity(FireballProjectile fireball) {
		if (playerCharacter.getLastFacingDirection() == "Left") {
			fireball.addXOffset(-20);
			fireball.setXVelocity(-7 + playerCharacter.getXVelocity());
		} else {
			fireball.addXOffset(20);
			fireball.setXVelocity(7 + playerCharacter.getXVelocity());
		}
	}

	private FireballProjectile createFireball() {
		FireballProjectile fireball = playerCharacter.getEntityFactory().getFireballProjectile((int) playerCharacter.getXPosition(), (int) playerCharacter.getYPosition() + 10, playerCharacter);
		playerCharacter.getLevel().addProjectile(fireball);
		playerCharacter.getLevel().getGame().registerProjectileObservers();
		return fireball;
	}

	public void ranIntoEnemy(Enemy enemy) {
		playerCharacter.hitByEnemy(enemy);
		playerCharacter.swapState(new PlayerSuperState(playerCharacter), true);
	}
	
	@Override
	public void applySuperMushroom() {
		
	}

	@Override
	public void applyStar() {
		playerCharacter.swapState(new PlayerStarState(playerCharacter), false);
	}

	@Override
	public void applyFireFlower() {
		
	}
	
	private class FireballCooldownTimer extends Thread {
		@Override
		public void run() {
			try {
				playerCharacter.setPlayingAnimation(true);
				playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioFireball" + playerCharacter.getLastFacingDirection() + ".png");
				sleep(100);
				if (playerCharacter != null)
					playerCharacter.getSprite().setCurrentImagePath(stateTexturePath + "MarioStanding" + playerCharacter.getLastFacingDirection() + ".png");
					playerCharacter.setPlayingAnimation(false);
				sleep(100);
				cooldownActive = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
