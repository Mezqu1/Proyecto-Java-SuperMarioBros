package Threads;

import Game.Game;

public abstract class UpdateThread extends Thread {
	protected Game game;
	protected int updatesPerSecond = 60;
	
	public UpdateThread(Game game) {
		this.game = game;
	}
	
	public void run() {
		double currentTime;
		double loopTimePassed;
		double remainingLoopTime;
		double gameLoopTime = 1000000000/updatesPerSecond;
		while (true) {
			currentTime = System.nanoTime();
			update();
			loopTimePassed = System.nanoTime() - currentTime;
			remainingLoopTime = gameLoopTime - loopTimePassed;
			try {
				if (remainingLoopTime > 0)
					sleep((long) remainingLoopTime/1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected abstract void update();
	
	
}
