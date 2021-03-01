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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author flori
 */
public class Stockscontroller implements Initializable {

    DecimalFormat df = new DecimalFormat("0.00");
    private Label label;
    private LineChart<String, Number> chart;
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
    private Button btn;
    @FXML
    private Label value;
    @FXML
    private TextField inputbox;
    @FXML
    private Button btnset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            start();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnclickbitcoin(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        start();

    }

    @FXML
    private void btnclickethereum(ActionEvent event) throws IOException, FileNotFoundException, ParseException {

        klicked(ETHEREUM);
    }

    @FXML
    private void btnclickdogecoin(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        klicked(DOGECOIN);
    }

    @FXML
    private void btnclickstocks(ActionEvent event) throws URISyntaxException, IOException, org.apache.hc.core5.http.ParseException, ParseException {
        try {
            stocks();
        } catch (Exception a) {

        }
    }

    public void start() throws IOException, FileNotFoundException, ParseException {
        
    }

    private void klicked(Coins coin) throws IOException, FileNotFoundException, ParseException {
        Parent root;
        String path = "FXMLDocument.fxml";
        root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage();
        Stage old = (Stage) btn.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
        old.close();
    }

    private double calcPerc(double vorher, double nacher) {
        double b = Double.valueOf(vorher / nacher);
        double c = b * 100;
        double a = c - 100;
        return a;
    }

    private void stocks() throws URISyntaxException, IOException, org.apache.hc.core5.http.ParseException, ParseException {
        chart.setVisible(false);
        up.setVisible(false);
        down.setVisible(false);

        Data data = new Data();
        String stok = String.valueOf(data.getStock("AAPL"));
        gains.setText(stok);

    }

    @FXML
    private void btnclickgo(ActionEvent event) throws URISyntaxException, IOException, org.apache.hc.core5.http.ParseException, ParseException {
        String input = inputbox.getText();
        Double result = Data.getStock(input);
        value.setText(String.valueOf("Value of "+input+": "+result));
    }

    @FXML
    private void btnclickset(ActionEvent event) {
    }
}
