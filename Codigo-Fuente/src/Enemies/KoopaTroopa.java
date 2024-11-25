package Enemies;

import EntityStates.KoopaTroopaState;
import EntityStates.NormalKoopaTroopaState;
import Factories.EntityFactory;
import Game.Level;
import Game.PlayerCharacter;
import Projectiles.ShellProjectile;
import Visuals.Sprite;

public class KoopaTroopa extends Enemy {

	KoopaTroopaState currentKoopaTroopaState;
	EntityFactory factory;
	Level level;
	
	public KoopaTroopa(Sprite entitySprite, float XPos, float YPos, Level level, EntityFactory factory) {
		super(entitySprite, XPos, YPos);
		this.level = level;
		this.factory = factory;
		swapState(new NormalKoopaTroopaState(this));
		this.maxXVelocity = 1;
		this.startAutomaticMovement(getRandomDirection());
		currentKoopaTroopaState.setAnimatedSprite();
	}

	public void receiveHitFromAbove(PlayerCharacter playerCharacter) {
		currentKoopaTroopaState.hitFromAbove();
	}

	public void createShellProjectile() {
		ShellProjectile shellProjectile = factory.getShellProjectile(xPosition, yPosition, level.getPlayerCharacter());
		level.addProjectile(shellProjectile);
		level.getGame().registerEntityObserver(shellProjectile);
		level.getPlayerCharacter().addScore(getScoreForDestroying());
		this.markForDestruction();
	}
	
	public float getTopY() {
		return yPosition - 27;
	}
	
	public void swapState(KoopaTroopaState newState) {
		this.currentKoopaTroopaState = newState;
		if (newState != null) {
			this.currentKoopaTroopaState.initialize();
		}
	}

	@Override
	protected void setAnimatedSprite() {
		if (currentKoopaTroopaState != null)
			currentKoopaTroopaState.setAnimatedSprite();
	}

	@Override
	public int getScoreForDestroying() {
		return 90;
	}

	@Override
	public int getScoreForDyingTo() {
		return -45;
	}

	public String getLastFacingDirection() {
		return this.lastFacingDirection;
	}
	
}
