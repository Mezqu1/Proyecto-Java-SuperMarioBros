package Visuals;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Hud extends JPanel {
    private static final long serialVersionUID = -1685706086644648957L;
	private JLabel pointsLabel;
    private JLabel livesLabel;
    private JLabel levelLabel;
    private JLabel timeLabel;

    public Hud() {
        Font customFont = null;
        try {
            InputStream inputStream  = GameScreenPanel.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (inputStream  == null) {
                throw new IOException("No se encontró el archivo de fuente.");
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream) ;
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
        setOpaque(false);
        setupPointsLabel(customFont);
        setupLivesLabel(customFont);
        setupLevelLabel(customFont);
        setupTimeLabel(customFont);
        addLabels();
    }
    
    private void addLabels() {
    	add(pointsLabel);
        add(livesLabel);
        add(levelLabel);
        add(timeLabel);
	}
    
	private void setupTimeLabel(Font customFont) {
    	timeLabel = new JLabel();
        timeLabel.setFont(customFont.deriveFont(20f));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBounds(550, -20, 300, 80);
	}
	
	private void setupLevelLabel(Font customFont) {
    	 levelLabel = new JLabel();
         levelLabel.setFont(customFont.deriveFont(20f));
         levelLabel.setForeground(Color.WHITE);
         levelLabel.setBounds(350, -20, 200, 80);
	}
	
	private void setupLivesLabel(Font customFont) {
    	livesLabel = new JLabel();
        livesLabel.setFont(customFont.deriveFont(20f));
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setBounds(140, -20, 200, 80);
	}
	
	private void setupPointsLabel(Font customFont) {
    	pointsLabel = new JLabel();
        pointsLabel.setFont(customFont.deriveFont(20f));
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setBounds(10, -20, 200, 80);
	}
	
	public void updatePosition(int PosX, int PosY) {
        setLocation(PosX, PosY);
        revalidate();
        repaint();
    }

    public void updateScore(int points) {
        pointsLabel.setText(" " + points + " ");
       
    }

    public void updateLives(int lives) {
        livesLabel.setText("LIVES " + lives);
    }
    
    public void updateLevel(int level) {
    	levelLabel.setText("LEVEL "+ level);
    }
    public void updateTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        timeLabel.setText(String.format("TIME %02d:%02d", minutes, secs));
       
        timeLabel.revalidate();
        timeLabel.repaint();
        
    }
 
}