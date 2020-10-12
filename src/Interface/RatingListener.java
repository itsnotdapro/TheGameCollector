package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

public class RatingListener implements ActionListener {
	private JRadioButton rate1, rate2, rate3, rate4, rate5;
	
	public RatingListener(JRadioButton rate1, JRadioButton rate2, JRadioButton rate3, JRadioButton rate4, JRadioButton rate5) {
		this.rate1 = rate1;
		this.rate2 = rate2;
		this.rate3 = rate3;
		this.rate4 = rate4;
		this.rate5 = rate5;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(rate1)) {
			rate1.setSelected(true);
			rate2.setSelected(false);
			rate3.setSelected(false);
			rate4.setSelected(false);
			rate5.setSelected(false);
		}
		if (e.getSource().equals(rate2)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(false);
			rate4.setSelected(false);
			rate5.setSelected(false);
		}
		if (e.getSource().equals(rate3)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(false);
			rate5.setSelected(false);
		}
		if (e.getSource().equals(rate4)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(false);
		}
		if (e.getSource().equals(rate5)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(true);
		}
	}

}
