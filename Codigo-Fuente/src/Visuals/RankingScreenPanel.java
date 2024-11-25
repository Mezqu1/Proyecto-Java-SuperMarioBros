package Visuals;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.util.List;
import Game.Player;
import Game.Ranking;

public class RankingScreenPanel extends JPanel {
	
	private static final long serialVersionUID = 2296036526828757090L;
	private JPanel rankingListPanel;
	private Font customFont;
	private Image backgroundImage;
	
	public RankingScreenPanel(Ranking ranking) {
		loadBackgroundImage();
		loadCustomFont();
		configurePanel();
        addTitle();
        createRankingList(ranking.getTop5());
	}
	
	private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("src/Textures/rankingBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar la imagen de fondo.");
        }
    }
	
	private void loadCustomFont() {
        try {
        	InputStream inputStream = GameScreenPanel.class.getResourceAsStream("/Fonts/PressStart2P-Regular.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(24f); 
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);  
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar la fuente personalizada.");
            customFont = new Font("Serif", Font.PLAIN, 20); 
        }
    }	
	
	private void configurePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        rankingListPanel = new JPanel();
        rankingListPanel.setOpaque(false); 
    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("Ranking", SwingConstants.CENTER);
        titleLabel.setFont(customFont.deriveFont(24f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 
        add(titleLabel, BorderLayout.NORTH);
    }
    
    private void createRankingList(List<Player> topPlayers) {
    	decorateRankingList(topPlayers);
    	int playerPosition = 0;
        for (Player player : topPlayers) {
        	
        	JLabel playerLabel = new JLabel("  "+(++playerPosition)+".  "+player.getName());
        	decorateLabel(playerLabel);
        	rankingListPanel.add(playerLabel);
        	
            JLabel scoreLabel = new JLabel(String.valueOf(player.getScore()), SwingConstants.CENTER);
            decorateLabel(scoreLabel);
            rankingListPanel.add(scoreLabel);
                        
        }
        add(rankingListPanel);
    }
    
    private void decorateRankingList(List<Player> topPlayers) {
        rankingListPanel.setLayout(new GridLayout(topPlayers.size(), 2, 10, 5));
        rankingListPanel.setBackground(new Color(0, 0, 0, 0));
    }
    
    private void decorateLabel(JLabel label) {
    	label.setFont(customFont.deriveFont(11f));
    	label.setForeground(Color.WHITE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    
}
