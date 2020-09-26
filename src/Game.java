import java.io.Serializable;
import java.util.Date;

public class Game implements Serializable {
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

    public Game(String title, float price, Platform platform, Date release) {
        setTitle(title);
        setPrice(price);
        setPlatform(platform);
        setRelease(release);
    }

    @Override
    public String toString() { return title + " | $" + price + "| For: " + platform + " | Released on: " + release.getDate(); }
}
