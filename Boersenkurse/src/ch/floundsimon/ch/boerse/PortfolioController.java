/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.floundsimon.ch.boerse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            /*
            try {
            String b = PortfolioData.getStockJson("aa");
            String[] titleJson = PortfolioData.getStocks();
            String[] portfolioJson = new String[4];
            JSONObject[] jsonObjectArray = new JSONObject[4];
            int i = 0;
            JSONParser parser = new JSONParser();
            
            for (String a : titleJson) {
            portfolioJson[i] = PortfolioData.getStockJson(a);
            jsonObjectArray[i] = (JSONObject) parser.parse(PortfolioData.getStockJson(a));
            i++;
            }
            labelNameStock1.setText(titleJson[0]);
            labelNameStock2.setText(titleJson[1]);
            labelNameStock3.setText(titleJson[2]);
            labelNameStock4.setText(titleJson[3]);
            
            } catch (Exception ex) {
            System.out.println(ex);
            } */
            String[] a = PortfolioData.getStocks();
            for(String b :a){
                System.out.println(a);
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
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
}
