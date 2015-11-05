/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import player.api.Requests;
import player.api.Result;
import player.api.BookContent;
import java.io.IOException;
import java.net.ProtocolException;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextArea;
import player.security.DecryptBook;

public class ViewBookController implements Initializable {
    @FXML private TextArea textZone;
    @FXML private Label title;
    @FXML private Label page;
    private String identifier;
    private DecryptBook db;
    private int page_number = 0;
    
    public class Ebook{
        public SimpleStringProperty ebook = new SimpleStringProperty();

        public Ebook(String text){
            this.ebook = new SimpleStringProperty(text);  
        }
        
        public String getEbook(){
            return ebook.get();
        }
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textZone.setEditable(false);
        
    }    
    
    @FXML
    private void handleUserBtn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("BackendFrontPage.fxml"));
            Scene scene = new Scene(root);
            Player.thestage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleMyBooksBtn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MyBooksPage.fxml"));
            Scene scene = new Scene(root);
            Player.thestage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleForwardBtn(ActionEvent event) {
        textZone.setText(db.getContent(++page_number));
        page.setText(Integer.toString(page_number+1));
    }
    
    @FXML
    private void handleBackwardBtn(ActionEvent event) {
        textZone.setText(db.getContent(--page_number));
        page.setText(Integer.toString(page_number+1));
    }
    
    public void setIdentifier(String identifier){
        db = new DecryptBook(identifier);
        textZone.setText(db.getContent(0));
        title.setText(db.title);
    }
    
}
