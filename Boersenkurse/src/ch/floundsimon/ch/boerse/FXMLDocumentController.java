package ch.floundsimon.ch.boerse;

import static ch.floundsimon.ch.boerse.Coins.*;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private void btnclickstocks(ActionEvent event) {
    }

    public void start() throws IOException, FileNotFoundException, ParseException {
        klicked(BITCOIN);
    }

    private void klicked(Coins coin) throws IOException, FileNotFoundException, ParseException {
         XYChart.Series<String, Number> databit = new XYChart.Series<>();
        databit.getData().removeAll(Collections.singleton(chart.getData().setAll()));
        Data data = new Data();
        int vals[] = new int[10];

        for (int i = 0; i < 7; i++) {
            /*
            switch (coin) {
                case "BITCOIN":
                    databit.getData().add(new XYChart.Data<>(String.valueOf(i), (int)data.getBtc()));
                    vals[i] = (int) data.getBtc();
                    break;
                case "ETHEREUM":
                    databit.getData().add(new XYChart.Data<>(String.valueOf(i), (int)data.getEth()));
                    vals[i] = (int) data.getEth();
                    break;
                case "DOGECOIN":
                    databit.getData().add(new XYChart.Data<>(String.valueOf(i), (int)data.getDoge()));
                    vals[i] = (int) data.getDoge();
                    break;
                    
            }
*/
            /*
            Random r = new Random();
            int rnd = r.nextInt(10) + 50;
            databit.getData().add(new XYChart.Data<>(String.valueOf(i), rnd));
            vals[i] = rnd;       
            */
        }
       

                        
            
            
        // System.out.println(vals[5] + "  "+ vals[6]);
        gainsPerc = Double.valueOf(df.format(calcPerc(vals[6], vals[5])));

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
}

