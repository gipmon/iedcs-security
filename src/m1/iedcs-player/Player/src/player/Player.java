package player;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import player.security.PlayerKeyStore;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.security.ComputerDetails;
import player.security.PlayerPublicKey;

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

    private static void initSecurityResources() throws URISyntaxException, IOException{
        try {
            /* aqui deve carregar o keyStore, verificar se tem o ficheiro e carregar as chaves
            que ele conhece:
            - private key device [x]
            - public key device [x]
            - unique identifier [x]
            - public key player [x]
            */
            
            
            // verificar assinatura
            /*
            String s = null;
            
            File codeBase = new File(Player.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            System.out.println(codeBase.getPath());
            Process p = Runtime.getRuntime().exec("jarsigner -verify -keystore JarSignature.KeyStore " + codeBase.getPath());
            BufferedReader stdinput  = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stderror  = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            System.out.println("Here is the standard output of the command:\n");
            s = stdinput.readLine();
            System.out.println(s);
              
            if(s.toLowerCase().contains("jar is unsigned".toLowerCase())){
                System.exit(0);
            }else if(s.toLowerCase().contains("jar verified".toLowerCase())){
                System.out.println("esta assinado");
            }

            */

            // if private key don't exists
            if(!PlayerKeyStore.exists("privateKeyDevice")){
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair kp = kpg.genKeyPair();
                Key publicKeyDevice = kp.getPublic();
                Key privateKeyDevice = kp.getPrivate();
                
                System.out.println("creating");
                
                PlayerKeyStore.storeKey("publicKeyDevice", publicKeyDevice);
                PlayerKeyStore.storeKey("privateKeyDevice", privateKeyDevice);
            }
            
            // public key player
            PublicKey key_Test_apagar = PlayerPublicKey.getKey();
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
