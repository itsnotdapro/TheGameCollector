package Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JProgressBar;

import Exceptions.Log;

/** An abstract class that can be extended to an object that requires information to be 
 * retrieved from the API. getInfo method is defined as part of the abstract class, but 
 * retrieving data and parsing data from the info must be declared as part of the subclass
 * @author 1906935 */
public abstract class InfoRetrieval {
    protected String url;
       
    protected abstract void getData();
	protected abstract void parseData();
	
	/** Gets the information specified by the object's URL
	 * (used when not making GUI calls, as function requires a progress bar to update)
	 * @return The JSON string retrieved from the API
	 * @author 19076935 */
    protected String getInfo() { return getInfo(new JProgressBar()); }

    /** Gets the information specified by the object's URL
	 * @return The JSON string retrieved from the API
	 * @author 19076935 */
    protected String getInfo(JProgressBar bar) {
        try {
        	bar.setValue(0);
            URL path = new URL(url); 
            Thread.sleep(100);
            HttpURLConnection connection = (HttpURLConnection)path.openConnection(); 
            connection.setRequestMethod("GET"); 
            connection.addRequestProperty("x-rapidapi-host", "chicken-coop.p.rapidapi.com");
            connection.addRequestProperty("x-rapidapi-key", "32388f85fbmsh54131dbeca29461p1d73b3jsnb48d4a19c61f"); 
            connection.setDoOutput(true); 
            bar.setValue(40);

            // If the response returns code 200
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
                String response = inputStream.readLine();
                bar.setValue(60);
                inputStream.close(); 
                bar.setValue(100);
                return response; 
            }

        } catch (Exception e) { new Log(e.getMessage()); }

        return null;
    }
}
