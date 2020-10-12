package Library;

public enum SortingMethod {
	NAME ("Name"),
	RELEASE_FIRST ("Release (Latest)"),
	RELEASE_LAST ("Release (Earliest)"),
	RATING_HIGHEST ("Rating (Highest)"),
	RATING_LOWEST ("Rating (Lowest)"),
	PRICE_HIGHEST ("Pice (Highest)"),
	PRICE_LOWEST ("Price (Lowest)");
	
	private String readableName;
	private SortingMethod(String readableName) { this.readableName = readableName; }
	
	@Override
	public String toString() { return readableName; }
}
