package player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
            
            // if private key don't exists
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            Key publicKeyDevice = kp.getPublic();
            Key privateKeyDevice = kp.getPrivate();
            
            // JCEKS allows secret keys
            // JKS keystore can only store private keys and certificates but not secret keys
            // http://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html#KeystoreImplementation
            KeyStore ks = KeyStore.getInstance("JCEKS");
         
            // https://docs.oracle.com/javase/7/docs/api/java/security/KeyStore.html
            // get user password and file input stream
            String key = "WvJd8x4b3fpJPAEtFNWd6ptKUEARSpKZEyZDRVq9xJQZAvpbTpKVUhqYDJt8Q3Pxcgfb9r2eHxKQ7N7n28bt6TgUk9wzZbJVANZPWGUfYqttXwpZYetU3zYjmQXGDqET";
            char[] password = key.toCharArray();
            
            FileInputStream fis = null;
            try {
                fis = new FileInputStream("Player.KeyStore");
            } catch (IOException ex) {}
            
            try {
                ks.load(fis, password);
            } catch (IOException | CertificateException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ProtectionParameter protParam =  new PasswordProtection(password);

            // get my private key
            /*
            
            PrivateKeyEntry pkEntry = (PrivateKeyEntry) ks.getEntry("privateKeyAlias", protParam);
            PrivateKey myPrivateKey = pkEntry.getPrivateKey();
            */
            // save my secret key
            SecretKeySpec sks = new SecretKeySpec(publicKeyDevice.getEncoded(), publicKeyDevice.getAlgorithm());
            SecretKey mySecretKey = (SecretKey)sks;
            SecretKeyEntry skEntry = new SecretKeyEntry(mySecretKey);
            ks.setEntry("secretKeyAlias", skEntry, protParam);

            // store away the keystore
            java.io.FileOutputStream fos = null;
            try {
                fos = new java.io.FileOutputStream("Player.KeyStore");
                ks.store(fos, password);
            } catch (IOException | CertificateException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            System.out.println("");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
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
