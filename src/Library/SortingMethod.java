package Library;

public enum SortingMethod {
	NAME ("Name", false),
	PURCHASE_FIRST ("Purchase Date (Earliest)", false),
	PURCHASE_LAST ("Purchase Date (Latest)", false),
	RELEASE_FIRST ("Release (Latest)", true),
	RELEASE_LAST ("Release (Earliest)", true),
	RATING_HIGHEST ("Rating (Highest)", false),
	RATING_LOWEST ("Rating (Lowest)", false),
	PRICE_HIGHEST ("Pice (Highest)", false),
	PRICE_LOWEST ("Price (Lowest)", false),
	DEVELOPER ("Developer", true),
	METACRITIC ("Metacritic Score", true);
	
	private String readableName;
	private boolean requiresData;
	private SortingMethod(String readableName, boolean requiresData) { 
		this.readableName = readableName; 
		this.requiresData = requiresData;
		}
	
	public static SortingMethod fromIndex(int i) {
		for (SortingMethod method : SortingMethod.values()) {
			if (method.ordinal() == i) { return method; }
		}
		return null;
	}
	
	@Override
	public String toString() { return readableName; }
	public boolean requiresData() { return requiresData; }
}
