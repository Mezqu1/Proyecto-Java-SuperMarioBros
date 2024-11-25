package Visuals;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Game.SoundManager;

public class GameEndPanel extends JPanel {
	private static final long serialVersionUID = 1776387285454816993L;
	protected VisualController visualController;
	protected JLabel backgroundImageLabel;
	
	public GameEndPanel(VisualController visualController) {
		this.visualController = visualController;
		setSize(VisualsConstants.PANEL_WIDTH, VisualsConstants.PANEL_HEIGHT);
		setLayout(null);
		setBackgroundImage();
	}

	private void setBackgroundImage() {
		ImageIcon backgroundImage = new ImageIcon(this.getClass().getResource("/Textures/BlackScreen.png"));
		Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(VisualsConstants.PANEL_WIDTH, VisualsConstants.PANEL_HEIGHT, Image.SCALE_SMOOTH);
		Icon scaledIcon = new ImageIcon(scaledBackgroundImage);
		backgroundImageLabel = new JLabel();
		backgroundImageLabel.setIcon(scaledIcon);
		backgroundImageLabel.setBounds(0, 0, VisualsConstants.PANEL_WIDTH, VisualsConstants.PANEL_HEIGHT);
		add(backgroundImageLabel);
	}

	void setText(String gameEndString) {
        try {
    		JTextArea loadingText = new JTextArea(gameEndString);
    		loadingText.setFont(createFont().deriveFont(20f));
    		backgroundImageLabel.add(loadingText);
    		setupLoadingText(loadingText);
    		SoundManager.getInstance().stopSound("background");
    		if (!gameEndString.contains("WIN"))
    			SoundManager.getInstance().playSound("game over",false);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        } 	
	}

	private void setupLoadingText(JTextArea loadingText) {
		loadingText.setBounds(300, 100, 300, 100);
		loadingText.setForeground(Color.WHITE);
		loadingText.setOpaque(false);
		loadingText.setEditable(false);
	}

	private Font createFont() throws IOException, FontFormatException {
		Font customFont = null;
		InputStream inputStream  = GameScreenPanel.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
	    if (inputStream  == null) {
	        throw new IOException("No se encontró el archivo de fuente.");
	    }
	    customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream) ;
	    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    graphicsEnvironment.registerFont(customFont);
	     
	    return customFont;
	}
}
