package Library;

import java.util.Comparator;

public class Sort {

	public static Comparator<Game> get(SortingMethod method) {
		switch(method) {
		case NAME:
			return new Comparator<Game>() {
				@Override
				public int compare(Game g1, Game g2) {
					return g1.getTitle().compareTo(g2.getTitle());
				}
			};
		case PRICE_HIGHEST:
			return new Comparator<Game>() {
				@Override
				public int compare(Game g1, Game g2) {
					return (int)((g2.getPrice() - g1.getPrice()) * 100);
				}
			};
		case PRICE_LOWEST:
			return new Comparator<Game>() {
				@Override
				public int compare(Game g1, Game g2) {
					return (int)((g1.getPrice() - g2.getPrice()) * 100);
				}
			};
		case RELEASE_FIRST:
			return new Comparator<Game>() {
				@Override
				public int compare(Game g1, Game g2) {
					return g2.getRelease().compareTo(g1.getRelease());
				}
			};
		case RELEASE_LAST:
			return new Comparator<Game>() {
				@Override
				public int compare(Game g1, Game g2) {
					return g1.getRelease().compareTo(g2.getRelease());
				}
			};
		case RATING_LOWEST:
			return new Comparator<Game>() {
				@Override
				public int compare(Game g1, Game g2) {
					return g1.getRating() - g2.getRating();
				}
			};
		case RATING_HIGHEST:
			return new Comparator<Game>() {
				@Override
				public int compare(Game g1, Game g2) {
					return g2.getRating() - g1.getRating();
				}
			};
		}
		return null;
	}
}
