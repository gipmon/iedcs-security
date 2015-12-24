package player;

import iaik.pkcs.pkcs11.wrapper.CK_TOKEN_INFO;
import iaik.pkcs.pkcs11.wrapper.PKCS11;
import iaik.pkcs.pkcs11.wrapper.PKCS11Connector;
import player.api.Utils;
import player.api.Requests;
import player.api.Result;
import java.io.IOException;
import java.net.ProtocolException;
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
import org.json.JSONException;
import player.security.CitizenCard;
import iaik.pkcs.pkcs11.wrapper.*;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import player.security.ccCertValidate;
import player.security.ccCertValidate.ccException;

public class FrontPageController implements Initializable {
    @FXML private static TextField login_email = new TextField();
    @FXML private static PasswordField login_password = new PasswordField();
    @FXML private static Button login_button = new Button();
    @FXML private static Button citizen_card_login = new Button();

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            Utils.openBrowser(IEDCSPlayer.getRegisterUrl());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleCitizenCardLogin(ActionEvent event){
        login_button.setDisable(true);
        //citizen_card_login.setDisable(true);
        try{
        // Select the correct PKCS#11 module for dealing with Citizen Card tokens
            PKCS11 module = PKCS11Connector.connectToPKCS11Module ( System.getProperty ( "os.name" ).contains ( "Mac OS X" ) ?
                                                                    "pteidpkcs11.dylib" : "pteidpkcs11" );

            
            // Find all Citizen Card tokens
            long[] tokens = module.C_GetSlotList(true);

            if (tokens.length == 0) {
                handleException("No card inserted" );
                return;
            }

            // Perform a challenge-response operation using the authentication key pair
            for (int i = 0; i < tokens.length; i++) {
                CK_TOKEN_INFO tokenInfo = module.C_GetTokenInfo ( tokens[i] );
                if (String.valueOf ( tokenInfo.label ).startsWith ( "CARTAO DE CIDADAO" )) {
                    ccCertValidate.validateCertificate ( module, tokens[i], "CITIZEN AUTHENTICATION CERTIFICATE", "AUTHENTICATION SUB CA" );
                }

            }
        } catch (ccException ex) {
            handleException(ex.getMessage());
            return;
        } catch (IOException | PKCS11Exception ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Result r = Requests.login_with_cc();
        if(r == null | r.getStatusCode()!=200){
            if(!CitizenCard.cc_is_inserted && !CitizenCard.canceled){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wait!");
                alert.setHeaderText(null);
                JSONObject object = new JSONObject(r);
                JSONArray b = object.names();
                JSONObject c = null;
                try {
                    c = object.toJSONArray(b).getJSONObject(0);
                } catch (JSONException ex) {
                    Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //String a = b.next().toString();
                try {
                    alert.setContentText(c.getString("message"));
                } catch (JSONException ex) {
                    Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                alert.showAndWait();
                login_button.setDisable(false);
                citizen_card_login.setDisable(false);     
            }else if(CitizenCard.cc_is_inserted && !CitizenCard.canceled){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wait!");
                alert.setHeaderText(null);
                alert.setContentText("E-mail or Password is wrong!");
                alert.showAndWait();
            }
        }else{
            // set first scene, the frontpage scene
            try {
                Parent root = FXMLLoader.load(getClass().getResource("BackendFrontPage.fxml"));
                Scene scene = new Scene(root);
                Player.thestage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        login_button.setDisable(false);
        citizen_card_login.setDisable(false);
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
    
    public static void handleException(String e){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Wait!");
        alert.setHeaderText(null);
        alert.setContentText(e);
        alert.showAndWait();
        login_button.setDisable(false);
        citizen_card_login.setDisable(false); 
    }
    
    
}
