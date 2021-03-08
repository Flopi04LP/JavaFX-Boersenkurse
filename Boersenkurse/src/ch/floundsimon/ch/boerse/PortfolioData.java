package ch.floundsimon.ch.boerse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author kappe
 */
public class PortfolioData {

    public static String title;
    public static Properties props = new Properties();

    public static void setNewStock(String stock, Double amount, Double price) throws Exception {
        PortfolioStock p = new PortfolioStock(stock, amount, price);
        props.setProperty(p.getTitle(), p.getJson());
        title = p.getTitle();
        // System.out.println(props.getProperty(title));      
        OutputStream output = new FileOutputStream("config.properties");
        props.store(output, null);
    }

    public static String getStockJson(String stock) throws Exception {
        InputStream input = new FileInputStream("config.properties");
        props.load(input);
        String p = props.getProperty(title);
        return p;
    }
    
    public static String[] getStocks() throws Exception{
        String[] array = new String[4];
        InputStream input = new FileInputStream("config.properties");
        props.load(input);
        Set<Object> set = new HashSet<>();
        set = props.keySet();
        int i = 0;
        
        for(Object a : set){
            array[i] = (String) a;
            i++;
        }
        
        return array;
    }
}
