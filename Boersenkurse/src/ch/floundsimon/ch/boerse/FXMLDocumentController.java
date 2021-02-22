/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.floundsimon.ch.boerse;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
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

/**
 *
 * @author flori
 */
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
    
    
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    

    @FXML
    private void btnclickbitcoin(ActionEvent event) {
        
        XYChart.Series<String, Number> databit = new XYChart.Series<>();   
        databit.getData().removeAll(Collections.singleton(chart.getData().setAll()));
        databit.getData().add(new XYChart.Data<>("1995", 180000));
        databit.getData().add(new XYChart.Data<>("1996", 190000));
        databit.getData().add(new XYChart.Data<>("1997", 240000));
        databit.getData().add(new XYChart.Data<>("1998", 230000));
        
        chart.getData().add(databit);
    }

    @FXML
    private void btnclickethereum(ActionEvent event) {
        XYChart.Series<String, Number> data = new XYChart.Series<>(); 
        
        data.getData().removeAll(Collections.singleton(chart.getData().setAll()));
               
        data.getData().add(new XYChart.Data<>("1995", 18000));
        data.getData().add(new XYChart.Data<>("1996", 19000));
        data.getData().add(new XYChart.Data<>("1997", 24000));
        data.getData().add(new XYChart.Data<>("1998", -23));
        
        chart.getData().add(data);
    }

    @FXML
    private void btnclickdogecoin(ActionEvent event) {
    }

    @FXML
    private void btnclickstocks(ActionEvent event) {
    }
    
}
