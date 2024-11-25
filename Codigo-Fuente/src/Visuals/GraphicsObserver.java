package Visuals;

import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Game.GameEntity;

public abstract class GraphicsObserver extends JLabel implements Observer {
	
	public static boolean debug = false;
	
	GameEntity observedEntity;

	private String lastImagePath;
	
	public GraphicsObserver(GameEntity observedEntity) {
		super();
		this.observedEntity = observedEntity;
		this.lastImagePath = "";
	}
	
	public void update() {
		if (observedEntity != null && !observedEntity.getDestroyed()) {
			boolean entityIsVisible = observedEntity.isVisible();
			if (entityIsVisible) {
				updateImage();
				updatePositionAndSize();
			}
			setVisible(entityIsVisible);
		} else {
			removeGraphicsObserver();
			
		}
	}
	
	private void removeGraphicsObserver() {
		observedEntity = null;
		Container parent = getParent();
		if (parent != null) {
			parent.remove(this);
			parent.validate();
			parent.repaint();
		}
	}

	protected void updatePositionAndSize() {
		if (observedEntity != null && getIcon() != null) {
			int x = (int) observedEntity.getXPosition();
			int y = (int) observedEntity.getYPosition();
			int ancho = this.getIcon().getIconWidth();
			int alto = this.getIcon().getIconHeight();
			setBounds(x, y, ancho, alto);
		}
	}

	protected void updateImage() {
		if (observedEntity != null) {
			String imagePath = observedEntity.getSprite().getCurrentImagePath();
			if (debug || getClass().getClassLoader().getResource(imagePath) == null) {
				imagePath = "Textures/whiteBackground.jpg";
			}
			if (imagePath != lastImagePath) {
				lastImagePath = imagePath;
				ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
				if (debug)
					icon.setImage(icon.getImage().getScaledInstance((int) observedEntity.getWidth(), (int) observedEntity.getHeight(), Image.SCALE_SMOOTH));
				setIcon(icon);
			}
		}
	}
}
