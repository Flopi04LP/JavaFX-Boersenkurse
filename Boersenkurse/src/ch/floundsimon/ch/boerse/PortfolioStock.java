package ch.floundsimon.ch.boerse;

import org.json.simple.JSONObject;

/**
 *
 * @author Florian Büchi & Simon Kappeler
 */
// Class to save stock data used for the portfolio into
public class PortfolioStock {
    private String title;
    private Double amount;
    private Double price;

    public PortfolioStock(String title, Double amount, Double price) {
        this.title = title.toLowerCase();
        this.amount = amount;
        this.price = price;
    }
    
    // returns a json of the saved data
    public String getJson(){      
        JSONObject a = new JSONObject();
        a.put("title", title);
        a.put("amount", amount);
        a.put("price", price);
        return a.toJSONString();
    }

    // various getters
    public String getTitle() {
        return title;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }
    
}
