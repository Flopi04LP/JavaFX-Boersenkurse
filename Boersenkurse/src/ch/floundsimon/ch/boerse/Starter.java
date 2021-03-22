package ch.floundsimon.ch.boerse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Florian Büchi & Simon Kappeler
 */
public class Starter extends Application {

    @Override
    // Starts the application
    public void start(Stage stage) throws Exception {
        try {
            DataHelper.ping();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Börsenkurse");
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
