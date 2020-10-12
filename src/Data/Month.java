package Data;

public enum Month {
	JANUARY,
	FEBURARY,
	MARCH,
	APRIL,
	MAY,
	JUNE,
	JULY,
	AUGUST,
	SEPTEMBER,
	OCTOBER,
	NOVERMBER,
	DECEMBER;
	
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
