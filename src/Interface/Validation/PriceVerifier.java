package Interface.Validation;

import java.awt.Color;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/** Verifier to check the price field
 * @author 19076935 */
public class PriceVerifier extends InputVerifier {
	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField)input).getText();
		if (text.equals("")) { return true; }
		try {
			Float.parseFloat(text);
			input.setForeground(Color.BLACK);
			return true;
		} catch (NumberFormatException e) {
			input.setForeground(Color.LIGHT_GRAY);
			return false;
		}
	}
}
