package player;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.ArrayList;

public class FrontPageController implements Initializable {
    @FXML private TextField login_email = new TextField();
    @FXML private TextField login_password = new TextField();
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
        
        String login = "mail@rafaelferreira.pt"; //login_email.getText();
        String password = "12345678"; //login_password.getText();
        
        if(login.length()==0 || password.length()==0){
            System.out.println("E-mail or Password is empty!");
            return;
        }
        
        ArrayList<String> parameters = new ArrayList<String>();
        try{
            Requests.login(login, password);
        }catch(Exception e){
            Utils.printExceptionStack(e);
        }
        
        login_button.setDisable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
