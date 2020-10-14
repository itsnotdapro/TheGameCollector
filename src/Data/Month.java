package Data;

/** Enumerated type of the month of a year.  
 * @author 19076935 */
public enum Month {
	JANUARY (1),
	FEBURARY (2),
	MARCH (3),
	APRIL (4),
	MAY (5),
	JUNE (6),
	JULY (7),
	AUGUST (8),
	SEPTEMBER (9),
	OCTOBER (10),
	NOVEMBER (11),
	DECEMBER (12);
	
	private int value;
	/** Month constructor
	 * @param value The number of the month as on a calendar
	 * @author 19076935  */
	private Month(int value) { this.value = value; }
	
	/** Method to get the calendar value of the month
	 * @return Calendar value of the month
	 * @author 19076935 */
	public int getValue() { return value; }
	
	@Override
	public String toString() {
		String name = super.toString();
		String output = "";
		boolean first = true;
		for (char c : name.toCharArray()) {
			if (first) {
				output += c;
				first = false;
			} else {
				output += (char)(c - ('A' - 'a')); 
			}
		}
		return output;
	}
	
	/** Gets the Month type of a specified calendar value
	 * @param i The calendar number to return the Month of
	 * @return Month with that value
	 * @author 19076935 */
	public static Month valueOf(int i) {
		for (Month month : Month.values()) {
			if (month.value == i) { return month; }
		}
		return null;
	}
}
