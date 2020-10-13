package Library;

import Data.Json;
import Exceptions.Log;
import Data.InfoRetrieval;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    
    


    @Override
    public String toString() { return title + " | $" + price + "| For: " + platform + " | Released on: " + release; }
}
