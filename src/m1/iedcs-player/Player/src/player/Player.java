package player;

import player.security.PlayerKeyStore;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;

public class Player extends Application {
    
    static Stage thestage;
    
    @Override
    public void start(Stage pstage) throws Exception {
        initSecurityResources();
        thestage = pstage;
        
        // set first scene, the frontpage scene
        Parent root = FXMLLoader.load(getClass().getResource("FrontPage.fxml"));
        Scene scene_frontpage = new Scene(root);
        
        thestage.setScene(scene_frontpage);
        thestage.show();
    }

    private static void initSecurityResources(){
        try {
            /* aqui deve carregar o keyStore, verificar se tem o ficheiro e carregar as chaves
            que ele conhece:
            - private key device
            - public key device
            - unique identifier
            - public key player
            */
            // verificar assinatura
            
            
            // if private key don't exists
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            Key publicKeyDevice = kp.getPublic();
            Key privateKeyDevice = kp.getPrivate();
            
            PlayerKeyStore.storeKey("publicKeyDevice", publicKeyDevice);
            PlayerKeyStore.storeKey("privateKeyDevice", privateKeyDevice);
            
            // unique identifier
            
            
            // public key player
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
