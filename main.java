/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author kappe
 */
public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void init(Stage primaryStage) {
        HBox root = new HBox();
        Scene scene = new Scene(root, 450,330);
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Salery");
        
        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("Chart");
        
        XYChart.Series<String, Number> data = new XYChart.Series<>();        
        data.getData().add(new XYChart.Data<String, Number>("1995", 180000));
        data.getData().add(new XYChart.Data<String, Number>("1996", 190000));
        data.getData().add(new XYChart.Data<String, Number>("1997", 240000));
        data.getData().add(new XYChart.Data<String, Number>("1998", 230000));
        data.getData().add(new XYChart.Data<String, Number>("1999", 190000));
        
        lineChart.getData().add(data);
        root.getChildren().add(lineChart);
        
        primaryStage.setTitle("AAAAAAAAA   ");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
}
