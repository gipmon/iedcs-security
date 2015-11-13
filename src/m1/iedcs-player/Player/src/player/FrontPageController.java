package player;

import player.api.Utils;
import player.api.Requests;
import player.api.Result;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FrontPageController implements Initializable {
    @FXML private TextField login_email = new TextField();
    @FXML private PasswordField login_password = new PasswordField();
    @FXML private Button login_button = new Button();
    
    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            Utils.openBrowser(IEDCSPlayer.getBaseUrl());
        } catch (IOException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleLogin(ActionEvent event){
        if(login_button.isDisable()){
            return;
        }
        login_button.setDisable(true);
        
        String login = login_email.getText();
        String password = login_password.getText();
        
        if(login.length()==0 || password.length()==0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Wait!");
            alert.setHeaderText(null);
            alert.setContentText("E-mail or Password is empty!");
            alert.showAndWait();
            login_button.setDisable(false);
            return;
        }
        
        try{
            Result rs = Requests.login(login, password);
            if(rs.getStatusCode()!=200){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wait!");
                alert.setHeaderText(null);
                alert.setContentText("E-mail or Password is wrong!");
                alert.showAndWait();
                login_button.setDisable(false);
                return;
            }
        }catch(Exception e){
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, e);
        }
        
        login_button.setDisable(false);
        
        // set first scene, the frontpage scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("BackendFrontPage.fxml"));
            Scene scene = new Scene(root);
            Player.thestage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
