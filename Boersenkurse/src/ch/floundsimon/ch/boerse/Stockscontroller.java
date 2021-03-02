package ch.floundsimon.ch.boerse;

import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private Label desc;
    @FXML
    private Label lowoftheday;
    @FXML
    private Label highoftheday;
    @FXML
    private Label previousclose;
    @FXML
    private Label labelRecomendation;
    @FXML
    private ImageView logo;
    @FXML
    private Label companyname;
    @FXML
    private Label noLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desc.setVisible(false);
        labelRecomendation.setVisible(false);
        noLogo.setOpacity(0);

    }

    @FXML
    private void btnclickbitcoin(ActionEvent event) throws Exception {
        klicked();
    }

    private void btnclickstocks(ActionEvent event) throws Exception {
        try {
            stocks();
        } catch (Exception a) {

        }
    }

    private void klicked() throws Exception {
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

    private void stocks() throws Exception {
        chart.setVisible(false);
        up.setVisible(false);
        down.setVisible(false);
    }

    @FXML
    private void btnclickgo(ActionEvent event) throws Exception {
        desc.setVisible(true);
        String input = inputbox.getText().toUpperCase();
        try {
            Image image = new Image(StocksData.getLogoPath(input));
            logo.setImage(image);
            logo.setOpacity(100);
            noLogo.setOpacity(0);
        } catch (Exception e) {
            noLogo.setOpacity(100);
            logo.setOpacity(0);
            System.out.println("There is no Logo available");
        }

        Double result = 0.0;
        try {
            result = StocksData.getStock(input.toUpperCase());
        } catch (Exception e) {
            System.out.println("No such company");
        }
        companyname.setText(StocksData.name);
        value.setText(String.valueOf(result + "  " + StocksData.currency));

        Double pc = 0.0;
        Double perc = 0.0;
        try {
            pc = StocksData.writeStockJsonPc();
            perc = Double.valueOf(df.format(Double.valueOf(calcPerc(pc, result))));
        } catch (Exception e) {
            System.out.println("Can't calculate percentage");
        }
        if (calcPerc(pc, perc) < 0) {
            down.setVisible(true);
            up.setVisible(false);
            gains.setText(String.valueOf(perc + "%"));
        } else if (calcPerc(pc, perc) > 0) {
            down.setVisible(false);
            up.setVisible(true);
            gains.setText(String.valueOf("+" + perc + "%"));
        } else {
            System.out.println("Can't calculate percentages");
        }

        try {
            lowoftheday.setVisible(true);
            lowoftheday.setText(String.valueOf(StocksData.getLowOfTheDay()));

            highoftheday.setVisible(true);
            highoftheday.setText(String.valueOf(StocksData.getHighOfTheDay()));

            previousclose.setVisible(true);
            previousclose.setText(String.valueOf(StocksData.getPreviousClose()));

            labelRecomendation.setVisible(true);
            labelRecomendation.setText(String.valueOf(StocksData.getRecomendation(input.toUpperCase())));
        } catch (Exception e) {
            System.out.println("No Data for Highs, Lows and previous Close");
        }
    }

    @FXML
    private void onClick(MouseEvent event) throws Exception {
        URI url = new URI(StocksData.weblink);
        java.awt.Desktop.getDesktop().browse(url);

    }
}
