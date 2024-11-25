package Visuals;

import Game.GameEntity;
import Game.PlayerCharacter;

public interface VisualGameController {
	public Observer registerEntity(GameEntity gameEntity);
	public Observer registerPlayer(PlayerCharacter playerCharacter);
	public void showSplashScreen();
	public void setGameActive(boolean active);
	public void endGame(boolean success);
	public String getPlayerNameInput();
	public void openRankingWindow(boolean isFromGameEnd);
	public void resetScrollOffset();
	public void showLoadingLevelScreen();
	public void finishLoadingLevel();
	public void removeGameWindow();
	public void showGameEndPanel(boolean success);
	public void setNameInputted(boolean nameInputted);
}
