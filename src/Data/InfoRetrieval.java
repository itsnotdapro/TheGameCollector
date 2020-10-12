package Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JProgressBar;

public abstract class InfoRetrieval {
    protected String url;
    protected Json APIData;
    
    protected String getInfo() {
    	return getInfo(new JProgressBar());
    }

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

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
}
