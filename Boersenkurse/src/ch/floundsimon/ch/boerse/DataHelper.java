package ch.floundsimon.ch.boerse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Florian Büchi & Simon Kappeler
 */
// Helper class for CryptoData and StocksData Models
public class DataHelper {

    // Makes an Api call to a given url and returns a string
    public static String makeApiCall(String url) throws Exception {
        // if (ping()) {
            String uri = url;
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
            return response_content;
        // } else {
        //     return "No internet";
        // }
    }

    // Creates a JSONObject from a String
    public static JSONObject getJSONObject(String string) throws Exception {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(string);
    }

    // Returns the amount of gain or loss made from two values
    public static Double gains(Double nacher, Double vorher) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(calcPerc(nacher, vorher)));
    }

    // Previously used to check for internet connection, not used anymore
    public static boolean ping() throws Exception {
        return true;
    }

    // Calculates the percentage in change between two values
    public static double calcPerc(double vorher, double nacher) {
        double b = Double.valueOf(vorher / nacher);
        double c = b * 100;
        double a = c - 100;
        return a;
    }
}
