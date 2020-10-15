package Interface.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JRadioButton;

/** Listener that handles updating the 10 rating radio buttons
 * @author 19076935 */
public class RatingListener implements ActionListener {
	private JRadioButton[] ratingButtons;
	private int ratingValue = 1;
	
	/** Sets the currently selected rating value 
	 * @param value The value to set the rating input to
	 * @author 19076935 */
	public void setValue(int value) { 
		ratingValue = value; 
		updateButtons();
	}
	
	/** Gets the currently selected value
	 * @return The currently selected value
	 * @author 19076935 */
	public int getValue() { return ratingValue; }
	
	/** Instantiates the rating listener
	 * @param ratingButtons The array of radio buttons that the listener handles\
	 * @author 19076935 */
	public RatingListener(JRadioButton[] ratingButtons) {
		this.ratingButtons = ratingButtons;
	}
	
	/** Updates all the buttons to the currently selected value
	 * @author 19076935 */
	private void updateButtons() {
		List<JRadioButton> list = Arrays.asList(ratingButtons);
		for (JRadioButton btn : ratingButtons) {
			if (list.indexOf(btn) + 1 <= ratingValue) { btn.setSelected(true); }
			else { btn.setSelected(false); }
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		List<JRadioButton> list = Arrays.asList(ratingButtons);
		ratingValue = list.indexOf((JRadioButton)e.getSource()) + 1;
		updateButtons();
	}
}
