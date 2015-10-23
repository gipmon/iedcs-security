/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ViewBookController implements Initializable {
    @FXML private TextArea textZone;
    @FXML private Label title;
    private String identifier;

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
    
    public void setIdentifier(String identifier){
        try {
            Result rs = Requests.getBookContent(Requests.VIEW_BOOK, identifier);
            if(rs.getStatusCode()==200){
                BookContent obj = (BookContent) rs.getResult();
                textZone.setText(obj.getContent());  
                for(int i = 0; i<obj.getHeaders().length; i++){
                    if(obj.getHeaders()[i].toString().contains("name")){
                        title.setText(obj.getHeaders()[i].toString().substring(5));
                    }
                }
            }
        } catch (ProtocolException ex) {
            Logger.getLogger(MyBooksPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyBooksPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(MyBooksPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
