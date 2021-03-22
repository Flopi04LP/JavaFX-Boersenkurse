package ch.floundsimon.ch.boerse;

import static ch.floundsimon.ch.boerse.Coins.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Florian Büchi & Simon Kappeler
 */
// The Controller File for the FXMLDocument View
public class FXMLDocumentController implements Initializable {

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

    // Arrays to save coin prices
    Integer bitcoinVals[] = new Integer[10];
    Integer etherumVals[] = new Integer[10];
    Integer dogecoinVals[] = new Integer[10];
    Double gainsPerc = 1.23;
    String currency = "usd";
    @FXML
    private Label gains;
    @FXML
    private ImageView up;
    @FXML
    private ImageView down;
    @FXML
    private Label price;
    @FXML
    private ComboBox<String> inputCurrency;
    String a = "usd";
    String b = "eur";
    String c = "chf";
    Coins currentCoin = BITCOIN;
    @FXML
    private Button btnPortfolio;

    @Override
    // Initializes the View
    public void initialize(URL url, ResourceBundle rb) {
        // sets the currency to USD, can be changed by the user later
        inputCurrency.setItems(FXCollections.observableArrayList(a, b, c));
        inputCurrency.setValue(a);
        currency = inputCurrency.getValue();
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
    // On click for the Bitcoin Button
    private void btnclickbitcoin(ActionEvent event) throws Exception {
        start();

    }

    @FXML
    // On click for the Ethereum Button
    private void btnclickethereum(ActionEvent event) throws Exception {

        klicked(ETHEREUM);
    }

    @FXML
    // On click for the Dogecoin Button
    private void btnclickdogecoin(ActionEvent event) throws Exception {
        klicked(DOGECOIN);
    }

    @FXML
    // On click for the Stocks lookup Button
    private void btnclickstocks(ActionEvent event) throws Exception {
        stocks();
    }

    // Not sure why this exists, but it works so i'll leave it here
    public void start() throws Exception {
        klicked(BITCOIN);
    }

    // Updates the screen to the selected coin
    // Is used by the onClick functions
    public void klicked(Coins coin) throws IOException, FileNotFoundException, ParseException, Exception {
        // The amount of days we display data for
        int days = 5;
        
        // Check for Internetconnections
        if (DataHelper.ping()) {
            currentCoin = coin;
            chart.setVisible(true);
            
            // Init the Chart
            XYChart.Series<String, Number> databit = new XYChart.Series<>();
            databit.getData().removeAll(Collections.singleton(chart.getData().setAll()));
            Double vals[] = new Double[days];
            Double[] array = CryptoData.getDays(coin, currency, days);

            // Get the data and display it in the chart
            for (int i = 0; i < days - 1; i++) {
                Double val = array[i];
                databit.getData().add(new XYChart.Data<>(String.valueOf(i), val));
                vals[i] = val;
            }
            
            // Gets the current price and sets it into the chart
            Double latest = CryptoData.getCoin(coin, currency);
            databit.getData().add(new XYChart.Data<>(String.valueOf(days), latest));
            vals[days - 1] = latest;
            price.setText(String.valueOf(latest) + "  " + currency.toUpperCase());

            // gainsPerc = Double.valueOf(df.format(calcPerc(vals[5], vals[4])));
            gainsPerc = DataHelper.gains(vals[days - 1], vals[days - 2]);

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
            chart.setTitle(coin.cleanString(coin));
        } else {
            // If ping returns false (Not going to happen, ping always returns true)
            chart.setTitle("No Internet");
            hideGains();
        }
    }

    // Opens the Stock lookup window and closes this one
    private void stocks() throws Exception{
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

    @FXML
    // Changes the currency to what the user changed it to
    private void inputCurrencyChange(ActionEvent event) throws Exception {
        currency = inputCurrency.getValue();
        klicked(currentCoin);
    }
    
    private void hideGains() {
        up.setVisible(false);
        down.setVisible(false);
        gains.setVisible(false);
        inputCurrency.setVisible(false);
    }

    @FXML
    // Opens the Portfolio window and closes this one
    private void btnclickportfolio(ActionEvent event) throws Exception {
        Parent root;
        String path = "Portfolio.fxml";
        root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage();
        Stage old = (Stage) chart.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
        old.close();
    }
}
