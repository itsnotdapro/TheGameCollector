package Interface.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JRadioButton;

public class RatingListener implements ActionListener {
	private JRadioButton[] ratingButtons;
	private int ratingValue = 1;
	
	public void setValue(int value) { 
		List<JRadioButton> list = Arrays.asList(ratingButtons);
		ratingValue = value; 
		for (JRadioButton btn : ratingButtons) {
			if (list.indexOf(btn) + 1 <= ratingValue) { btn.setSelected(true); }
			else { btn.setSelected(false); }
		}
	}
	public int getValue() { return ratingValue; }
	
	public RatingListener(JRadioButton[] ratingButtons) {
		this.ratingButtons = ratingButtons;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		List<JRadioButton> list = Arrays.asList(ratingButtons);
		ratingValue = list.indexOf((JRadioButton)e.getSource()) + 1;
		for (JRadioButton btn : ratingButtons) {
			if (list.indexOf(btn) + 1 <= ratingValue) { btn.setSelected(true); }
			else { btn.setSelected(false); }
		}
		
	}
}
