package player;

import player.api.Utils;
import player.api.Requests;
import player.api.Result;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class MyBooksPageController implements Initializable {
    @FXML TableView<BookEntry> tableBooks;
    @FXML TableColumn columnName;
    @FXML TableColumn columnAuthor;
    @FXML TableColumn columnDate;
    private final ObservableList<BookEntry> data = FXCollections.observableArrayList();  

    public class BookEntry {
        public SimpleStringProperty identifier = new SimpleStringProperty(); 
        public SimpleStringProperty name = new SimpleStringProperty();
        public SimpleStringProperty production_date = new SimpleStringProperty();
        public SimpleStringProperty author = new SimpleStringProperty();
        
        public BookEntry(String identifier, String name, String production_date, String author){
            this.identifier = new SimpleStringProperty(identifier);
            this.name = new SimpleStringProperty(name);
            this.production_date = new SimpleStringProperty(production_date);
            this.author = new SimpleStringProperty(author);
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnAuthor.setCellValueFactory(new PropertyValueFactory("author"));
            columnDate.setCellValueFactory(new PropertyValueFactory("production_date"));
            
            tableBooks.setItems(data);
            
            Result rs = Requests.getJson(Requests.USER_BOOKS);
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
            
            tableBooks.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override 
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        Node node = ((Node) event.getTarget()).getParent();
                        TableRow row;
                        if (node instanceof TableRow) {
                            return; // do nothing
                        } else {
                            // clicking on text part
                            try {
                                System.out.println(data);
                                row = (TableRow) node.getParent();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewBook.fxml"));
                                AnchorPane anchor = loader.load();
                                ViewBookController controller = loader.getController();
                                controller.setIdentifier(data.get(row.getIndex()).getIdentifier());
                            
                                //Parent root = FXMLLoader.load(getClass().getResource("ViewBook.fxml"));
                                Scene scene = new Scene(anchor);
                                Player.thestage.setScene(scene);
                            } catch (IOException ex) {
                                Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                        }
                       
                    }
                }
            });
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
        try {
            Utils.openBrowser(IEDCSPlayer.getBaseUrl());
        } catch (IOException ex) {
            Logger.getLogger(MyBooksPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MyBooksPageController.class.getName()).log(Level.SEVERE, null, ex);
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
