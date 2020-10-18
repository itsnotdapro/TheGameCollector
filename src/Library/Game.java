package Library;

import Data.Json;
import Exceptions.InvalidDateFormatException;
import Exceptions.InvalidPlatformException;
import Exceptions.Log;
import Data.InfoRetrieval;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JProgressBar;

/** An object that stores the information of a game
 * @author 19076935 */
@SuppressWarnings("serial")
public class Game extends InfoRetrieval implements Serializable {
    private String title;
    /** @return the title of the game @author 19076935 */
    public String getTitle() { return title; }
    /** @param title The title to set @author 19076935 */
    public void setTitle(String title) { this.title = title; }

    // If there was arithmetic that needed to be done on the prices, it would be safer to store them as integers, but there isn't so it's not
    private float price;
    /** @return the price of the game @author 19076935 */
    public float getPrice() { return price; }
    /** @param price The price to set @author 19076935 */
    public void setPrice(float price) { this.price = price; }
    /** @return the price as a string for UI use @author 19076935 */
    public String getPriceAsString() {
    	DecimalFormat df = new DecimalFormat();
    	df.setMinimumFractionDigits(2);
    	df.setMaximumFractionDigits(2);
    	return df.format(price);
    }

    private Platform platform;
    /** @return The platform of the game @author 19076935*/
    public Platform getPlatform() { return platform; }
    /** @param platform The platform to set @author 19076935 */
    public void setPlatform(Platform platform) { this.platform = platform; }
    
    private boolean physical;
    /** @return If the game is a physical release @author 19076935*/
    public boolean getPhysical() { return physical; }
    /** @param physical Whether the game is physical or not @author 19076935 */
    public void setPhysical(boolean physical) { this.physical = physical; } 
    
    private Date purchased;
    /** @return The purchase date of the game @author 19076935 */
    public Date getPurchase() { return purchased; }
    /** @param purchased The purchase date of the game @author 19076935 */
    public void setPurchase(Date purchased) { this.purchased = purchased; }

    private Date release;
    /** @return The release date of the game @author 19076935 */
    public Date getRelease() { return release; }
    /** @param release The release date of the game @author 19076935 */
    public void setRelease(Date release) { this.release = release; }
    
    private int rating;
    /** @return The rating of the game @author 19076935 */
    public int getRating() { return rating; }
    /** @param rating The rating of the game @author 19076935 */
    public void setRating(int rating) { this.rating = rating; } 
   
    private ArrayList<String> genres;
    /** @return the genres of the game @author 19076935 */
    public ArrayList<String> getGenres() { return this.genres; }
    /** @param genres The genres of the game @author 19076935 */
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    private Json APIData;
    private Json APIResults;
    /** Gets an API result
     * @param key The result to get
     * @return The result from the key
     * @author 19076935 */
    public Object getResult(String key) { return APIResults.get(key); }
    /** @return The API results @author 19076935 */
    public Json getResults() { return APIResults; }
    /** @param APIResults The API results @author 19076935 */
    public void setResults(Json APIResults) { 
    	this.APIResults = APIResults;   	
    	if (!APIResults.isEmpty()) { parseData(); } 	
    }

    /** Constructor when all parameters are known
     * @param title The title of the game
     * @param price The price of the game
     * @param platform The platform of the game
     * @param purchase The purchase date of the game
     * @param rating the rating of the game
     * @param physical Whether or not the game is a physical release
     * @author 19076935 */
    public Game(String title, float price, Platform platform, Date purchase, int rating, boolean physical) {
        setTitle(title);
        setPrice(price);
        setPlatform(platform);
        setPurchase(purchase);
        setRating(rating);
        setPhysical(physical);
    }    
    
    /** Constructor with console input
     * @param scanner The main application thread's scanner
     * @author 19076935 */
    public Game(Scanner scanner) {
		System.out.print("\nName > ");
		title = scanner.next();
		scanner.nextLine();
		do {
			try { 
				System.out.print("Price > ");
				price = scanner.nextFloat(); 
			} catch(InputMismatchException e) { 
				System.err.println("\nNot a valid price!\n");
				scanner.nextLine();
			}
		} while (price == 0f);
		
		scanner.nextLine();
		do {
			try { 
				System.out.print("Platform (Enter ? to see available platforms) > ");
				String input = scanner.nextLine();
				if (input.equals("?")) {
					for (Platform platform : Platform.values()) {
						System.out.println(platform + " | " + platform.value());
					}
					System.out.println("");
				} else {
					platform = Platform.getPlatformFromString(input);
				}
			} catch (InvalidPlatformException e) {
				System.err.println("\nNot a valid platform!\n");
			}
		} while (platform == null);
		
		do {
			try {
				System.out.print("Purchase Date (Fomat as DD/MM/YYYY) > ");
				String input = scanner.nextLine();
				int[] date = getDateFromString(input);
				Calendar cal = Calendar.getInstance();
				cal.set(date[2],  date[1], date[0]);
				purchased = cal.getTime();
			} catch (InvalidDateFormatException e) {
				System.err.println("\nNot a valid date!\n");
			}
		} while (purchased == null);
		
		do {
			try {
				System.out.print("Rating (Between 1 and 10) > ");
				int input = scanner.nextInt();
				if (input > 0 || input < 11) { rating = input; }
				else { throw new NumberFormatException(); }
			} catch (Exception e) { 
				System.err.println("\nNot a valid rating!\n");
				scanner.nextLine();
			}
		} while (rating == 0);
		scanner.nextLine();
		
		// I know I know while true bad
		while (true) {
			System.out.print("Physical Copy (Y/N) > ");
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("y")) { physical = true; }
			else if (input.equalsIgnoreCase("n")) { physical = false; }
			else { 
				System.err.println("\nNot a valid state!\n");
				continue;
			}
			break;
		}
		
		System.out.println("\nGetting data...");
		getData();
		if (APIResults.isEmpty()) { System.err.println("No data found"); }
	}
    
    /** Gets the API data of the game (Used when no JProgressBar is present, as method requires a JProgressBar to function)
     * @author 19076935 */
	public void getData() { getData(new JProgressBar()); }
	
	/** Gets the API data of the game
	 * @param bar The UI's progress bar
	 * @author 19076935 */
    @SuppressWarnings("unchecked") // I designed it to be this way chill out my guy
	public void getData(JProgressBar bar) {    
    	String urlTitle = title.replace(" ", "%20");
    	urlTitle = urlTitle.replace("&", "%26");
        url = "https://rapidapi.p.rapidapi.com/games/" + urlTitle + (getPlatform() == Platform.PC ? "" :  ("?platform=" + platform.getApiName()));
        try {
        	APIData = new Json(this.getInfo(bar));
        	APIResults = new Json((HashMap<String, Object>)APIData.get("results")); 
        	
        	parseData();
        	
        } catch (NullPointerException e) {
        	APIResults = new Json("");
        	bar.setValue(100);
        }
    }
    
    /** Parses the game's API data, and sets the corresponding variables
     * @author 19076935 */
    @SuppressWarnings("unchecked") 
	protected void parseData() {
    	if (!APIResults.isEmpty()) {
    		setTitle((String)APIResults.get("title"));
    		try {
    			Date release;
				release = new SimpleDateFormat("MMM dd yyyy").parse((String)APIResults.get("releaseDate"));
				setRelease(release);
			} catch (ParseException e) { 
				setRelease(purchased);
				new Log(e.getMessage());
				System.err.println("Unable to parse release date");
			}
    		setGenres((ArrayList<String>)APIResults.get("genres"));
    	} else {
    		APIResults = new Json("");
    	}
    }
    
    /** Returns a date as a formatted string
     * @param date The date to get as a string
     * @param format The format of the date string
     * @return The date formated based on the input string
     * @author 19076935 */
    public static String getDateAsString(Date date, String format) {
    	return new SimpleDateFormat(format).format(date);
    }
    
    /** Gets a date from a string
     * @param date The string to get the date from
     * @return The date as specified by the parameters
     * @throws InvalidDateFormatException If the input string is not a valid date
     * @author 19076935 */
    public static int[] getDateFromString(String date) throws InvalidDateFormatException {
    	int[] output = new int[3];
    	try {
    		output[0] = Integer.parseInt(date.substring(0, 1));
    		output[1] = Integer.parseInt(date.substring(3, 5));
    		output[2] = Integer.parseInt(date.substring(6, 10));
    	} catch (Exception e) { throw new InvalidDateFormatException(); }
    	return output;
    }

    @Override
    public String toString() { 
    	String out = "";
    	out += title + ":\n\tPrice: $" + getPriceAsString() +
    		            "\n\tPlatform: " + platform + 
    		            "\n\tPurchase Date: " + getDateAsString(purchased, "dd, MM yyyy") + 
    		            "\n\tRating: " + rating  + "/10" +
    		            "\n\tPhysical Copy: " + (physical ? "Yes" : "No");
    	if (!APIResults.isEmpty()) {
    		String genreText = "Genres: ";
        	int i = 0;
        	for (String genre : getGenres()) {
        		genreText += genre + (i == getGenres().size() - 1 ? "" : ", ");
        		++i;
        	}
        	
    		out += "\n\n\tRelease Date: " + getDateAsString(release, "dd, MM yyyy") + 
    			   "\n\tDeveloper: " + getResult("developers") + 
    			   "\n\t" + genreText + 
    			   "\n\tMetacritic Score: " + getResult("score");
    	}
    	return out;
    }
}
