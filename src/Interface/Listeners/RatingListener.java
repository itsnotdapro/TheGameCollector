package Interface.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

public class RatingListener implements ActionListener {
	private JRadioButton rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9, rate10;
	private int ratingValue = 1;
	
	public void setValue(int value) { ratingValue = value; }
	public int getValue() { return ratingValue; }
	
	public RatingListener(JRadioButton rate1, JRadioButton rate2, JRadioButton rate3, JRadioButton rate4, JRadioButton rate5, 
						  JRadioButton rate6, JRadioButton rate7, JRadioButton rate8, JRadioButton rate9, JRadioButton rate10) {
		this.rate1 = rate1;
		this.rate2 = rate2;
		this.rate3 = rate3;
		this.rate4 = rate4;
		this.rate5 = rate5;
		this.rate6 = rate6;
		this.rate7 = rate7;
		this.rate8 = rate8;
		this.rate9 = rate9;
		this.rate10 = rate10;	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(ratingValue);
		if (e.getSource().equals(rate1)) {
			rate1.setSelected(true);
			rate2.setSelected(false);
			rate3.setSelected(false);
			rate4.setSelected(false);
			rate5.setSelected(false);
			rate6.setSelected(false);
			rate7.setSelected(false);
			rate8.setSelected(false);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 1;
		}
		if (e.getSource().equals(rate2)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(false);
			rate4.setSelected(false);
			rate5.setSelected(false);
			rate6.setSelected(false);
			rate7.setSelected(false);
			rate8.setSelected(false);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 2;
		}
		if (e.getSource().equals(rate3)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(false);
			rate5.setSelected(false);
			rate6.setSelected(false);
			rate7.setSelected(false);
			rate8.setSelected(false);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 3;
		}
		if (e.getSource().equals(rate4)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(false);
			rate6.setSelected(false);
			rate7.setSelected(false);
			rate8.setSelected(false);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 4;
		}
		if (e.getSource().equals(rate5)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(true);
			rate6.setSelected(false);
			rate7.setSelected(false);
			rate8.setSelected(false);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 5;
		}
		
		if (e.getSource().equals(rate6)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(true);
			rate6.setSelected(true);
			rate7.setSelected(false);
			rate8.setSelected(false);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 6;
		}
		
		if (e.getSource().equals(rate7)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(true);
			rate6.setSelected(true);
			rate7.setSelected(true);
			rate8.setSelected(false);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 7;
		}
				
		if (e.getSource().equals(rate8)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(true);
			rate6.setSelected(true);
			rate7.setSelected(true);
			rate8.setSelected(true);
			rate9.setSelected(false);
			rate10.setSelected(false);
			ratingValue = 8;
			
		}
		
		if (e.getSource().equals(rate9)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(true);
			rate6.setSelected(true);
			rate7.setSelected(true);
			rate8.setSelected(true);
			rate9.setSelected(true);
			rate10.setSelected(false);
			ratingValue = 9;
		}
		
		if (e.getSource().equals(rate10)) {
			rate1.setSelected(true);
			rate2.setSelected(true);
			rate3.setSelected(true);
			rate4.setSelected(true);
			rate5.setSelected(true);
			rate6.setSelected(true);
			rate7.setSelected(true);
			rate8.setSelected(true);
			rate9.setSelected(true);
			rate10.setSelected(true);
			ratingValue = 10;
		}
	}

}
