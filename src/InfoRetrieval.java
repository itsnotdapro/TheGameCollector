import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public abstract class InfoRetrieval {
    protected String url;
    protected HashMap<String, Object> APIData;

    protected String getInfo() {
        try {
            URL path = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)path.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("x-rapidapi-host", "chicken-coop.p.rapidapi.com");
            connection.addRequestProperty("x-rapidapi-key", "32388f85fbmsh54131dbeca29461p1d73b3jsnb48d4a19c61f");
            connection.setDoOutput(true);

            // If the response returns code 200
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = inputStream.readLine();
                inputStream.close();
                return response;
            }

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
    protected abstract HashMap<String, Object> parseInfo(String info);
}
