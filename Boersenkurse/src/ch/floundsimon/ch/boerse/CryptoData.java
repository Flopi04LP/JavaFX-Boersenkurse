/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.floundsimon.ch.boerse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author kappe
 */
public class CryptoData {

    public static double eth, doge, btc = 0;
    public static ArrayList<Double> btcArray = new ArrayList<>();

    public static void getData() throws Exception {
        String uri = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin%2Cethereum%2Cdogecoin&vs_currencies=usd";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(params);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        CloseableHttpResponse response = client.execute(request);

        try {
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            response.close();
        }
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(response_content);

        JSONObject priceEth = (JSONObject) object.get("ethereum");
        JSONObject priceBtc = (JSONObject) object.get("bitcoin");
        JSONObject priceDoge = (JSONObject) object.get("dogecoin");

        Object btcObject = priceBtc.get("usd");
        String btcString = String.valueOf(btcObject);
        btc = Double.valueOf(btcString);

        Object ethObject = priceEth.get("usd");
        String ethString = String.valueOf(ethObject);
        eth = Double.valueOf(ethString);

        Object dogeObject = priceDoge.get("usd");
        String dogeString = String.valueOf(dogeObject);
        doge = Double.valueOf(dogeString);
    }

    public static Double[] getFiveDays(Coins coin) throws Exception {
        Double array[] = new Double[5];
        LocalDate td = LocalDate.now();
        String format = "dd-MM-yyyy";
        String date = td.format(DateTimeFormatter.ofPattern(format));
        String uri;

        for (int i = 0; i < 5; i++) {
         uri = "https://api.coingecko.com/api/v3/coins/" + coin.toString(coin) + "/history?date="+ td.minusDays(i).format(DateTimeFormatter.ofPattern(format)) +"&localization=false";
         
         
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            String response_content = "";

            URIBuilder query = new URIBuilder(uri);
            query.addParameters(params);

            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(query.build());

            request.setHeader(HttpHeaders.ACCEPT, "application/json");
            CloseableHttpResponse response = client.execute(request);

            try {
                HttpEntity entity = response.getEntity();
                response_content = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                response.close();
            }

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(response_content);
            JSONObject market_data = (JSONObject) object.get("market_data");
            JSONObject current_price = (JSONObject) market_data.get("current_price");
            
            Object price = current_price.get("usd");
            String priceString = String.valueOf(price);
            Double fin = Double.valueOf(priceString);
            
            array[i]=fin;
        }
        return array;
    }
    
    public static Double getCoin(Coins coin) throws Exception{
        getData();
        switch(coin){
            case BITCOIN:
                return btc;
            case ETHEREUM:
                return eth;
            case DOGECOIN:
                return doge;
        }
        return null;
    }
}
