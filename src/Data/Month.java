package Data;

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
	NOVERMBER (11),
	DECEMBER (12);
	
	private int value;
	private Month(int value) { this.value = value; }
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

}
