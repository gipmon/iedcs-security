package player;

import player.api.Utils;
import player.api.Requests;
import java.io.IOException;
import java.net.URISyntaxException;
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
import javafx.scene.control.Label;
import org.json.JSONException;
import org.json.JSONObject;

public class BackendFrontPageController implements Initializable {
    @FXML private Label name = new Label();
    @FXML private Label email = new Label();
    @FXML private Label username = new Label();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            JSONObject user = Requests.getUser();
            name.setText("Name: " + user.getString("first_name") + " " + user.getString("last_name"));
            email.setText("E-mail: " + user.getString("email"));
            username.setText("Username: " + user.getString("username"));
        } catch (JSONException ex) {
            Logger.getLogger(BackendFrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void handleWebStoreBtn(ActionEvent event) {
        try {
            Utils.openBrowser(IEDCSPlayer.getBaseUrl());
        } catch (IOException ex) {
            Logger.getLogger(BackendFrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(BackendFrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleMyBooks(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MyBooksPage.fxml"));
            Scene scene = new Scene(root);
            Player.thestage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
