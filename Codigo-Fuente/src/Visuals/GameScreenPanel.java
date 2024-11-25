package Visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Game.GameEntity;
import Game.PlayerCharacter;
public class GameScreenPanel extends JPanel {
	
	private static final long serialVersionUID = 8964651860724411145L;

	protected VisualController visualController;
	
	protected JLabel gameBackgroundLabel;
	protected GameScrollPanel gameScrollPanel;
	protected JPanel gamePanel;
	protected JPanel hudPanel;
	protected JLabel pointsLabel;
	protected JLabel nombre;
	protected Hud hud;
	protected ImageIcon backgroundImage;
	
	public GameScreenPanel(VisualController visualController) {
	        this.visualController = visualController;
	        setSize(VisualsConstants.GAME_SCREEN_PANEL_WIDTH, VisualsConstants.GAME_SCREEN_PANEL_HEIGHT);
	        setLayout(new BorderLayout());
	        setup();
	}
	
	private void setup() {
		setupBackground();
		setupGamePanel();
		setupScrollPanel();
	}

	private void setupScrollPanel() {
		gameScrollPanel = new GameScrollPanel(gamePanel);
		gameScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		gameScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		gameScrollPanel.setBounds(0, 0, backgroundImage.getIconWidth(), VisualsConstants.GAME_SCREEN_PANEL_HEIGHT);
		gameScrollPanel.setBackground(new Color(94, 149, 251));
	    gameScrollPanel.setBorder(BorderFactory.createEmptyBorder());
		add(gameScrollPanel, BorderLayout.CENTER);
	}
	
	private void setupGamePanel() {
		gamePanel = new JPanel(null);
		gamePanel.setPreferredSize(new Dimension(backgroundImage.getIconWidth(), VisualsConstants.GAME_SCREEN_PANEL_HEIGHT));
		gamePanel.setBackground(new Color(94, 149, 251));
	    gamePanel.setBorder(BorderFactory.createEmptyBorder());
		gamePanel.add(gameBackgroundLabel);
	}
	
	private void setupBackground() {
		gameBackgroundLabel = new JLabel();
		backgroundImage = new ImageIcon(this.getClass().getResource("/Textures/marioBackgroundExtended.png"));
		Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(backgroundImage.getIconWidth(), VisualsConstants.GAME_SCREEN_PANEL_HEIGHT, Image.SCALE_SMOOTH);
		Icon scaledIcon = new ImageIcon(scaledBackgroundImage);
		gameBackgroundLabel.setIcon(scaledIcon);
		gameBackgroundLabel.setLayout(null);
		gameBackgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), VisualsConstants.GAME_SCREEN_PANEL_HEIGHT);
		gameBackgroundLabel.setBackground(new Color(94, 149, 251));
	    gameBackgroundLabel.setBorder(BorderFactory.createEmptyBorder());
	}

	public Observer addEntityToScreen(GameEntity entity) {
		EntityObserver entityObserver= new EntityObserver(entity);
		gameBackgroundLabel.add(entityObserver);	
		return entityObserver;
	}

	public Observer addHudToScreen(PlayerCharacter playerCharacter) {

	    Hud playerHud = new Hud();
	    
	    PlayerObserver playerObserver = new PlayerObserver(playerCharacter, playerHud);
	    gameBackgroundLabel.add(playerObserver);
	    
	    gameBackgroundLabel.add(playerHud, 0);
	    playerHud.setVisible(true);
	    
	    playerHud.setPreferredSize(new Dimension(backgroundImage.getIconWidth(), 55));
	    playerHud.setBounds(0, 0, backgroundImage.getIconWidth(), 55); //
	    playerHud.setOpaque(false);

	    gameBackgroundLabel.revalidate();
	    gameBackgroundLabel.repaint();
		
	    return playerObserver;
	}

	public void resetScrollOffset() {
		gameScrollPanel.getHorizontalScrollBar().setValue(0);
	}
	
}
