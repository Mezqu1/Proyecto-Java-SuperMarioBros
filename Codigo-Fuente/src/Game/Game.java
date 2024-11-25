package Game;

import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Enemies.Enemy;
import EntityStates.PlayerNormalState;
import Factories.*;
import Parser.LevelGenerator;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;
import Threads.NonPlayerEntitiesThread;
import Threads.PlayerCharacterThread;
import Threads.RenderThread;
import Visuals.*;

public class Game{
	
	public static final float MAX_GRAVITY = 10;
	public static final float TILE_SIZE = 36;
	public static float LEVEL_END = 4770;
	
	public static int currentMinX = 0;
	
	public static boolean debug = true;
	
	protected VisualGameController visualController;
	
	protected Ranking ranking;
	protected Level level;
	protected int currentLevel;
	protected LevelGenerator levelGenerator;
	protected SpriteFactory spriteFactory;
	protected EntityFactory entityFactory;
	
	protected PlayerCharacterThread playerCharacterGameLoop;
	protected NonPlayerEntitiesThread nonPlayerEntitiesGameLoop;
	protected RenderThread renderThread;
	private String selectedGameMode;
	
	private boolean gameEnded;
	
	private boolean loadingLevel;
	
	public Game(){
		ranking = new Ranking();
		this.loadingLevel = false;
		this.gameEnded = false;
	}
	
	public void setVisualController(VisualController visualController) {
		this.visualController = visualController;
	}
	
	public void startGame() {
		initializeFactories();
		initializeLevel();
		initializeThreads();
		registerObservers(true);
		startGameLoop();
		
		visualController.setGameActive(true);
	}

	private void initializeFactories() {
		if (selectedGameMode == "Original")
			spriteFactory = new OriginalSpriteFactory();
		else
			spriteFactory = new AlternativeSpriteFactory();
		entityFactory = new EntityFactory(spriteFactory);
	}

	private void initializeThreads() {
		playerCharacterGameLoop = new PlayerCharacterThread(this, level.getPlayerCharacter());
		nonPlayerEntitiesGameLoop = new NonPlayerEntitiesThread(this);
		renderThread = new RenderThread(this);
	}

	private void initializeLevel() {
		level = new Level(this);
		levelGenerator = new LevelGenerator(level, entityFactory);
		SoundManager.getInstance().playSound("background",true);
		this.currentLevel = 1;
		levelGenerator.loadLevel(currentLevel);
		level.startLevelTimer(currentLevel);
	}

	public void startGameLoop() {
		playerCharacterGameLoop.start();
		nonPlayerEntitiesGameLoop.start();
		renderThread.start();
	}

	private void registerObservers(boolean registerPlayerObserver) {
		if (registerPlayerObserver)
			registerPlayerObserver(level.getPlayerCharacter());
		registerPowerUpObservers();
		registerPlatformObservers();
		registerEnemyObservers();
		registerProjectileObservers();
	}
	
	public void registerPlatformObservers() {
		Platform [][] platforms = level.getPlatforms();
		for (int i = 0; i < platforms.length; i++) {
			for (int j = 0; j < platforms[0].length; j++) {
				if (platforms[i][j] != null)
					registerEntityObserver(platforms[i][j]);
			}
		}
	}

	public void registerEnemyObservers() {
		for (GameEntity gameEntity : getEnemiesCopy()) {
			registerEntityObserver(gameEntity);
		}
	}

	public void registerPowerUpObservers() {
		for (GameEntity gameEntity : getPowerUpsCopy()) {
			registerEntityObserver(gameEntity);
		}
	}

	public void registerProjectileObservers() {
		for (GameEntity gameEntity : getProjectilesCopy()) {
			registerEntityObserver(gameEntity);
		}
	}
	
	public void registerPlayerObserver(PlayerCharacter playerCharacter) {
		if (playerCharacter != null) {
			Observer playerObserver = visualController.registerPlayer(playerCharacter);
			level.getPlayerCharacter().attachObserver(playerObserver);
		}
	}
	
	public void registerEntityObserver(GameEntity entity) {
		if (entity.getObserver() == null) {
			Observer entityObserver = visualController.registerEntity(entity);
			entity.attachObserver(entityObserver);
		}
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Ranking getRanking() {
		return ranking;
	}

	public void startUpPlayerMovement() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		if (playerCharacter != null) {
			level.getPlayerCharacter().startUpMovement();
		}
	}
	
	public void startRightPlayerMovement() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		if (playerCharacter != null)
			level.getPlayerCharacter().startRightMovement();
	}

	public void startLeftPlayerMovement() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		if (playerCharacter != null)
			level.getPlayerCharacter().startLeftMovement();
	}

	public void stopRightPlayerMovement() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		if (playerCharacter != null)
			level.getPlayerCharacter().stopRightMovement();
	}

	public void stopLeftPlayerMovement() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		if (playerCharacter != null)
			level.getPlayerCharacter().stopLeftMovement();
	}

	public void resetPlayerPosition() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		if (playerCharacter != null) {
			level.getPlayerCharacter().setXPosition(0);
			level.getPlayerCharacter().setYPosition(300);
		}
		visualController.resetScrollOffset();
	}

	public void endGame(boolean success) {
		if (!gameEnded) {
			this.gameEnded = true;
			level.destroyLevel();
			checkScoreAndProcessRanking(success);
		}
	}
	
	private void checkScoreAndProcessRanking(boolean success) {
		visualController.showGameEndPanel(success);
		int currentScore = level.getPlayerCharacter().getScore();
		if (ranking.belongsToTop5(currentScore)) {
			String playerName = visualController.getPlayerNameInput();
			if(playerName != null && !playerName.isEmpty()) {
				ranking.addPlayerToTop5(playerName, currentScore);
				visualController.openRankingWindow(true);
				visualController.setNameInputted(true);
			}
		}
		visualController.endGame(success);
	}

	public List<Enemy> getEnemiesCopy() {
		List<Enemy> returnList = new ArrayList<>();
		returnList.addAll(level.getEnemies());
		return returnList;
	}

	public List<PowerUp> getPowerUpsCopy() {
		List<PowerUp> returnList = new ArrayList<>();
		returnList.addAll(level.getPowerUps());
		return returnList;
	}

	public List<Projectile> getProjectilesCopy() {
		List<Projectile> returnList = new ArrayList<>();
		returnList.addAll(level.getProjectiles());
		return returnList;
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public void spaceAction() {
		if (level != null && level.getPlayerCharacter() != null)
			level.getPlayerCharacter().doSpaceAction();
	}

	public void startLevelLoadingThread() {
		if (currentLevel == 3)
			endGame(true);
		else {
			partiallyPausePlayerCharacter();
			loadingLevel = true;
			LevelLoader levelLoader = new LevelLoader();
			levelLoader.start();
		}
	}

	private void partiallyPausePlayerCharacter() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		playerCharacter.setXVelocity(0);
		playerCharacter.setInputEnabled(false);
	}

	private void unpausePlayerCharacter() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		resetPlayerPosition();
		playerCharacter.setUsesGravity(true);
		playerCharacter.setHasCollision(true);
		playerCharacter.setVisible(true);
		playerCharacter.setInputEnabled(true);
	}

	private void pausePlayerCharacter() {
		PlayerCharacter playerCharacter = level.getPlayerCharacter();
		playerCharacter.setUsesGravity(false);
		playerCharacter.setHasCollision(false);
		playerCharacter.setVisible(false);
		playerCharacter.setInputEnabled(false);
	}
	
	private class LevelLoader extends Thread {
		
		@Override
		public void run() {
			try {
				sleep(1000);
				visualController.showLoadingLevelScreen();
				destroyCurrentLevel();
				sleep(1000);
				startLoadingLevel();
				sleep(1000);
				finishLoadingLevel();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		private void finishLoadingLevel() {
			unpausePlayerCharacter();
			visualController.finishLoadingLevel();
			SoundManager.getInstance().playSound("background",true);
			loadingLevel = false;
		}

		private void startLoadingLevel() {
			levelGenerator.loadLevel(currentLevel);
			level.startLevelTimer(currentLevel);
			registerObservers(false);
		}

		private void destroyCurrentLevel() {
			pausePlayerCharacter();
			level.destroyLevel();
			currentLevel++;
			if (currentLevel == 2)
				LEVEL_END = 6770;
			if(currentLevel == 3)
				LEVEL_END = 7164;
		}
	}

	public void setGameMode(String selectedGameMode) {
		this.selectedGameMode = selectedGameMode;
	}
	
	public boolean getLoadingLevel() {
		return this.loadingLevel;
	}

	public void moveToLevelEnd() {
		if (level != null && level.getPlayerCharacter() != null)
			level.getPlayerCharacter().setXPosition(LEVEL_END);
	}
	
}
