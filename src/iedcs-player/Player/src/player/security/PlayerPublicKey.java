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
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerPublicKey {
    
    public static PublicKey getKey(){
        try {
            String key_b64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqZ8rdPCND6AVoCqHmDWYZqG9X4oukF31SpU0pjYb4p70eS4J+mXKd2Y2S/fnM0IguxAbTWEtefS8tLztxrn1ign8tWo7EwuRmeb5vnof6/14Nwn+wmkx5Zq/Uwyay8DwwGtClg5zQfLHaRQ4IBH5uxCTMl33jq8oBOSMKL/xHIkynSZwWegyAgEEkhyadx3PnOfmBLo7X+/MrLE/5Ec1d8taFVgFYynw7VWJfCITN/nioDxQCQXv7n6E29MLjLyaHwopWSjiOv4PKFP3xCZlFuq0nF9Z58foU1ngkqPTugOLzhhSOK35zgyZJ4xx/GAwb7rtxGitcHyXTB0WcFRnjwIDAQAB";
            
            byte[] keyBytes = Base64.getDecoder().decode(key_b64.getBytes());
            
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            
            return kf.generatePublic(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            Logger.getLogger(PlayerPublicKey.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
