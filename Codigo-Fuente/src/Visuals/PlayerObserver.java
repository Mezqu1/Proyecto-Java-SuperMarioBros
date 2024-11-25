package Visuals;

import Game.Game;
import Game.GameEntity;
import Game.PlayerCharacter;

public class PlayerObserver extends GraphicsObserver {

    private static final long serialVersionUID = -7145708747695866570L;
	PlayerCharacter playerCharacter;
    Hud playerHud;
	 
	public PlayerObserver(GameEntity observedEntity, Hud playerHud) {
		super(observedEntity);
		playerCharacter = (PlayerCharacter) observedEntity;
		this.playerHud = playerHud;
	}
	
	public void updatePositionAndSize() {
		super.updatePositionAndSize();
		updateScrollOffset();
		
	}
	
    private void updateScrollOffset() {
    	if (observedEntity.getXVelocity() > 0) {
			GameScrollPanel scrollPanel = (GameScrollPanel)	getParent().getParent().getParent().getParent();
			if (observedEntity.getXPosition() - scrollPanel.getHorizontalScrollBar().getValue() > VisualsConstants.PANEL_WIDTH / 2) {
				scrollPanel.addScrollOffset((int) observedEntity.getXVelocity());
				VisualController.setScrollOffset(scrollPanel.getHorizontalScrollBar().getValue());
			}
		}
	}

	public void update() {
        super.update();
        updateHud();
	}
    

	public void updateHud() {
		playerHud.updateLevel(playerCharacter.getLevel().getGame().getCurrentLevel());
		playerHud.updateLives(playerCharacter.getLives());
		playerHud.updateScore(playerCharacter.getScore());
		playerHud.updatePosition(Game.currentMinX, 0);
		playerHud.updateTime(playerCharacter.getLevel().getRemainingTime());
	}
}
