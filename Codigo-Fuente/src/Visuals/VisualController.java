package Visuals;

import javax.swing.*;

import Game.*;
import Parser.LevelCreator;

public class VisualController implements VisualGameController {
	
	protected Game game;
	protected JFrame gameWindow;
	protected SplashScreenPanel splashScreenPanel;
	protected GameScreenPanel gameScreenPanel;
	protected InputHandler playerInputHandler;
	protected LevelCreator levelCreator;
	
	protected boolean gameIsActive;
	private LoadingScreenPanel loadingScreenPanel;
	private GameEndPanel gameEndPanel;
	private boolean nameInputted;
	
	
	public VisualController(Game game) {
		this.game = game;
		this.splashScreenPanel = new SplashScreenPanel(this);
		this.gameScreenPanel = new GameScreenPanel(this);
		this.loadingScreenPanel = new LoadingScreenPanel(this);
		this.gameEndPanel = new GameEndPanel(this);
		this.gameIsActive = false;
		this.nameInputted = false;
		initializeWindow();
		registerInputHandler();
	}
	
	private void registerInputHandler() {
		playerInputHandler = new InputHandler(this);
		gameWindow.addKeyListener(playerInputHandler);
	}

	private void initializeWindow() {
		gameWindow = new JFrame("TdP Comision 30");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setResizable(false);
		gameWindow.setSize(VisualsConstants.WINDOW_WIDTH, VisualsConstants.WINDOW_HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		gameWindow.setFocusable(true);
	}

	public void showSplashScreen() {
		gameWindow.setContentPane(splashScreenPanel);
		refresh();
	}
	
	public void openRankingWindow(boolean isFromGameEnd) {
		RankingScreenPanel rankingScreenPanel = new RankingScreenPanel(game.getRanking());
		JFrame rankingDialog =  new JFrame("Ranking");
		if (isFromGameEnd)
			rankingDialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		else
			rankingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    rankingDialog.setSize(300, 400);
	    rankingDialog.setLocationRelativeTo(null);
	    rankingDialog.add(rankingScreenPanel);
	    rankingDialog.setVisible(true);
	}

	public String getPlayerNameInput() {
		String returnString = "";
		if (!nameInputted) {
			PlayerNameInputPanel playerNameInputPanel = new PlayerNameInputPanel();
	        JDialog dialog = new JDialog((JFrame) null, "", true);
	        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        dialog.getContentPane().add(playerNameInputPanel);
	        dialog.pack();
	        dialog.setLocationRelativeTo(null);
	        dialog.setVisible(true);
	        returnString = playerNameInputPanel.getPlayerInput();
		}
        return returnString;
	}

	public void refresh() {
		if (gameWindow != null) {
			gameWindow.revalidate();
			gameWindow.repaint();	
		} else {
			System.out.println(System.nanoTime() +": Game Window Null");
		}
	}

	public void startGame(String selectedGameMode) {
		refresh();
		game.setGameMode(selectedGameMode);
		game.startGame();
		gameWindow.setContentPane(gameScreenPanel);
	}

	public Observer registerPlayer(PlayerCharacter playerCharacter) {
		Observer playerObserver = gameScreenPanel.addHudToScreen(playerCharacter);
		return playerObserver;
	}
	
	public Observer registerEntity(GameEntity entity) {
		Observer entityObserver = gameScreenPanel.addEntityToScreen(entity);
		return entityObserver;
	}

	public void startUpPlayerMovement() {
		if(gameIsActive)
			game.startUpPlayerMovement();
	}
	
	public void startRightPlayerMovement() {
		if (gameIsActive)
			game.startRightPlayerMovement();
	}

	public void startLeftPlayerMovement() {
		if (gameIsActive)
			game.startLeftPlayerMovement();
	}

	public void stopRightPlayerMovement() {
		if (gameIsActive)
			game.stopRightPlayerMovement();
	}

	public void stopLeftPlayerMovement() {
		if (gameIsActive)
			game.stopLeftPlayerMovement();
	}

	public void resetPlayerPosition() {
		if (gameIsActive)
			game.resetPlayerPosition();
	}

	public void setGameActive(boolean active) {
		gameIsActive = active;
	}

	public void resetScrollOffset() {
		gameScreenPanel.resetScrollOffset();
		Game.currentMinX = 0;
	}

	public void openLevelCreator() {
		Level level = game.getLevel();
		if (level != null) {
			levelCreator = new LevelCreator(level, game, playerInputHandler);
			levelCreator.addButtonsToLevelCreator();
		}
		
	}
	
	public LevelCreator getLevelCreator() {
		return levelCreator;
	}

	@Override
	public void endGame(boolean success) {
		GameEndTimer gameEndTimer = new GameEndTimer(success);
		gameEndTimer.start();
	}

	public static void setScrollOffset(int value) {
		Game.currentMinX = value;
	}

	public void spaceAction() {
		game.spaceAction();
	}

	@Override
	public void showLoadingLevelScreen() {
		gameWindow.setContentPane(loadingScreenPanel);
		SoundManager.getInstance().stopSound("background");
		SoundManager.getInstance().playSound("level completed",false);
	}

	@Override
	public void finishLoadingLevel() {
		gameWindow.setContentPane(gameScreenPanel);
	}
	
	private class GameEndTimer extends Thread {
		
		boolean success;
		
		public GameEndTimer(boolean success) {
			this.success = success;
		}
		
		@Override
		public void run() {
			try {
				sleep(3000);
				if (!success) {
					gameWindow.dispose();
					System.exit(0);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void showGameEndPanel(boolean success) {
		if (success)
			gameEndPanel.setText("YOU WIN!");
		else
			gameEndPanel.setText("GAME OVER");
		gameWindow.setContentPane(gameEndPanel);
	}
	
	public void moveToLevelEnd() {
		game.moveToLevelEnd();
	}

	@Override
	public void removeGameWindow() {
		gameWindow.dispose();
	}

	@Override
	public void setNameInputted(boolean nameInputted) {
		this.nameInputted = nameInputted;
	}

	
}
