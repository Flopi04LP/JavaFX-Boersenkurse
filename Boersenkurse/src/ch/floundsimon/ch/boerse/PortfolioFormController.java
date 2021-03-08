package ch.floundsimon.ch.boerse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kappe
 */
public class PortfolioFormController implements Initializable {
    
    @FXML
    private Label labelTitle;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField inputStocksymbol;
    @FXML
    private TextField inputAmount;
    @FXML
    private TextField inputPrice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void onClickBtnConfirm(ActionEvent event) throws Exception {
        confirm();
    }
    
    @FXML
    private void onClickBtnCancel(ActionEvent event) {
        cancel();
    }
    
    private void onClickBtnConfirm(MouseEvent event) throws Exception {
        confirm();
    }
    
    private void onClickBtnCancel(MouseEvent event) {
        cancel();
    }
    
    private void confirm() throws Exception {
        try {
            String stock = inputStocksymbol.getText().toLowerCase();
            Double amount = Double.valueOf(inputAmount.getText());
            Double price = Double.valueOf(inputPrice.getText());
            
            PortfolioData.setNewStock(stock, amount, price);
            cancel();
        } catch (Exception e) {
        }
    }
    
    private void cancel() {
        Stage old = (Stage) btnConfirm.getScene().getWindow();
        old.close();
    }
    
    @FXML
    private void onClickConfirm(MouseEvent event) throws Exception {
        confirm();
    }
    
    @FXML
    private void onClickCancel(MouseEvent event) {
        cancel();
    }
}
