package EntityStates;

import Enemies.Enemy;
import Game.PlayerCharacter;
import PowerUps.PowerUp;
import Visuals.Sprite;

public class PlayerNormalState extends PlayerPowerUpState {

	
	public PlayerNormalState(PlayerCharacter playerCharacter) {
		super(playerCharacter);
	}

	public void notifyStateChange(PlayerPowerUpState newState) {
		
	}
	
	@Override
	public void initialize() {
		this.playerCharacter.setHeight(36);
		this.playerCharacter.setWidth(36);
		Sprite playerSprite = playerCharacter.getSprite();
		this.stateTexturePath = playerSprite.getBaseImagePath() + "NormalState/";
		setStandingSprite();
	}

	@Override
	public void hitEnemyFromAbove(Enemy enemy) {
		playerCharacter.stopDownwardsMovement();
		playerCharacter.startUpMovement();
		enemy.receiveHitFromAbove(playerCharacter);
	}
	
	public void ranIntoEnemy(Enemy enemy) {
		playerCharacter.reduceLives();
		playerCharacter.addScore(enemy.getScoreForDyingTo());
	}

	@Override
	public void applySuperMushroom() {
		playerCharacter.swapState(new PlayerSuperState(playerCharacter), true);
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
	public int getScoreForCurrentState(PowerUp powerUp) {
		return powerUp.getScoreForNormalState();
	}

	@Override
	public void unInitialize() {
		this.playerCharacter = null;
	}

}
