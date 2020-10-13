package Interface.Listeners;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Data.Month;
import Interface.Application;
import Interface.GamePanel;
import Library.Game;
import Library.Platform;

public class SelectionListener extends MouseAdapter {
	private GamePanel selectedGamePanel;
	private Game selectedGame;
	private JButton deleteButton, addButton;
	private JPanel fieldsPanel;
	private Application instance;
	
	public SelectionListener(Application instance) { 
		this.instance = instance;
		deleteButton = instance.getDeleteButton(); 
		addButton = instance.getAddButton();
		fieldsPanel = instance.getFieldsPanel();
	}
	public GamePanel getSelectedPanel() { return selectedGamePanel; }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		selectedGamePanel = (GamePanel)e.getSource();
		selectedGame = selectedGamePanel.getGame();
    	for (GamePanel panel : instance.getGamePanels()) {
    		panel.setTitleBorder();
    	}
    	deleteButton.setEnabled(true);
    	selectedGamePanel.setSelectedBorder();
    	
    	Calendar purchaseDateOfSelected = Calendar.getInstance();
    	purchaseDateOfSelected.setTime(selectedGame.getPurchase());
    	
    	instance.getNameField().setText(selectedGame.getTitle());
    	instance.getPriceField().setText(selectedGame.getPriceAsString());
    	instance.getPlatformField().setSelectedItem((selectedGame.getPlatform()));
    	instance.getDayField().setText(Integer.toString(purchaseDateOfSelected.get(Calendar.DATE)));
    	instance.getMonthField().setSelectedItem(Month.valueOf(purchaseDateOfSelected.get(Calendar.MONTH)));
    	instance.getYearField().setText(Integer.toString(purchaseDateOfSelected.get(Calendar.YEAR)));
    	instance.getRatingListener().setValue(selectedGame.getRating());
    	instance.getPhysicalField().setSelected(selectedGame.getPhysical());

    	
    	addButton.setText("Update");
    	fieldsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Update Game", TitledBorder.RIGHT, 0, new Font("Tahoma", Font.BOLD, 30)));
    	fieldsPanel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), fieldsPanel.getBorder()));
    	
    	instance.checkAllFields();
	}
	
	public void deselect() {
		for (GamePanel gp : instance.getGamePanels()) {
			gp.setTitleBorder();
        	deleteButton.setEnabled(false);
        	selectedGamePanel = null;
        	selectedGame = null;
        }
		
		instance.getNameField().setText("");
    	instance.getPriceField().setText("");
    	instance.getPlatformField().setSelectedItem(Platform.PC);
    	instance.getDayField().setText("");
    	instance.getMonthField().setSelectedItem(Month.JANUARY);
    	instance.getYearField().setText("");
    	instance.getRatingListener().setValue(1);
    	instance.getPhysicalField().setSelected(false);
		
		addButton.setText("Add");
		fieldsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Add Game", TitledBorder.RIGHT, 0, new Font("Tahoma", Font.BOLD, 30)));
		fieldsPanel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), fieldsPanel.getBorder()));
		
		instance.checkAllFields();
	}
}
