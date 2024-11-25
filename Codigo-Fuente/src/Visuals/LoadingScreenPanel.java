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

public class LoadingScreenPanel extends JPanel {
	private static final long serialVersionUID = 8084012835414932208L;
	protected VisualController visualController;
	protected JLabel backgroundImageLabel;
	
	public LoadingScreenPanel(VisualController visualController) {
		this.visualController = visualController;
		setSize(VisualsConstants.PANEL_WIDTH, VisualsConstants.PANEL_HEIGHT);
		setLayout(null);
		setBackgroundImage();
		setLoadingText();
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
	
	private void setLoadingText() {
		Font customFont = null;
        try {
            InputStream inputStream  = GameScreenPanel.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (inputStream  == null) {
                throw new IOException("No se encontró el archivo de fuente.");
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream) ;
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(customFont);
    		JTextArea loadingText = new JTextArea("LOADING LEVEL");
    		loadingText.setFont(customFont.deriveFont(20f));
    		backgroundImageLabel.add(loadingText);
    		loadingText.setBounds(250, 250, 300, 100);
    		loadingText.setForeground(Color.WHITE);
    		loadingText.setOpaque(false);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        } 	
	}
}
