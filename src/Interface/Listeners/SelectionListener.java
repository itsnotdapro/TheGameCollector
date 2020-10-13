package Interface.Listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import Interface.GamePanel;

public class SelectionListener extends MouseAdapter {
	private GamePanel selectedGame;
	private JButton deleteButton;
	
	public SelectionListener(JButton button) { deleteButton = button; }
	public GamePanel getSelected() { return selectedGame; }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		selectedGame = (GamePanel)e.getSource();
    	for (GamePanel panel : selectedGame.getAllPanels()) {
    		panel.setTitleBorder();
    	}
    	deleteButton.setEnabled(true);
    	selectedGame.setSelectedBorder();
	}
}
