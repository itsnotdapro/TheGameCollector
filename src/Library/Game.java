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

public class Game extends InfoRetrieval implements Serializable {
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    // If there was arithmetic that needed to be done on the prices, it would be safer to store them as integers, but there isn't so it's not
    private float price;
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getPriceAsString() {
    	DecimalFormat df = new DecimalFormat();
    	df.setMinimumFractionDigits(2);
    	df.setMaximumFractionDigits(2);
    	return df.format(price);
    }

    private Platform platform;
    public Platform getPlatform() { return platform; }
    public void setPlatform(Platform platform) { this.platform = platform; }
    
    private boolean physical;
    public boolean getPhysical() { return physical; }
    public void setPhysical(boolean physical) { this.physical = physical; } 
    
    private Date purchased;
    public Date getPurchase() { return purchased; }
    public void setPurchase(Date purchased) { this.purchased = purchased; }

    private Date release;
    public Date getRelease() { return release; }
    public void setRelease(Date release) { this.release = release; }
    
    private int rating;
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; } 
    
    private ArrayList<String> genres;
    public ArrayList<String> getGenres() { return this.genres; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    private Json APIData;
    private Json APIResults;
    public Object getResult(String key) { return APIResults.get(key); }
    public Json getResults() { return APIResults; }
    public boolean hasData() { return (APIResults == null) ? false : true; }
    public void setResults(Json APIResults) { 
    	this.APIResults = APIResults; 
    	parseData();
    }


    public Game(String title, float price, Platform platform, Date purchase, int rating, boolean physical) {
        setTitle(title);
        setPrice(price);
        setPlatform(platform);
        setPurchase(purchase);
        setRating(rating);
        setPhysical(physical);
    }    
    
    public Game(Scanner scanner) {
		System.out.print("\nName > ");
		title = scanner.nextLine();
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
		if (!hasData()) { System.err.println("No data found"); }
	}
    
	public void getData() { getData(new JProgressBar()); }
    public void getData(JProgressBar bar) {    
    	String urlTitle = title.replace(" ", "%20");
    	urlTitle = urlTitle.replace("&", "%26");
        url = "https://rapidapi.p.rapidapi.com/games/" + urlTitle + (getPlatform() == Platform.PC ? "" :  ("?platform=" + platform.getApiName()));
        try {
        	APIData = new Json(this.getInfo(bar));
        	APIResults = new Json((HashMap<String, Object>)APIData.get("results")); 
        	
        	parseData();
        	
        } catch (NullPointerException e) {
        	APIResults = null;
        	bar.setValue(100);
        }
    }
    
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
    		APIResults = null;
    	}
    }
    
    public static String getDateAsString(Date date) {
    	return new SimpleDateFormat("dd MMMM, yyyy").format(date);
    }
    
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
    		            "\n\tPurchase Date: " + getDateAsString(purchased) + 
    		            "\n\tRating: " + rating  + "/10" +
    		            "\n\tPhysical Copy: " + (physical ? "Yes" : "No");
    	if (hasData()) {
    		String genreText = "Genres: ";
        	int i = 0;
        	for (String genre : getGenres()) {
        		genreText += genre + (i == getGenres().size() - 1 ? "" : ", ");
        		++i;
        	}
        	
    		out += "\n\n\tRelease Date: " + getDateAsString(release) + 
    			   "\n\tDeveloper: " + getResult("developers") + 
    			   "\n\t" + genreText + 
    			   "\n\tMetacritic Score: " + getResult("score");
    	}
    	return out;
    }
}
