package player.security;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerPublicKey {
    
    public static PublicKey getKey(){
        FileInputStream fis = null;
        
        try {
            File f = new File("keystore/player.pub");
            
            if(f.exists()){
                System.out.println("AQUI");
            }
            
            fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            
            byte[] keyBytes = new byte[(int)f.length()];
            dis.readFully(keyBytes);
            dis.close();
            
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            
            return kf.generatePublic(spec);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayerPublicKey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(PlayerPublicKey.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(PlayerPublicKey.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
}
