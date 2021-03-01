package ch.floundsimon.ch.boerse;

import static ch.floundsimon.ch.boerse.Coins.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author flori
 */
public class FXMLDocumentController implements Initializable {

    DecimalFormat df = new DecimalFormat("0.00");
    private Label label;
    @FXML
    private LineChart<String, Number> chart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private Button btnbitcoin;
    @FXML
    private Button btnethereum;
    @FXML
    private Button btndogecoin;
    @FXML
    private Button btnstocks;

    Integer bitcoinVals[] = new Integer[10];
    Integer etherumVals[] = new Integer[10];
    Integer dogecoinVals[] = new Integer[10];
    Double gainsPerc = 1.23;
    @FXML
    private Label gains;
    @FXML
    private ImageView up;
    @FXML
    private ImageView down;
    @FXML
    private Label price;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            start();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnclickbitcoin(ActionEvent event) throws Exception {
        start();

    }

    @FXML
    private void btnclickethereum(ActionEvent event) throws Exception {

        klicked(ETHEREUM);
    }

    @FXML
    private void btnclickdogecoin(ActionEvent event) throws Exception {
        klicked(DOGECOIN);
    }

    @FXML
    private void btnclickstocks(ActionEvent event) throws Exception {

        stocks();

    }

    public void start() throws Exception {
        klicked(BITCOIN);
    }

    public void klicked(Coins coin) throws IOException, FileNotFoundException, ParseException, Exception {
        chart.setVisible(true);
        XYChart.Series<String, Number> databit = new XYChart.Series<>();
        databit.getData().removeAll(Collections.singleton(chart.getData().setAll()));
        Double vals[] = new Double[6];
        Double[] array = CryptoData.getFiveDays(coin);

        for (int i = 0; i < 5; i++) {
            Double val = array[i];
            databit.getData().add(new XYChart.Data<>(String.valueOf(i), val));
            vals[i] = val;

            /*
            Random r = new Random();
            int rnd = r.nextInt(10) + 50;
            databit.getData().add(new XYChart.Data<>(String.valueOf(i), rnd));
            vals[i] = rnd;
             */
        }

        Double latest = CryptoData.getCoin(coin);
        databit.getData().add(new XYChart.Data<>(String.valueOf(6), latest));
        vals[5] = latest;
        price.setText(String.valueOf(latest)+" USD");
        
        gainsPerc = Double.valueOf(df.format(calcPerc(vals[5], vals[4])));

        if (gainsPerc > 0) {
            gains.setText(String.valueOf("+ " + gainsPerc + "%"));
            up.setVisible(true);
            down.setVisible(false);
        } else {
            gains.setText(String.valueOf(gainsPerc + "%"));
            up.setVisible(false);
            down.setVisible(true);
        }

        chart.getData().add(databit);
        chart.setTitle(coin.toString(coin));
    }

    private double calcPerc(double vorher, double nacher) {
        double b = Double.valueOf(vorher / nacher);
        double c = b * 100;
        double a = c - 100;
        return a;
    }

    private void stocks() throws URISyntaxException, IOException, org.apache.hc.core5.http.ParseException, ParseException {
        Parent root;
        String path = "Stocks.fxml";
        root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage();
        Stage old = (Stage) chart.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
        old.close();

    }
}
