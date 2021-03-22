package ch.floundsimon.ch.boerse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Florian Büchi & Simon Kappeler
 */
// Model class for the PortfolioController
public class PortfolioData {

    public static Properties props = new Properties();
    public static String filename = "config.properties";

    // Creates a new Property for a new Portfolio stock
    public static void setNewStock(String stock, Double amount, Double price) throws Exception {
        PortfolioStock p = new PortfolioStock(stock, amount, price);
        props.setProperty(p.getTitle(), p.getJson());
        OutputStream output = new FileOutputStream(filename);
        props.store(output, null);
    }
    
    // Gets the JSONObject of a Stock from the properties file
    public static JSONObject getStockJson(String stock) throws Exception {
        InputStream input = new FileInputStream(filename);
        props.load(input);
        String str = props.getProperty(stock);
        
        JSONParser parser = new JSONParser();
        JSONObject p = (JSONObject) parser.parse(str);
        return p;
    }

    // Get the titles of all stocks saved in the properties
    public static ArrayList<String> getStockTitles() throws Exception {
        ArrayList<String> array = new ArrayList<>();
        InputStream input = new FileInputStream(filename);
        props.load(input);
        Enumeration a = props.keys();

        while (a.hasMoreElements()) {
            String str = (String) a.nextElement();
            array.add(str);
        }
        return array;
    }
}
