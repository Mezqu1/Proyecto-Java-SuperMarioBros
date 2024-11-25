package EntityStates;

import Enemies.Enemy;
import Game.PlayerCharacter;
import Platforms.Platform;
import PowerUps.PowerUp;
import Visuals.Sprite;

public class PlayerSuperState extends PlayerPowerUpState {
	
	public PlayerSuperState(PlayerCharacter playerCharacter) {
		super(playerCharacter);
	}

	public void hitPlatformFromBelow(Platform platform) {
		platform.interactWithPlayer(playerCharacter, true);
	}

	public void notifyStateChange(PlayerPowerUpState newState) {
		
	}

	@Override
	public void initialize() {
		if (playerCharacter != null) {
			playerCharacter.addYOffset(-36);
			playerCharacter.setHeight(72);
			playerCharacter.setWidth(36);
			Sprite playerSprite = playerCharacter.getSprite();
			this.stateTexturePath = playerSprite.getBaseImagePath() + "SuperState/";
			setStandingSprite();
		}
	}

	@Override
	public void hitEnemyFromAbove(Enemy enemy) {
		playerCharacter.stopDownwardsMovement();
		playerCharacter.startUpMovement();
		enemy.receiveHitFromAbove(playerCharacter);
	}

	public void ranIntoEnemy(Enemy enemy) {
		playerCharacter.hitByEnemy(enemy);
		playerCharacter.swapState(new PlayerNormalState(playerCharacter), true);
		
	}

	@Override
	public int getScoreForCurrentState(PowerUp powerUp) {
		return powerUp.getScoreForSuperState();
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
		playerCharacter.swapState(new PlayerFireFlowerState(playerCharacter), true);
	}

	@Override
	public void unInitialize() {
		this.playerCharacter = null;
	}
	
}
