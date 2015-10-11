package fantasticfour.roam.database;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Vamsi on 10/10/15.
 */
public class DatabaseConnection {
    private static final String DB_URL = "http://theindieme.com/fernandl_roam/locations/";
    private static final String GET_LOCATION = "getLocation";

    public static JSONObject getLocation(int category, double latitude, double longitude, int distance) {
        JSONParser jsonParser = new JSONParser();
        HashMap<String, Object> values = new HashMap<>();
        values.put("tag", GET_LOCATION);
        values.put("category_id", category);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("distance", distance);
        return jsonParser.getJSONFromUrl(DB_URL, values);
    }

}
