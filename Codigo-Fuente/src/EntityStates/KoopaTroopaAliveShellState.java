package EntityStates;


import Enemies.KoopaTroopa;

public class KoopaTroopaAliveShellState extends KoopaTroopaState {
	
	KoopaReviveTimer reviveTimer;
	
	public KoopaTroopaAliveShellState(KoopaTroopa koopaTroopa) {
		super(koopaTroopa);
		this.reviveTimer = new KoopaReviveTimer();
	}

	@Override
	public void hitFromAbove() {
		koopaTroopa.createShellProjectile();
		koopaTroopa = null;
	}

	@Override
	public void initialize() {
		koopaTroopa.setXVelocity(0);
		koopaTroopa.setWidth(36);
		koopaTroopa.setHeight(36);
		setShellSprite();
		startReviveTimer();
	}

	private void startReviveTimer() {
		reviveTimer.start();
	}

	private void setShellSprite() {
		koopaTroopa.getSprite().setCurrentImagePath(koopaTroopa.getSprite().getBaseImagePath().concat("KoopaShellAlive.png"));
	}

	@Override
	public void setAnimatedSprite() {
		
	}
	
	private class KoopaReviveTimer extends Thread {
		@Override
		public void run() {
			try {
				sleep(5000);
				if (koopaTroopa != null) {
					koopaTroopa.swapState(new NormalKoopaTroopaState(koopaTroopa));
					koopaTroopa = null;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
