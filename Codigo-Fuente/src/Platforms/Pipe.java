package Platforms;

import Enemies.Enemy;
import Enemies.PiranhaPlant;
import Visuals.Sprite;

public class Pipe extends Platform {

	PiranhaPlant piranhaPlant;
	PiranhaTimer timer;
	
    public Pipe(Sprite entitySprite, float xPosition, float yPosition) {
		super(entitySprite, xPosition, yPosition);
		this.width = 72;
		this.height = 72;
		piranhaPlant = null;
		this.timer = new PiranhaTimer();
	}

	public void setContainedEnemy(Enemy enemyToAdd) {
		this.piranhaPlant = (PiranhaPlant) enemyToAdd;
		timer.start();
	}
	
	private class PiranhaTimer extends Thread {
		@Override
		public void run() {
			try {
				while(piranhaPlant != null) {
					if (!piranhaPlant.getDestroyed()) {
						sleep(3000);
						showPiranhaPlant();
						sleep(3000);
						hidePiranhaPlant();
					} else {
						piranhaPlant = null;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void hidePiranhaPlant() {
			piranhaPlant.show();
		}

		private void showPiranhaPlant() {
			piranhaPlant.hide();
		}
	}
	
	@Override
	public void initializeImagePath() {
		entitySprite.setCurrentImagePath(entitySprite.getBaseImagePath().concat("PipeWide.png"));
	}

	@Override
	public int getTypeForLevelCreator() {
		return 5;
	}
}
