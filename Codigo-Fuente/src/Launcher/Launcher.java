package Launcher;

import java.awt.EventQueue;

import Game.Game;
import Visuals.VisualController;
import Game.SoundLoader;

public class Launcher {
    
    public static void main (String args[])
    {
        initialize();
    }

    public static void initialize() {
    	EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				Game game = new Game();
    		    	VisualController visualController = new VisualController(game);
    		    	SoundLoader soundLoader = new SoundLoader();
    		    	game.setVisualController(visualController);
    		    	visualController.showSplashScreen();
    		    	soundLoader.loadAllSounds();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	});
    }
 
}
