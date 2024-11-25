package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Parser.LevelCreator;
import Visuals.GraphicsObserver;
import Visuals.VisualController;

public class InputHandler implements KeyListener {

	VisualController visualController;
	LevelCreator levelCreator;
	
	int keyCode;
	
	public InputHandler(VisualController visualController) {
		this.visualController = visualController;
	}
	
	public void keyPressed(KeyEvent e) {
		keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_W)
			visualController.startUpPlayerMovement();
		if (keyCode == KeyEvent.VK_D)
			visualController.startRightPlayerMovement();
		if (keyCode == KeyEvent.VK_A)
			visualController.startLeftPlayerMovement();
		if (keyCode == KeyEvent.VK_SPACE)
			visualController.spaceAction();
		
		
		
		if (keyCode == KeyEvent.VK_ESCAPE) {
			visualController.resetScrollOffset();
			visualController.resetPlayerPosition();
			visualController.moveToLevelEnd();
		}
		if (keyCode == KeyEvent.VK_PERIOD)
			visualController.openLevelCreator();
		if (keyCode == KeyEvent.VK_2)
			if (visualController.getLevelCreator() != null)
				visualController.getLevelCreator().incrementOffset();
		if (keyCode == KeyEvent.VK_1)
			if (visualController.getLevelCreator() != null)
				visualController.getLevelCreator().decrementOffset();
		if (keyCode == KeyEvent.VK_4)
			if (visualController.getLevelCreator() != null)
				visualController.getLevelCreator().saveToFile();
		if (keyCode == KeyEvent.VK_3)
			if (visualController.getLevelCreator() != null)
				visualController.getLevelCreator().changeLevelNumber();
		if (keyCode == KeyEvent.VK_5)
			if (visualController.getLevelCreator() != null)
				visualController.getLevelCreator().changeEntityType();
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_D)
			visualController.stopRightPlayerMovement();
		
		if (keyCode == KeyEvent.VK_A)
			visualController.stopLeftPlayerMovement();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
