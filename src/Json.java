import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Json {
    public static HashMap<String, Object> parse(String jsonString) {
        HashMap<String, Object> output = new HashMap<String, Object>();

        //  All of these values are utilised inside the loop, but their values shouldn't be reset on evey iteration //
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
                    if (c == ',') {
                        parsingKeyResult = true;
                        result.put("title", resultValue);
                        resultValue = "";
                        resultKey = "";
                        continue;
                    } if (c != '"' && c != ':') { resultValue += c; }
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
                        genres.add(resultValue);
                        result.put("genres", genres);
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
                        result.put("score", resultValue);
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
                        result.put("score", resultValue);
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
