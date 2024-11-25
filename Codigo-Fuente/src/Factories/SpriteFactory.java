package Factories;

import Visuals.Sprite;

public abstract class SpriteFactory {
	
	String texturePath;
	
	public SpriteFactory(String path){
		this.texturePath = path;
	}
	
	public Sprite getPlayerCharacterSprite() {
		return new Sprite(texturePath + "Mario/");
	}
	
	public Sprite getBrickBlockSprite() {
		return new Sprite(texturePath + "Platforms/");
	}

	public Sprite getSolidBlockSprite() {
		return new Sprite(texturePath + "Platforms/");
	}

	public Sprite getMysteryBlockSprite() {
		return new Sprite(texturePath + "Platforms/");
	}

	public Sprite getPipeSprite() {
		return new Sprite(texturePath + "Platforms/");
	}

	public Sprite getFlagSprite() {
		return new Sprite(texturePath + "Platforms/");
	}

	public Sprite getBuzzyBeetleSprite() {
		return new Sprite(texturePath + "Enemies/");
	}

	public Sprite getGoombaSprite() {
		return new Sprite(texturePath + "Enemies/");
	}

	public Sprite getKoopaTroopaSprite() {
		return new Sprite(texturePath + "Enemies/");
	}

	public Sprite getLakituSprite() {
		return new Sprite(texturePath + "Enemies/");
	}

	public Sprite getPiranhaPlantSprite() {
		return new Sprite(texturePath + "Enemies/");
	}

	public Sprite getSpinySprite() {
		return new Sprite(texturePath + "Enemies/");
	}

	public Sprite getCoinSprite() {
		return new Sprite(texturePath + "PowerUps/");
	}

	public Sprite getFireFlowerSprite() {
		return new Sprite(texturePath + "PowerUps/");
	}

	public Sprite getGreenMushroomSprite() {
		return new Sprite(texturePath + "PowerUps/");
	}

	public Sprite getStarSprite() {
		return new Sprite(texturePath + "PowerUps/");
	}

	public Sprite getSuperMushroomSprite() {
		return new Sprite(texturePath + "PowerUps/");
	}
	
	public Sprite getFireballProjectileSprite() {
		return new Sprite(texturePath + "Projectiles/");
	}

	public Sprite getShellProjectileSprite() {
		return new Sprite(texturePath + "Projectiles/");
	}
	
}
