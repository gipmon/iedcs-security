package player;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FrontPageController implements Initializable {
    @FXML private TextField login_email = new TextField();
    @FXML private PasswordField login_password = new PasswordField();
    @FXML private Button login_button = new Button();
    
    @FXML
    private void handleRegister(ActionEvent event) {
        try{
            Utils.openBrowser(IEDCSPlayer.getBaseUrl());
        }catch(Exception e){
            Utils.printExceptionStack(e);
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
            System.out.println("E-mail or Password is empty!");
            login_button.setDisable(false);
            return;
        }
        
        ArrayList<String> parameters = new ArrayList<String>();
        try{
            Requests.login(login, password);
        }catch(Exception e){
            Utils.printExceptionStack(e);
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
