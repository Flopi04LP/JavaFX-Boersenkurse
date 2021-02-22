package ch.floundsimon.ch.boerse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Data {
    private double eth, doge, btc =0;
    
    ArrayList<Double> btcArray = new ArrayList<>();

    public void getData() {

        String uri = "https://api.nomics.com/v1/currencies/ticker?key=2009f9d43e98104543309592cf3d3ad6&ids=BTC,ETH,DOGE&attributes=id,name,price&interval=1h,1d,7d,30d&per-page=100&page=1";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();

        try {
            String result = makeAPICall(uri, paratmers);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error: cannont access content - " + e.toString());
        }
    }

    public String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        CloseableHttpResponse response = client.execute(request);

        try {
            // System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);

            FileWriter fileWriter = new FileWriter("a.json");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(response_content);
            printWriter.close();

        } finally {
            response.close();
        }
        writeJsons();
        return response_content;
    }


    public void writeJsons() throws FileNotFoundException, IOException, org.json.simple.parser.ParseException{
        
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("a.json"));
        
        JSONArray array = (JSONArray) object;
        
        JSONObject btcJsonObj = (JSONObject) array.get(0);
        String btcString = (String) btcJsonObj.get("price");
        System.out.println("Price BTC = "+btcString);
        btc = Double.valueOf(btcString);
        
        
        JSONObject ethJsonObj = (JSONObject) array.get(1);
        String ethString = (String) ethJsonObj.get("price");
        System.out.println("Price ETH = "+ethString);
        eth = Double.valueOf(ethString);
        
        
        JSONObject dogeJsonObj = (JSONObject) array.get(2);
        String dogeString = (String) dogeJsonObj.get("price");
        System.out.println("Price DOGE = "+dogeString);
        doge = Double.valueOf(dogeString);
    }

    public double getEth() {
        return eth;
    }

    public double getDoge() {
        return doge;
    }

    public double getBtc() {
        return btc;
    }
    
}
