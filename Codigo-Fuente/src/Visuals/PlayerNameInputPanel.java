package Visuals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PlayerNameInputPanel extends JPanel {
	
	private static final long serialVersionUID = 6203250079333155758L;
	private JTextField nameTextField;
	private String playerName;
	
	public PlayerNameInputPanel() {
		nameTextField = new JTextField(6);
        add(new JLabel("Enter name (max 6 digits):"));
        add(nameTextField);

        nameTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameTextField.getText().trim();
                if (playerName.isEmpty() || playerName.length() > 6) {
                    JOptionPane.showMessageDialog(PlayerNameInputPanel.this, "Please enter a valid name.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    SwingUtilities.getWindowAncestor(PlayerNameInputPanel.this).dispose();
                }
            }
        });
    }

    public String getPlayerInput() {
        return playerName;
    }
}