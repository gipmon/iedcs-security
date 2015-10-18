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
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyBooksPageController implements Initializable {
    @FXML TableView<BookEntry> tableBooks;
    @FXML TableColumn columnName;
    @FXML TableColumn columnAuthor;
    @FXML TableColumn columnDate;
    @FXML TableColumn columnView;
    private final ObservableList<BookEntry> data = FXCollections.observableArrayList();  

    public class BookEntry {
        public SimpleStringProperty identifier = new SimpleStringProperty(); 
        public SimpleStringProperty name = new SimpleStringProperty();
        public SimpleStringProperty production_date = new SimpleStringProperty();
        public SimpleStringProperty author = new SimpleStringProperty();
        public SimpleObjectProperty<ViewBook> view;
        
        public BookEntry(String identifier, String name, String production_date, String author){
            this.identifier = new SimpleStringProperty(identifier);
            this.name = new SimpleStringProperty(name);
            this.production_date = new SimpleStringProperty(production_date);
            this.author = new SimpleStringProperty(author);
            this.view = new SimpleObjectProperty(new ViewBook(identifier));
        }
        
        public String getIdentifier() {
            return identifier.get();
        }

        public String getName() {
            return name.get();
        }
        
        public String getProduction_date() {
            return production_date.get();
        }
        
        public String getAuthor() {
            return author.get();
        }
        
    }
    
    public class ViewBook extends Button{
        private String identifier;
        
        public ViewBook(String identifier){
            super("View");
            this.identifier = identifier;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnAuthor.setCellValueFactory(new PropertyValueFactory("author"));
            columnDate.setCellValueFactory(new PropertyValueFactory("production_date"));
            columnView.setCellValueFactory(new PropertyValueFactory("view"));
            
            tableBooks.setItems(data);
            
            Result rs = Requests.get(Requests.USER_BOOKS);
            if(rs.getStatusCode()==200){
                JSONArray ja = (JSONArray)rs.getResult();
                for (int i = 0 ; i < ja.length(); i++) {
                    JSONObject obj = ja.getJSONObject(i);
                    String identifier = obj.getString("identifier");
                    String name = obj.getString("name");
                    String production_date = obj.getString("production_date");
                    String author = obj.getString("author");                    
                    System.out.println(identifier + " " + name + " " + production_date + " " + author);
                    data.add(new BookEntry(identifier, name, production_date, author));
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
    
    @FXML
    private void handleWebStoreBtn(ActionEvent event) {
        try{
            Utils.openBrowser(IEDCSPlayer.getBaseUrl());
        }catch(Exception e){
            Utils.printExceptionStack(e);
        }
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
}
