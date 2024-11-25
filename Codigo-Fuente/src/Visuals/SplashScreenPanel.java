package Visuals; 
import java.awt.Image; 
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO; 
import java.awt.*; 
import java.awt.event.*;

import javax.swing.*;

import Game.SoundManager;

public class SplashScreenPanel extends JPanel {

	private static final long serialVersionUID = -721792909315374693L;
	protected VisualController visualController;
	protected JLabel backgroundImageLabel;
	protected JButton startGameButton;
	protected JButton originalModeButton;
	protected JButton alternativeModeButton;
	protected JButton rankingButton;
	protected JLabel selectModeLabel;
	protected JLabel selectorCursor;
	private JButton selectedButton;

	public SplashScreenPanel(VisualController visualController) {
		this.visualController = visualController;
		setSize(VisualsConstants.PANEL_WIDTH, VisualsConstants.PANEL_HEIGHT);
		setLayout(null);
		setBackgroundImage();
		addStartGameButton();
		addSelectModeLabel();
		addOriginalModeButton();
		addAlternativeModeButton();
		addRankingButton();
		addSelectorCursor();
	}
	
	private void addSelectorCursor() {
		try {
			BufferedImage originalImage = ImageIO.read(this.getClass().getResource("/Textures/hongoCursor.png"));
        
			BufferedImage transparentImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics2D = transparentImage.createGraphics();
        
			graphics2D.drawImage(originalImage, 0, 0, null);
			graphics2D.dispose();
        
			Image scaledCursorImage = transparentImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			ImageIcon scaledCursorIcon = new ImageIcon(scaledCursorImage);
        
			selectorCursor = new JLabel(scaledCursorIcon) {
				private static final long serialVersionUID = 9153292344358268831L;

				@Override
	            public boolean isOpaque() {
	                return false;
	            }
			};
        
        	selectorCursor.setBounds(0, 0, scaledCursorIcon.getIconWidth(), scaledCursorIcon.getIconHeight());
        	backgroundImageLabel.add(selectorCursor);
        	hideCursor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void hideCursor() {
	        selectorCursor.setVisible(false);
	}
	
	private void showCursorAtButton(JButton button) { 
		if (button != null && selectorCursor != null) {
			if (button == alternativeModeButton) {
			    selectorCursor.setLocation(button.getX() + button.getWidth() + 5, button.getY() + (button.getHeight() - selectorCursor.getHeight()) / 2);
			} else {
			    selectorCursor.setLocation(button.getX() - selectorCursor.getWidth() - 5, button.getY() + (button.getHeight() - selectorCursor.getHeight()) / 2);
			}
			
			selectorCursor.setVisible(true);
		}
	}
	
	private void addMouseListenerToButton(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                showCursorAtButton(button);
                selectedButton = button;
            }
            public void mouseExited(MouseEvent e) {
                if (selectedButton == button) {
                    hideCursor();
                }
            }
        });
    }
	
	private void addStartGameButton() {
		startGameButton = new JButton();
		decorateStartGameButton();
		registerStartGameButtonListener(startGameButton);
		backgroundImageLabel.add(startGameButton);
		addMouseListenerToButton(startGameButton);
	}
	
	private void registerStartGameButtonListener(JButton button) {
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleButtonsVisibility();
				SoundManager.getInstance().playSound("click",false);
			}
		});
	}
	
	private void toggleButtonsVisibility() {
		startGameButton.setVisible(false);
		rankingButton.setVisible(false);
		selectModeLabel.setVisible(true);
		originalModeButton.setVisible(true);
		alternativeModeButton.setVisible(true);
	}
	
	private void setButtonTransparent(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
	}
	
	private void decorateStartGameButton() {
		ImageIcon buttonImage = new ImageIcon(this.getClass().getResource("/Textures/Start-game.png"));
		Image scaledBackgroundImage = buttonImage.getImage().getScaledInstance(288, 72, Image.SCALE_SMOOTH);
		Icon scaledIcon = new ImageIcon(scaledBackgroundImage);
		startGameButton.setIcon(scaledIcon);
		startGameButton.setBounds((VisualsConstants.PANEL_WIDTH / 2) - 150, VisualsConstants.PANEL_HEIGHT - 250, 288 , 72);
		setButtonTransparent(startGameButton);
	}
	
	private void addSelectModeLabel() {
		selectModeLabel = new JLabel("Select mode...");
		decorateSelectModeLabel();
		backgroundImageLabel.add(selectModeLabel);
	}
	
	private void decorateSelectModeLabel() {
		InputStream inputStream = GameScreenPanel.class.getResourceAsStream("/Fonts/PressStart2P-Regular.ttf");
		Font customFont;
        try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(15f);		
			selectModeLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar la fuente personalizada.");
        }finally {
        	selectModeLabel.setBounds((VisualsConstants.PANEL_WIDTH / 2) - 85, VisualsConstants.PANEL_HEIGHT - 225, 300, 15);
			selectModeLabel.setForeground(Color.BLACK);
			selectModeLabel.setVisible(false);
        }
	}
	
	private void addOriginalModeButton() {
		originalModeButton = new JButton();
		decorateOriginalModeButton();
		registerOriginalModeButtonListener(originalModeButton);
		backgroundImageLabel.add(originalModeButton);
		addMouseListenerToButton(originalModeButton);
	}
	
	private void registerOriginalModeButtonListener(JButton button) {
		originalModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualController.startGame("Original");
				SoundManager.getInstance().playSound("click", false);
			}
		});
	}
	
	private void decorateOriginalModeButton() {
		ImageIcon buttonImage = new ImageIcon(this.getClass().getResource("/Textures/botonOriginal2.png"));
		Image scaledBackgroundImage = buttonImage.getImage().getScaledInstance(160, 53, Image.SCALE_SMOOTH);
		Icon scaledIcon = new ImageIcon(scaledBackgroundImage);
		originalModeButton.setIcon(scaledIcon);
		originalModeButton.setBounds((VisualsConstants.PANEL_WIDTH / 2) - 170, VisualsConstants.PANEL_HEIGHT - 193, 160 , 53);
		setButtonTransparent(originalModeButton);
		originalModeButton.setVisible(false);
	}
	
	private void addAlternativeModeButton() {
		alternativeModeButton = new JButton();
		decorateAlternativeModeButton();
		registerAlternativeModeButtonListener(alternativeModeButton);
		backgroundImageLabel.add(alternativeModeButton);
		addMouseListenerToButton(alternativeModeButton);
	}
	
	private void registerAlternativeModeButtonListener(JButton button) {
		alternativeModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualController.startGame("Alternative");
				SoundManager.getInstance().playSound("click", false);
			}
		});
	}
	
	private void decorateAlternativeModeButton() {
		ImageIcon buttonImage = new ImageIcon(this.getClass().getResource("/Textures/botonAlternative2.png"));
		Image scaledBackgroundImage = buttonImage.getImage().getScaledInstance(190, 65, Image.SCALE_SMOOTH);
		Icon scaledIcon = new ImageIcon(scaledBackgroundImage);
		alternativeModeButton.setIcon(scaledIcon);
		alternativeModeButton.setBounds((VisualsConstants.PANEL_WIDTH / 2) + 10, VisualsConstants.PANEL_HEIGHT - 200, 190 , 65);
		setButtonTransparent(alternativeModeButton);
		alternativeModeButton.setVisible(false);
	}
	
	private void addRankingButton() {
		rankingButton = new JButton();
		decorateRankingButton();
		registerRankingButtonListener(rankingButton);
		backgroundImageLabel.add(rankingButton);
		addMouseListenerToButton(rankingButton);
	}
	
	private void registerRankingButtonListener(JButton button) {
		rankingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundManager.getInstance().playSound("click", false);
				visualController.openRankingWindow(false);
			}
		});
	}
	
	private void decorateRankingButton() {
		ImageIcon buttonImage = new ImageIcon(this.getClass().getResource("/Textures/ranking.png"));
		Image scaledBackgroundImage = buttonImage.getImage().getScaledInstance(129, 45, Image.SCALE_SMOOTH);
		Icon scaledIcon = new ImageIcon(scaledBackgroundImage);
		rankingButton.setIcon(scaledIcon);
		rankingButton.setBounds((VisualsConstants.PANEL_WIDTH/2) - 70, VisualsConstants.PANEL_HEIGHT - 150, 129 , 45);
		setButtonTransparent(rankingButton);
	}
	
	private void setBackgroundImage() {
	    ImageIcon backgroundImage = new ImageIcon(this.getClass().getResource("/Textures/TdpBrosTitleScreen.png"));
	    Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(VisualsConstants.PANEL_WIDTH, VisualsConstants.PANEL_HEIGHT, Image.SCALE_SMOOTH);
	    Icon scaledIcon = new ImageIcon(scaledBackgroundImage);
	    backgroundImageLabel = new JLabel();
	    backgroundImageLabel.setIcon(scaledIcon);
	    backgroundImageLabel.setBounds(0, 0, VisualsConstants.PANEL_WIDTH, VisualsConstants.PANEL_HEIGHT);
	    backgroundImageLabel.setLayout(null); 
	    add(backgroundImageLabel); 
	}
	
	public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
	    Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
	    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
	    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
	    return outputImage;
	}
}