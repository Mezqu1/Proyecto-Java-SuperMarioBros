package Visuals;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GameScrollPanel extends JScrollPane {
	
	public GameScrollPanel(JPanel gamePanel) {
		super(gamePanel);
	}

	public GameScrollPanel(JLabel label) {
		super(label);
	}
	public void addScrollOffset(int offset) {
		getHorizontalScrollBar().setValue(getHorizontalScrollBar().getValue() + offset);
	}
}
