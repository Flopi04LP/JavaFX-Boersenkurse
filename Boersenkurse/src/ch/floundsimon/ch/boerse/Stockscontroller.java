package ch.floundsimon.ch.boerse;

import java.net.URI;
import java.net.URL;
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
 * @author Florian Büchi & Simon Kappeler
 */
// Controller file for the Stocks View
public class Stockscontroller implements Initializable {

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
    @FXML
    private Label a;
    @FXML
    private Label b;
    @FXML
    private Label c;
    @FXML
    private Label e;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desc.setVisible(false);
        labelRecomendation.setVisible(false);
        noLogo.setOpacity(0);
        try {
            if (!DataHelper.ping()) {
                companyname.setText("No Internet");
                hideGains();
            }
        } catch (Exception ex) {
        }
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

    // Opens the Crypto window and closes this one
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
    
    // Initializes the charts and arrows
    private void stocks() throws Exception {
        chart.setVisible(false);
        up.setVisible(false);
        down.setVisible(false);
    }

    @FXML
    // On click of the 'go' button which looks up a stock
    private void btnclickgo(ActionEvent event) throws Exception {
        if (DataHelper.ping()) {
            desc.setVisible(true);
            String input = inputbox.getText().toUpperCase();
            
            // Make sure the input is not null or empty
            if (input != "" && input != null && input.length() > 0) {
                try {
                    // Set the logo
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
                    // Get Data from the Model class and save it to a double
                    result = StocksData.getStock(input.toUpperCase());
                } catch (Exception e) {
                    System.out.println("No such company");
                }
                // set the text for the company name and the current price
                companyname.setText(StocksData.name);
                value.setText(String.valueOf(result + "  " + StocksData.currency));

                Double pc = 0.0;
                Double perc = 0.0;
                try {
                    pc = StocksData.getPreviousClose();
                    Double current = StocksData.getCurrentPrice();
                    perc = DataHelper.gains(pc, current);

                } catch (Exception e) {
                    System.out.println(e);
                    // System.out.println("Can't calculate percentage");
                }
                if (DataHelper.calcPerc(pc, perc) < 0) {
                    down.setVisible(true);
                    up.setVisible(false);
                    gains.setText(String.valueOf(perc + "%"));
                } else if (DataHelper.calcPerc(pc, perc) > 0) {
                    down.setVisible(false);
                    up.setVisible(true);
                    gains.setText(String.valueOf("+" + perc + "%"));
                } else {
                    // System.out.println("Can't calculate percentages");
                }
                
                // Set the text for high, low and previous close data
                // As well as the Reccomendation
                try {
                    lowoftheday.setVisible(true);
                    lowoftheday.setText(String.valueOf(StocksData.getLowOfTheDay()));

                    highoftheday.setVisible(true);
                    highoftheday.setText(String.valueOf(StocksData.getHighOfTheDay()));

                    previousclose.setVisible(true);
                    previousclose.setText(String.valueOf(StocksData.getPreviousClose()));
                    
                    if (input.equals("GME")) {
                        rocket1.setVisible(true);
                        rocket2.setVisible(true);
                        labelRecomendation.setVisible(true);
                        labelRecomendation.setText("HOLDDD!!!");
                    } else {
                        rocket1.setVisible(false);
                        rocket2.setVisible(false);
                        labelRecomendation.setVisible(true);
                        labelRecomendation.setText(String.valueOf(StocksData.getRecomendation(input.toUpperCase())));
                    }
                    
                } catch (Exception e) {
                    System.out.println("No Data for Highs, Lows and previous Close");
                }
            } else {
                System.out.println("No input");
            }
        } else {
            companyname.setText("No Internet");
        hideGains();
        }

    }

    @FXML
    // Opens the companys website if the user clicks on the logo
    // Only works if the api actually gives a logo and a website
    private void onClick(MouseEvent event) throws Exception {
        if (DataHelper.ping()) {
            URI url = new URI(StocksData.weblink);
            java.awt.Desktop.getDesktop().browse(url);
        } else {
        }
    }

    // Hides stuff for aestetic reasons dont ask why ok
    private void hideGains() {
        up.setVisible(false);
        down.setVisible(false);
        gains.setVisible(false);
        inputbox.setVisible(false);
        btn.setVisible(false);
        lowoftheday.setVisible(false);
        highoftheday.setVisible(false);
        previousclose.setVisible(false);
        labelRecomendation.setVisible(false);
        desc.setVisible(false);
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        e.setVisible(false);
    }
}
