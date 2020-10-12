package Library;

import Data.Json;
import Data.InfoRetrieval;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JProgressBar;

public class Game extends InfoRetrieval implements Serializable {
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    private float price;
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    private Platform platform;
    public Platform getPlatform() { return platform; }
    public void setPlatform(Platform platform) { this.platform = platform; }
    
    private boolean physical;
    public boolean getPhysical() { return physical; }
    public void setPhysical(boolean physical) { this.physical = physical; } 

    private Date release;
    public Date getRelease() { return release; }
    public void setRelease(Date release) { this.release = release; }
    
    private int rating;
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; } 
    
    private ArrayList<String> genres;
    public ArrayList<String> getGenres() { return this.genres; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    private Json APIResults;
    public Object getResult(String key) { return APIResults.get(key); }
    public boolean hasData() { return (APIResults == null) ? false : true; }

    public Game(String title, float price, Platform platform, Date release, int rating) {
        setTitle(title);
        setPrice(price);
        setPlatform(platform);
        setRelease(release);
        setRating(rating);

    }
    
    public void getData() { getData(new JProgressBar()); }
    public void getData(JProgressBar bar) {    
        url = "https://rapidapi.p.rapidapi.com/games/" + title.replace(" ", "%20") + "?platform:" + platform;
        try {
        	APIData = new Json(this.getInfo(bar));
        	APIResults = new Json((HashMap<String, Object>)APIData.get("results")); 
        	
        } catch (NullPointerException e) {
        	APIResults = null;
        	bar.setValue(100);
        }
    }


    @Override
    public String toString() { return title + " | $" + price + "| For: " + platform + " | Released on: " + release.getDate(); }
}
