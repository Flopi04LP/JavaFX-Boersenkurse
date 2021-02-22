package ch.floundsimon.ch.boerse;

import java.io.FileWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
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

public class Data {

    private static String apiKey = "1e826734-1cd7-4375-a011-bf9d6f42c565";

    public void getData() {

        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("start", "1"));
        paratmers.add(new BasicNameValuePair("limit", "5"));
        paratmers.add(new BasicNameValuePair("cryptocurrency_type", "coins"));

        try {
            String result = makeAPICall(uri, paratmers);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error: cannont access content - " + e.toString());
        }
    }

    public String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException, ParseException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

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

        return response_content;
    }
}
