package Interface.Validation;

import java.awt.Color;
import java.awt.Font;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class YearVerifier extends InputVerifier {
	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField)input).getText();
		if (text.equals("")) { return true; }
		try {
			
			if (Integer.parseInt(text) >= 1970) {
				input.setForeground(Color.BLACK);
				return true; 
				}
			else { throw new NumberFormatException(); } 
		} catch (NumberFormatException e) {
			input.setForeground(Color.LIGHT_GRAY);
			return false;
		}
	}
}

