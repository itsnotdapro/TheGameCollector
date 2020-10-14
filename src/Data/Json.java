package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/** Class used for storing and parsing the JSON metadata retrieved by the API 
 * @author 19076935 */
public class Json implements Serializable {
	private HashMap<String, Object> data;
	
	// Constructors
	/** Constructor with string argument 
	 * @param jsonString the string of JSON info retrieved by the API
	 * @author 19076935 */
	public Json(String jsonString) { data = parse(jsonString); }
	
	/** Constructor with already parsed HahMap argument
	 * @param data HashMap from parsed API data
	 * @author 19076935 */
	public Json(HashMap<String, Object> data) { this.data = data; }
	
	// Methods
	/** Method used to retrieve a specific point of data in the JSON
	 * @param key The entry to find
	 * @return The entry specified (must be typecast to the required type)
	 * @author 19076935 */
	public Object get(String key) { return data.get(key); }
	
	/** Checks whether the object has data
	* @return Boolean state of the amount of data in the object
	* @author 19076935 */
	public boolean isEmpty() { return data.isEmpty(); }
	
	/** Removes the duplicates from a given ArrayList of strings
	 * @param array The array to remove duplicates from
	 * @return The edited array
	 * @author 19076935 */
	public static ArrayList<String> removeDuplicates(ArrayList<String> array) {
		ArrayList<String> newArray = new ArrayList<String>();
		for (String item : array) {
			if (!newArray.contains(item)) {
				newArray.add(item);
			}
		}
		return newArray;
	}
	
	/** Removes the duplicates from a given HashMap of (String, Object)'s
	 * @param map The map to remove duplicates from
	 * @return The edited map
	 * @author 19076935 */
	public static HashMap<String, Object> removeDuplicates(HashMap<String, Object> map) {
		HashMap<String, Object> newMap = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			if (newMap.containsKey(key) && newMap.containsValue(map.get(key))) {
				newMap.put(key, map.get(key));
			}
		}
		return newMap;
	}
	
	@Override
	public String toString() { return data.toString(); }
	
	/** Returns the formatted HashMap of a JSON string, based on the return results of the API data. Will only parse for the given data of the API 
	* @param jsonString the string of JSON data to be parsed
	* @return a HashMap generated by the JSON string
	* @author 19076935 */
    public static HashMap<String, Object> parse(String jsonString) {
        HashMap<String, Object> output = new HashMap<String, Object>();

        //  All of these values are utilised inside the loop, but their values shouldn't be reset on every iteration //
        String key = "", value = "", resultKey = "", resultValue = "";
        int repeatsParsed = 0;
        HashMap<String, Object> result = new HashMap<String, Object>();
        boolean parsingKey = true, parsingKeyResult = true;
        ArrayList<String> publishers = new ArrayList<String>(),
                          genres = new ArrayList<String>(),
                          otherPlatforms = new ArrayList<String>();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for (char c : jsonString.toCharArray()) {
            if (parsingKey) {
                key += c;
            }

            // TODO: Make each of these statements a function instead of just repeating it every time.
            // parsing the query
            if (key.equals("{\"query\":")) {
                parsingKey = false;
                if (c == ',') {
                    parsingKey = true;
                    output.put("query", value);
                    value = "";
                    key = "";
                    continue;
                }
                if (c != ':') { value += c; }
            }

            // parsing execution time
            if (key.equals("\"executionTime\":")) {
                parsingKey = false;
                if (c == ',') {
                    parsingKey = true;
                    output.put("executionTime", Float.parseFloat(value));
                    value = "";
                    key = "";
                    continue;
                } if (c != ':') { value += c; }
            }


            // parsing results
            if (key.equals("\"result\":{\"")) {
                parsingKey = false;
                if (parsingKeyResult) {
                    resultKey += c;
                }
                // parsing title in results
                if (resultKey.equals("\"title\":")) {
                    parsingKeyResult = false;
                    if (repeatsParsed == 0 && c == ':') { repeatsParsed = 1; continue; }
                    if (c == ',') {
                        parsingKeyResult = true;
                        result.put("title", resultValue);
                        resultValue = "";
                        resultKey = "";
                        repeatsParsed = 0;
                        continue;
                    } if (c != '"') { resultValue += c; }
                }

                // parsing release date in results
                if (resultKey.equals("\"releaseDate\":")) {
                    parsingKeyResult = false;
                    if (c == ',') {
                        if (repeatsParsed == 0) { repeatsParsed = 1; continue; }
                        parsingKeyResult = true;
                        result.put("releaseDate", resultValue);
                        repeatsParsed = 0;
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':') { resultValue += c; }
                }

                // parsing the description
                if (resultKey.equals("\"description\":")) {
                    parsingKeyResult = false;
                    if (c == '"') {
                        if (repeatsParsed == 0) { repeatsParsed = 1; continue; }
                        parsingKeyResult = true;
                        result.put("description", resultValue);
                        repeatsParsed = 0;
                        resultValue = "";
                        resultKey = "";
                        continue;
                    }  if (c != '"' && c != ':') { resultValue += c; }
                }

                // parsing the genres
                if (resultKey.equals(",\"genre\":[")) {
                    parsingKeyResult = false;
                    if (c == ',') {
                        genres.add(resultValue);
                        resultValue = "";
                        continue;
                    } else if (c == ']') {
                        parsingKeyResult = true;
     
                        result.put("genres", removeDuplicates(genres));
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':' && c != '[') { resultValue += c; }
                }

                // parsing image
                if (resultKey.equals(",\"image\":")) {
                    parsingKeyResult = false;
                    if (c == ':' && repeatsParsed == 0) { repeatsParsed = 1; continue; }
                    else if (c == ',') {
                        parsingKeyResult = true;
                        result.put("image", resultValue);
                        repeatsParsed = 0;
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != '\\') { resultValue += c; }
                }

                // parsing score
                if (resultKey.equals("\"score\":")) {
                    parsingKeyResult = false;
                    if (c == ',') {
                        parsingKeyResult = true;
                        result.put("score", Integer.parseInt(resultValue));
                        repeatsParsed = 0;
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':') { resultValue += c; }
                }

                // parsing developer
                if (resultKey.equals("\"developer\":")) {
                    parsingKeyResult = false;
                    if (c == ',') {
                        parsingKeyResult = true;
                        result.put("developers", resultValue);
                        repeatsParsed = 0;
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':') { resultValue += c; }
                }

                // parsing publishers
                if (resultKey.equals("\"publisher\":[")) {
                    parsingKeyResult = false;
                    if (c == ',') {
                        publishers.add(resultValue);
                        resultValue = "";
                        continue;
                    } else if (c == ']') {
                        parsingKeyResult = true;
                        publishers.add(resultValue);
                        result.put("publishers", publishers);
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':' && c != '[') { resultValue += c; }
                }

                // parsing score
                if (resultKey.equals(",\"rating\":")) {
                    parsingKeyResult = false;
                    if (c == ',') {
                        parsingKeyResult = true;
                        result.put("rating", resultValue);
                        repeatsParsed = 0;
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':') { resultValue += c; }
                }

                // parsing other platforms
                if (resultKey.equals("\"alsoAvailableOn\":[")) {
                    parsingKeyResult = false;
                    if (c == ',') {
                        otherPlatforms.add(resultValue);
                        resultValue = "";
                        continue;
                    } else if (c == ']') {
                        parsingKeyResult = true;
                        otherPlatforms.add(resultValue);
                        result.put("alsoAvailableOn", otherPlatforms);
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':' && c != '[') { resultValue += c; }
                }
            }
        }
        output.put("results", result);
        return output;
    }
}
