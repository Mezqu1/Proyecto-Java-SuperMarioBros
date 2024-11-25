package Enemies;

import Factories.EntityFactory;
import Game.Level;
import Game.PlayerCharacter;
import Visuals.Sprite;

public class Lakitu extends Enemy {
	
	private Level level;
	private EntityFactory entityFactory;
	private PlayerFollowTimer playerFollowTimer;
	private SpinyDropTimer spinyDropTimer;
	
	public Lakitu(Sprite entitySprite, float XPos, float YPos, Level level, EntityFactory entityFactory) {
		super(entitySprite, XPos, YPos);
		this.usesGravity = false;
		this.width = this.height = 36;
		this.level = level;
		this.entityFactory = entityFactory;
		this.maxXVelocity = 3;
		this.startAutomaticMovement(getRandomDirection());
	}
	
	public void receiveHitFromAbove(PlayerCharacter playerCharacter) {
		playerCharacter.addScore(getScoreForDestroying());
		markForDestruction();
	}

	private class PlayerFollowTimer extends Thread {
		@Override
		public void run() {
			while (!markedForDestruction) {
				try {
					sleep(3000);
					if (!markedForDestruction && !destroyed)
						changeDirectionTowardsPlayer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class SpinyDropTimer extends Thread {
		@Override
		public void run() {
			while (!markedForDestruction && !destroyed) {
				try {
					sleep(8000);
					if (entitySprite != null) {
						entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("LakituHidden.png"));
						long random = (long) (Math.random() * 500);
						sleep(500 + random);
						setAnimatedSprite();
						sleep(200);
						dropSpiny();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		private void dropSpiny() {
			Spiny spiny = entityFactory.getSpiny(xPosition, yPosition);
			level.addEnemy(spiny);
			level.getGame().registerEntityObserver(spiny);
		}
	}
	
	private void changeDirectionTowardsPlayer() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		if (isMovingAwayFromPlayer(playerCharacter))
			XVelocity *= -1;
	}
	
	public void startAutomaticMovement(String direction) {
		super.startAutomaticMovement(direction);
		if (playerFollowTimer == null) {
			this.playerFollowTimer = new PlayerFollowTimer();
			playerFollowTimer.start();
		}
		if (spinyDropTimer == null) {
			this.spinyDropTimer = new SpinyDropTimer();
			spinyDropTimer.start();
		}
	}
	
	private boolean isMovingAwayFromPlayer(PlayerCharacter playerCharacter) {
		return (playerCharacter.getXPosition() > xPosition && XVelocity < 0) || (playerCharacter.getXPosition() < xPosition && XVelocity > 0);
	}

	@Override
	protected void setAnimatedSprite() {
		if (entitySprite != null)
			entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("Lakitu" + lastFacingDirection + ".png"));
	}

	@Override
	public int getScoreForDestroying() {
		return 60;
	}

	@Override
	public int getScoreForDyingTo() {
		return 0;
	}
}
