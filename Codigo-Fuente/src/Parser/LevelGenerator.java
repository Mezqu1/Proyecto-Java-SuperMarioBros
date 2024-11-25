package Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Enemies.Enemy;
import Factories.EntityFactory;
import Game.Level;
import Platforms.MysteryBlock;
import PowerUps.PowerUp;

public class LevelGenerator {
	
	protected Level level;
	
	protected EntityFactory entityFactory;
	protected String[] levelFileNames = {"Proyecto-Nivel-1.txt", "Proyecto-Nivel-2.txt", "Proyecto-Nivel-3.txt"};
	
	public LevelGenerator(Level level, EntityFactory entityFactory) {
		this.entityFactory = entityFactory;
		this.level = level;
		this.level.setLevelGenerator(this);
	}

	public void loadLevel(int levelNumber) {
		try {
			File file = new File(levelFileNames[levelNumber - 1]);
			BufferedReader bReader = new BufferedReader(new FileReader(file));
			String currentLine = bReader.readLine();
			while (currentLine != null) {
				parseLine(currentLine, 0, false);
				currentLine = bReader.readLine();
			}
			bReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parseLine(String currentLine, int offset, boolean isFromCreator) {
		String[] strings = currentLine.split(" ");
		
		
		int entityXPos = Integer.parseInt(strings[1]);
		int entityYPos = Integer.parseInt(strings[2]);
		if (isFromCreator) {
			entityXPos = entityXPos * 36 + offset * LevelCreator.COLS * 36;
			entityYPos = entityYPos * 36;
		}
		
		int entityType = Integer.parseInt(strings[0]);
		
		int containedEntityType = -1;
		
		if (strings.length == 4)
			containedEntityType = Integer.parseInt(strings[3]);
		
		addEntityFromData(entityType, entityXPos, entityYPos, containedEntityType);
	}

	public void addEntityFromData(int entityType, int entityXPos, int entityYPos, int containedEntityType) {

		switch (entityType){
			case 1: {
				level.addPlayerCharacter(entityFactory.getPlayerCharacter(entityXPos, entityYPos, level, entityFactory));
				break;
			}
			case 2: {
				level.addPlatform(entityFactory.getBrickBlock(entityXPos, entityYPos));
				break;
			}
			case 3: {
				level.addPlatform(entityFactory.getSolidBlock(entityXPos, entityYPos));
				break;
			}
			case 4: {
				MysteryBlock mysteryBlock = entityFactory.getMysteryBlock(entityXPos, entityYPos);
				PowerUp powerUpToAdd = getPowerUpToAdd(containedEntityType, entityXPos, entityYPos);
				mysteryBlock.setContainedPowerUp(powerUpToAdd);
				level.addPlatform(mysteryBlock);
				level.addPowerUp(powerUpToAdd);
				break;
			}
			case 5: {
				Platforms.Pipe pipe = entityFactory.getPipe(entityXPos, entityYPos);
				Enemy enemyToAdd = getEnemyToAdd(11, entityXPos, entityYPos);
				pipe.setContainedEnemy(enemyToAdd);
				level.addEnemy(enemyToAdd);
				level.addPlatform(entityFactory.getPipe(entityXPos, entityYPos));
				break;
			}
			case 6: {
				level.addPlatform(entityFactory.getFlag(entityXPos, entityYPos));
				break;
			}
			case 7: {
				level.addEnemy(entityFactory.getBuzzyBeetle(entityXPos, entityYPos));
				break;
			}
			case 8: {
				level.addEnemy(entityFactory.getGoomba(entityXPos, entityYPos));
				break;
			}
			case 9: {
				level.addEnemy(entityFactory.getKoopaTroopa(entityXPos, entityYPos, level, entityFactory));
				break;
			}
			case 10: {
				level.addEnemy(entityFactory.getLakitu(entityXPos, entityYPos, level, entityFactory));
				break;
			}
			case 12: {
				level.addEnemy(entityFactory.getSpiny(entityXPos, entityYPos));
				break;
			}
			case 18: {
				level.addProjectile(entityFactory.getFireballProjectile(entityXPos, entityYPos, level.getPlayerCharacter()));
				break;
			}
			case 19: {
				level.addProjectile(entityFactory.getShellProjectile(entityXPos, entityYPos, level.getPlayerCharacter()));
				break;
			}
		}
	}

	private PowerUp getPowerUpToAdd(int containedEntityType, int entityXPos, int entityYPos) {
		PowerUp newPowerUp = null;
		switch (containedEntityType) {
			case 13: {
				newPowerUp = entityFactory.getCoin(entityXPos, entityYPos);
				newPowerUp.setHasCollision(false);
				newPowerUp.setUsesGravity(false);
				newPowerUp.setVisible(false);
				break;
			}
			case 14: {
				newPowerUp = entityFactory.getFireFlower(entityXPos, entityYPos);
				newPowerUp.setHasCollision(false);
				newPowerUp.setUsesGravity(false);
				newPowerUp.setVisible(false);
				break;
			}
			case 15: {
				newPowerUp = entityFactory.getGreenMushroom(entityXPos, entityYPos);
				newPowerUp.setHasCollision(false);
				newPowerUp.setUsesGravity(false);
				newPowerUp.setVisible(false);
				break;
			}
			case 16: {
				newPowerUp = entityFactory.getStar(entityXPos, entityYPos);
				newPowerUp.setHasCollision(false);
				newPowerUp.setUsesGravity(false);
				newPowerUp.setVisible(false);
				break;
			}
			case 17: {
				newPowerUp = entityFactory.getSuperMushroom(entityXPos, entityYPos);
				newPowerUp.setHasCollision(false);
				newPowerUp.setUsesGravity(false);
				newPowerUp.setVisible(false);
				break;
			}
		}
		return newPowerUp;
	}
	
	private Enemy getEnemyToAdd(int containedEntityType, int entityXPos, int entityYPos) {
		Enemy enemyToAdd = null;
		if (containedEntityType == 11) {
			enemyToAdd = entityFactory.getPiranhaPlant(entityXPos + 18, entityYPos - 50);
			enemyToAdd.setHasCollision(false);
			enemyToAdd.setUsesGravity(false);
			enemyToAdd.setVisible(false);
		}
		
		return enemyToAdd;
	}
}
