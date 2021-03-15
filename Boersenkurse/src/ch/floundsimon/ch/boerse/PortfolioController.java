package ch.floundsimon.ch.boerse;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author kappe
 */
public class PortfolioController implements Initializable {

    @FXML
    private Button btnbitcoin;
    @FXML
    private Button btnadd;
    @FXML
    private Label labelNameStock1;
    @FXML
    private Label labelNameStock2;
    @FXML
    private Label labelNameStock3;
    @FXML
    private Label labelNameStock4;
    @FXML
    private Label labelGainsStock1;
    @FXML
    private Label labelGainsStock2;
    @FXML
    private Label labelGainsStock3;
    @FXML
    private Label labelGainsStock4;
    @FXML
    private Label labelValue;
    @FXML
    private Label labelGainsPerc;
    @FXML
    private Label labelValue1;
    @FXML
    private Label labelValue2;
    @FXML
    private Label labelValue3;
    @FXML
    private Label labelValue4;

    private String[] stockJson;

    /**
     * Initializes the controller class.
     */
    NumberFormat formatter = new DecimalFormat("###.##");
    NumberFormat valueFormat = new DecimalFormat("############.##");
    @FXML
    private ImageView up;
    @FXML
    private ImageView down;
    @FXML
    private Button btnrefresh;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            refresh();
        } catch (Exception ex) {
        }
    }

    @FXML
    private void btnclickbitcoin(ActionEvent event) throws Exception {
        Parent root;
        String path = "FXMLDocument.fxml";
        root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage();
        Stage old = (Stage) btnbitcoin.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
        old.close();
    }

    private void add() throws Exception {
        try {
            Parent root;
            String path = "PortfolioForm.fxml";
            root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    private void onClickBtnAdd(ActionEvent event) throws Exception {
        add();
    }

    @FXML
    private void onClickBtnAdd(MouseEvent event) throws Exception {
        add();
    }

    public void refresh() throws Exception {
        try {
            ArrayList<String> a = PortfolioData.getStockTitles();
            JSONObject jsonObjects[] = new JSONObject[4];
            Double amount[] = new Double[4];
            Double valueBefore[] = new Double[4];
            Double valueNow[] = new Double[4];
            Double gains[] = new Double[4];
            Double price[] = new Double[4];
            Double totalValue = 0.0;
            Double totalGains = 0.0;

            for (int i = 0; i < 4; i++) {
                try {
                    jsonObjects[i] = PortfolioData.getStockJson(a.get(i));
                    amount[i] = (Double) jsonObjects[i].get("amount");
                    price[i] = (Double) jsonObjects[i].get("price");
                    valueNow[i] = StocksData.getStock(a.get(i).toUpperCase()) * amount[i];
                    valueBefore[i] = price[i] * amount[i];

                    gains[i] = Double.valueOf(formatter.format(DataHelper.calcPerc(valueNow[i], valueBefore[i])));
                    totalValue += valueNow[i];
                } catch (Exception e) {
                }
            }

            totalGains = gains[0] + gains[1] + gains[2] + gains[3];
            totalGains = totalGains / 4;

            if (totalGains < 0) {
                down.setVisible(true);
                up.setVisible(false);
            } else if (totalGains > 0) {
                down.setVisible(false);
                up.setVisible(true);
            } else {
                System.out.println("Can't calculate percentages");
            }

            labelGainsPerc.setText(formatter.format(totalGains) + " %");
            labelValue.setText(valueFormat.format(totalValue) + " USD");

            labelValue1.setText(String.valueOf(valueNow[0] + " USD"));
            labelGainsStock1.setText(gains[0] + " %");

            labelValue2.setText(String.valueOf(valueNow[1] + " USD"));
            labelGainsStock2.setText(gains[1] + " %");

            labelValue3.setText(String.valueOf(valueNow[2] + " USD"));
            labelGainsStock3.setText(gains[2] + " %");

            labelValue4.setText(String.valueOf(valueNow[3] + " USD"));
            labelGainsStock4.setText(gains[3] + " %");

            labelNameStock1.setText(a.get(0));
            labelNameStock2.setText(a.get(1));
            labelNameStock3.setText(a.get(2));
            labelNameStock4.setText(a.get(3));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void onClickBtnRefresh(ActionEvent event) throws Exception {
        refresh();
    }

    @FXML
    private void onClickBtnRefresh(MouseEvent event) throws Exception {
        refresh();
    }
}
