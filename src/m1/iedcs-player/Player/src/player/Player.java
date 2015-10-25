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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.security.ComputerDetails;

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
            - private key device
            - public key device
            - unique identifier
            - public key player
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
            while((s = stdinput.readLine()) != null){
                String[] aux = s.split(".");
                /*for(int i = 0; i<aux.length; i++){
                    if(aux[i] == "jar is unsigned"){
                        System.out.println("nao esta assinado");
                    }else if(aux[i] == "jar is signed"){
                        System.out.println("esta assinado");
                    }else{
                        System.out.println("salta");
                    }
                }*
            }
            
            System.out.println("Here is the standard error of the command:\n");
            while((s = stderror.readLine()) != null){
                System.out.println(s);
            }
            */

            // if private key don't exists
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            Key publicKeyDevice = kp.getPublic();
            Key privateKeyDevice = kp.getPrivate();
            
            PlayerKeyStore.storeKey("publicKeyDevice", publicKeyDevice);
            PlayerKeyStore.storeKey("privateKeyDevice", privateKeyDevice);
            
            // unique identifier
            System.out.println(ComputerDetails.getCpu_mhz());
            System.out.println(ComputerDetails.getCpu_model());
            System.out.println(ComputerDetails.getCpu_total_cpus());
            System.out.println(ComputerDetails.getCpu_vendor());
            System.out.println(ComputerDetails.getMac_address());
            System.out.println(ComputerDetails.getPublicIP());
            
            System.out.println(ComputerDetails.getUniqueIdentifier());
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
