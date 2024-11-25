package Factories;

import Game.Level;
import Game.PlayerCharacter;
import Visuals.Sprite;
import Platforms.*;
import Enemies.*;
import PowerUps.*;
import Projectiles.*;

public class EntityFactory {

	SpriteFactory spriteFactory;
	
	public EntityFactory(SpriteFactory spriteFactory) {
		this.spriteFactory = spriteFactory;
	}
	
	public PlayerCharacter getPlayerCharacter(float XPosition, float YPosition, Level level, EntityFactory entityFactory) {
		Sprite playerSprite = spriteFactory.getPlayerCharacterSprite();
		PlayerCharacter playerCharacter = new PlayerCharacter(playerSprite, XPosition, YPosition, level, entityFactory);
		return playerCharacter;
	}
	
	public BrickBlock getBrickBlock(float XPosition, float YPosition) {
		Sprite brickSprite = spriteFactory.getBrickBlockSprite();
		BrickBlock brickBlock = new BrickBlock(brickSprite, XPosition, YPosition);
		return brickBlock;
	}

	public SolidBlock getSolidBlock(float XPosition, float YPosition) {
		Sprite solidSprite = spriteFactory.getSolidBlockSprite();
		SolidBlock solidBlock = new SolidBlock(solidSprite, XPosition, YPosition);
		return solidBlock;
	}

	public MysteryBlock getMysteryBlock(float XPosition, float YPosition) {
		Sprite mysterySprite = spriteFactory.getMysteryBlockSprite();
		MysteryBlock mysteryBlock = new MysteryBlock(mysterySprite, XPosition, YPosition);
		return mysteryBlock;
	} 
	
	public Pipe getPipe(float XPosition, float YPosition) {
		Sprite pipeSprite = spriteFactory.getPipeSprite();
		Pipe pipe = new Pipe(pipeSprite, XPosition, YPosition);
		return pipe;
	}

	public Flag getFlag(float XPosition, float YPosition) {
		Sprite flagSprite = spriteFactory.getFlagSprite();
		Flag flag = new Flag(flagSprite, XPosition, YPosition);
		return flag;
	}
	
	public BuzzyBeetle getBuzzyBeetle(float XPosition, float YPosition) {
		Sprite buzzyBeetleSprite = spriteFactory.getBuzzyBeetleSprite();
		BuzzyBeetle buzzyBeetle = new BuzzyBeetle(buzzyBeetleSprite, XPosition, YPosition);
		return buzzyBeetle;
	}
	
	public Goomba getGoomba(float XPosition, float YPosition) {
		Sprite goombaSprite = spriteFactory.getGoombaSprite();
		Goomba goomba = new Goomba(goombaSprite, XPosition, YPosition);
		return goomba;
	}
	
	public KoopaTroopa getKoopaTroopa(float XPosition, float YPosition, Level level, EntityFactory entityFactory) {
		Sprite koopaTroopaSprite = spriteFactory.getKoopaTroopaSprite();
		KoopaTroopa koopaTroopa = new KoopaTroopa(koopaTroopaSprite, XPosition, YPosition, level, entityFactory);
		return koopaTroopa;
	}
	
	public Lakitu getLakitu(float XPosition, float YPosition, Level level, EntityFactory entityFactory) {
		Sprite lakituSprite = spriteFactory.getLakituSprite();
		Lakitu lakitu = new Lakitu(lakituSprite, XPosition, YPosition, level, entityFactory);
		return lakitu;
	}
	
	public PiranhaPlant getPiranhaPlant(float XPosition, float YPosition) {
		Sprite piranhaPlantSprite = spriteFactory.getPiranhaPlantSprite();
		PiranhaPlant piranhaPlant = new PiranhaPlant(piranhaPlantSprite, XPosition, YPosition);
		return piranhaPlant;
	}
	
	public Spiny getSpiny(float XPosition, float YPosition) {
		Sprite spinySprite = spriteFactory.getSpinySprite();
		Spiny spiny = new Spiny(spinySprite, XPosition, YPosition);
		return spiny;
	}
	
	public Coin getCoin(float XPosition, float YPosition) {
		Sprite coinSprite = spriteFactory.getCoinSprite();
		Coin coin = new Coin(coinSprite, XPosition, YPosition);
		return coin;
	}
	
	public FireFlower getFireFlower(float XPosition, float YPosition) {
		Sprite fireFlowerSprite = spriteFactory.getFireFlowerSprite();
		FireFlower fireFlower = new FireFlower(fireFlowerSprite, XPosition, YPosition);
		return fireFlower;
	}
	
	public GreenMushroom getGreenMushroom(float XPosition, float YPosition) {
		Sprite greenMushroomSprite = spriteFactory.getGreenMushroomSprite();
		GreenMushroom greenMushroom = new GreenMushroom(greenMushroomSprite, XPosition, YPosition);
		return greenMushroom;
	}
	
	public Star getStar(float XPosition, float YPosition) {
		Sprite starSprite = spriteFactory.getStarSprite();
		Star star = new Star(starSprite, XPosition, YPosition);
		return star;
	}
	
	public SuperMushroom getSuperMushroom(float XPosition, float YPosition) {
		Sprite superMushroomSprite = spriteFactory.getSuperMushroomSprite();
		SuperMushroom superMushroom = new SuperMushroom(superMushroomSprite, XPosition, YPosition);
		return superMushroom;
	}
	
	public FireballProjectile getFireballProjectile(float XPosition, float YPosition, PlayerCharacter playerCharacter) {
		Sprite fireballProjectileSprite = spriteFactory.getFireballProjectileSprite();
		FireballProjectile fireballProjectile = new FireballProjectile(fireballProjectileSprite, XPosition, YPosition, playerCharacter);
		return fireballProjectile;
	}
	
	public ShellProjectile getShellProjectile(float XPosition, float YPosition, PlayerCharacter playerCharacter) {
		Sprite shellProjectileSprite = spriteFactory.getShellProjectileSprite();
		ShellProjectile shellProjectile = new ShellProjectile(shellProjectileSprite, XPosition, YPosition, playerCharacter);
		return shellProjectile;
	}
}
