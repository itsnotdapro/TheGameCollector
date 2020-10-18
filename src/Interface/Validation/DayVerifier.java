package Interface.Validation;

import java.awt.Color;

import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import Data.Month;

/** Verifier to check the day input field
 * @author 19076935 */
public class DayVerifier extends InputVerifier {
	private JComboBox<Month> monthComboBox;
	/** Constructor to instantiate the verifier
	 * @param monthComboBox The month field combo box
	 * @author 19076935 */
	public DayVerifier(JComboBox<Month> monthComboBox) {
		this.monthComboBox = monthComboBox;
	}
	
	@Override
	public boolean verify(JComponent input) {
		if (((JTextField)input).getText().equals("")) { return true; }
		Month month = (Month)monthComboBox.getSelectedItem();
		try {
			int num = Integer.parseInt(((JTextField)input).getText());
			if (month == Month.SEPTEMBER || month == Month.APRIL || month == Month.JUNE || month == Month.NOVEMBER) {
				if (num > 30 || num < 1) { throw new NumberFormatException(); }
			} else if (month == Month.FEBURARY) {
				if (num > 29 || num < 1) { throw new NumberFormatException(); }
			} else {
				if (num > 31 || num < 1) { throw new NumberFormatException(); }
			}
			input.setForeground(Color.BLACK);
			return true;
		} catch (NumberFormatException e) {
			input.setForeground(Color.LIGHT_GRAY);
			return false;
		}
	}
}
