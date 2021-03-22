package ch.floundsimon.ch.boerse;

import java.io.*;
import java.net.URISyntaxException;
import org.apache.hc.core5.http.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Florian Büchi & Simon Kappeler
 */
// Model class for the StocksController file
public class StocksData {

    public static String name = "";
    public static String currency = "";
    public static String weblink = "";
    public static String jsonString = "";

    // Makes an Api call for a given stock and returns the current price
    // Sets the jsonString to something we can use later
    public static Double getStock(String stock) throws Exception {

        String uri = "https://finnhub.io/api/v1/quote?symbol=" + stock + "&token=sandbox_c0u914748v6qqphtv320";
        jsonString = DataHelper.makeApiCall(uri);
        getProfile(stock);

        return getCurrentPrice();
    }
    
    // Returns the current price
    public static Double getCurrentPrice() throws Exception {
        return getJsonValue("c", "stock.json");
    }

    // Returns the open price
    public static Double getOpenPrice() throws Exception {
        return getJsonValue("o", "stock.json");

    }

    // Returns the high price of the day
    public static Double getHighOfTheDay() throws Exception {
        return getJsonValue("h", "stock.json");
    }
    
    // Returns the low price of the day
    public static Double getLowOfTheDay() throws Exception {
        return getJsonValue("l", "stock.json");
    }

    // Returns the price of the previous close 
    public static Double getPreviousClose() throws Exception {
        return getJsonValue("pc", "stock.json");
    }

    // Returns a value from the json file with given symbol
    public static Double getJsonValue(String field, String path) throws Exception {
        JSONObject object = DataHelper.getJSONObject(jsonString);
        Object a = object.get(field);

        String priceStr = String.valueOf(a);
        Double price = 0.0;

        try {
            price = Double.valueOf(priceStr);

        } catch (Exception e) {
            System.out.println(e);
        }

        return price;
    }
    
    // Returns the reccomendation for a stock 
    public static String getRecomendation(String symbol) throws Exception {
        
        String uri = "https://finnhub.io/api/v1/stock/recommendation?symbol=" + symbol + "&token=sandbox_c0u914748v6qqphtv320";

        String response_content = DataHelper.makeApiCall(uri);

        Double[] recomendationArray = new Double[6];

        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(response_content);

        JSONObject o = (JSONObject) array.get(0);
        
        // Get the amount of strong buy reccomendations
        Object a = o.get("strongBuy");
        String f = String.valueOf(a);
        recomendationArray[0] = Double.valueOf(f);

        // Get the amount of buy reccomendations
        Object b = o.get("buy");
        String g = String.valueOf(b);
        recomendationArray[1] = Double.valueOf(g);

        // Get the amount of HODL reccomendations
        Object c = o.get("hold");
        String h = String.valueOf(c);
        recomendationArray[2] = Double.valueOf(h);

        // Get the amount of sell reccomendations
        Object d = o.get("sell");
        String i = String.valueOf(d);
        recomendationArray[3] = Double.valueOf(i);

        // Get the amount of strong sell reccomendations
        Object e = o.get("strongSell");
        String j = String.valueOf(e);
        recomendationArray[4] = Double.valueOf(j);

        // Gets the maximum value of all reccomendations
        Double max = 0.0;
        for (int y = 0; y < 5; y++) {
            if (recomendationArray[y] > max) {
                max = recomendationArray[y];
            }
        }
        
        // Returns a text for all recomendations
        if (max == recomendationArray[0]) {
            return "We strongly recomend you buy";
        } else if (max == recomendationArray[1]) {
            return "We recomend you buy";
        } else if (max == recomendationArray[2]) {
            return "We recomend you hold";
        } else if (max == recomendationArray[3]) {
            return "We recomend you sell";
        } else if (max == recomendationArray[4]) {
            return "We strongly recomend you sell";
        } else {
            return "";
        }
    }

    // Gets further details like name, link and currency for a stock
    public static void getProfile(String symbol) throws Exception {
        String uri = "https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=c0u914748v6qqphtv31g";
        String response_content = DataHelper.makeApiCall(uri);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(response_content);

        name = (String) object.get("name");
        weblink = (String) object.get("weburl");
        currency = (String) object.get("currency");
    }

    // Returns an image path for the company logo
    public static String getLogoPath(String symbol) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {
        String path = "https://finnhub.io/api/logo?symbol=" + symbol + "";
        return path;
    }
}
