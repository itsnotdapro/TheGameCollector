package Interface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionListener extends MouseAdapter {
	private GamePanel selectedGame;
	
	public GamePanel getSelected() { return selectedGame; }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		selectedGame = (GamePanel)e.getSource();
    	for (GamePanel panel : selectedGame.getAllPanels()) {
    		panel.setTitleBorder();
    	}
    	selectedGame.setSelectedBorder();
	}
}
