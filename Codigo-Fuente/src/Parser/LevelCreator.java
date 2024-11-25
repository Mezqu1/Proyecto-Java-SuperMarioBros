
package Parser;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import Game.Game;
import Game.InputHandler;
import Game.Level;
import Platforms.Platform;

public class LevelCreator{
	private JFrame creatorWindow;
	public static final int ROWS = 16, COLS = 20, SIZE = 10, BORDER = 0;
	private int offset;
	private JButton [][] buttons;
	
	private Level level;
	private LevelGenerator levelGenerator;
	private Game game;
	
	private int currentEntityType = 2;
	int levelNumber = 1;

	public LevelCreator(Level level, Game game, InputHandler inputHandler) {
		
		this.level = level;
		this.levelGenerator = level.getLevelGenerator();
		this.game = game;
		createLevelCreatorWindow();
		buttons = new JButton [ROWS][COLS];
		
		creatorWindow.addKeyListener(inputHandler);
	}
	
	private void createLevelCreatorWindow() {
		creatorWindow = new JFrame("Level Creator: 2: Increment offset, 1: Decrement offset");
		creatorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		creatorWindow.setResizable(true);
		creatorWindow.setSize(700, 700);
		creatorWindow.setLocationRelativeTo(null);
		creatorWindow.setVisible(true);
		creatorWindow.setFocusable(true);
		creatorWindow.setLayout(new GridLayout(ROWS, COLS, SIZE, BORDER));
		Point windowLocation = creatorWindow.getLocation();
		creatorWindow.setLocation((int) windowLocation.getX() - 700, (int) windowLocation.getY());
	}

	public void addButtonsToLevelCreator() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				String buttonText = j + " " + i;
				JButton newButton = new JButton(buttonText);
				creatorWindow.add(newButton);
				buttons[i][j] = newButton;
				registerButtonListener(newButton);
				newButton.setBackground(Color.BLACK);
			}
		}
	}
	
	public void incrementOffset() {
		offset++;
		System.out.println("New Offset: " + offset * LevelCreator.COLS * 36);
		
	}

	public void decrementOffset() {
		if (offset > 0) {
			offset--;
			System.out.println("New Offset: " + offset * LevelCreator.COLS * 36);
		}
	}
	
	private String removeLastChar(String str) {
	    if(str != null && !str.trim().isEmpty()) {
	        return str.substring(0, str.length() - 1);
	    }
	    return "";
	}

	private void registerButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createEntity(button);
				game.registerPlatformObservers();
				button.setBackground(Color.GREEN);
			}

			private void createEntity(JButton button) {
				
			    String[] buttonPosition = button.getText().split(" ");
			    
			    if (buttonPosition.length != 2) {
			        System.out.println("Error: Formato de posición del botón no válido - " + button.getText());
			        return;
			    }

			    String newEntityText = currentEntityType + " " + buttonPosition[0] + " " + buttonPosition[1];
			    
			    if (currentEntityType == 4)
			    	newEntityText = newEntityText.concat(" " + 15);
			    if (currentEntityType == 5)
			    	newEntityText = newEntityText.concat(" " + 11);
			    
			    levelGenerator.parseLine(newEntityText, offset, true);
			}
		});
	}
	
	public  void changeEntityType() {
		if(this.currentEntityType>=2 || this.currentEntityType<10)
			this.currentEntityType++;
		if(this.currentEntityType==10)
			this.currentEntityType=12;
		if(this.currentEntityType==12)
			this.currentEntityType=2;
		printCurrentEntityType();
	}
	
	private void printCurrentEntityType() {
		switch(currentEntityType) {
			case 2: {
				System.out.println("Current Entity : BrickBlock ");
				break;
			}
			case 3: {
				System.out.println("Current Entity : SolidBlock");
				break;
			}
			case 4: {
				System.out.println("Current Entity : MysteryBox");
				break;
			}
			case 5: {
				System.out.println("Current Entity : Pipe");
				break;
			}
		}
	}

	public void saveToFile() {
	    System.out.println("Saving to file");
	    Platform[][] platforms = level.getPlatforms();
	    BufferedWriter writer = null;
	    try {
	        writer = new BufferedWriter(new FileWriter("Proyecto-Nivel-" + levelNumber + ".txt"));
	        
	        writer.write("1 100 400");
	        
	        for (int j = 0; j < platforms[0].length; j++) {
	            for (int i = 0; i < platforms.length; i++) {
	                
	                Platform nextPlatform = platforms[i][j];
	                
	                if (nextPlatform != null) {
	                    int platformType = nextPlatform.getTypeForLevelCreator();
	                    int platformXPos = (int) nextPlatform.getXPosition();
	                    int platformYPos = (int) nextPlatform.getYPosition();
	                    
	                    String stringToSave = platformType + " " + platformXPos + " " + platformYPos;
	                    writer.write("\n" + stringToSave);
	                    System.out.println(stringToSave);
	                }
	            }
	        }
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void changeLevelNumber() {
		if (levelNumber == 3) {
			levelNumber = 1;
		} else {
			levelNumber++;
		}
		System.out.println("New Level number: " + levelNumber);
	}
}


