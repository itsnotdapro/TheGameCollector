import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

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

    private Date release;
    public Date getRelease() { return release; }
    public void setRelease(Date release) { this.release = release; }

    public HashMap<String, Object> getAPIData() { return APIData; }

    private HashMap<String, Object> APIResults;
    public HashMap<String, Object> getAPIResults() { return APIResults; }
    public void setAPIResults(HashMap<String, Object> APIResults) { this.APIResults = APIResults; }

    public Game(String title, float price, Platform platform, Date release, boolean getData) {
        setTitle(title);
        setPrice(price);
        setPlatform(platform);
        setRelease(release);

        if (getData) {
            url = "https://rapidapi.p.rapidapi.com/games/" + title.replace(" ", "%20") + "?platform:" + platform;
            APIData = parseInfo(this.getInfo());
            APIResults = (HashMap<String, Object>)APIData.get("results");
        }
    }

    /** Constructor if the game's info does not need to be retrieved
    * @param title
    * @param price
    * @param platform
    * @param release */
    public Game(String title, float price, Platform platform, Date release) {
        setTitle(title);
        setPrice(price);
        setPlatform(platform);
        setRelease(release);
    }

    @Override
    public HashMap<String, Object> parseInfo(String info) { return Json.parse(info); }

    @Override
    public String toString() { return title + " | $" + price + "| For: " + platform + " | Released on: " + release.getDate(); }
}
