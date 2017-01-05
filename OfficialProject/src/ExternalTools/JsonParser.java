package ExternalTools;

import java.util.HashMap;

import com.google.gson.Gson;
/** 
 * 
 * Used to parse JSON String
 *
 */
public class JsonParser {

  @SuppressWarnings("unchecked")
  public static HashMap<String, Object> jsonToHashMap(String jsonString) {
    Gson gson = new Gson();
    HashMap<String, Object> map = gson.fromJson(jsonString, HashMap.class);
    return map;
  }

  @SuppressWarnings("rawtypes")
  public static void main(String args[]) {
    String jsonString = "{hello:World,How:\"Are you\"}";

    HashMap p = (JsonParser.jsonToHashMap(jsonString));
    System.out.println(p.get("How"));
  }
}
