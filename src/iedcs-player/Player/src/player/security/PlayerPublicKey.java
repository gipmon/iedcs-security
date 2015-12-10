package player.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import player.Player;

public class PlayerPublicKey {
    private final static String key = "77RhYLLHHuMx3FmYP7pXUjUWstWVqC26RQbMgeEAZaXfTLgBULYz735Yd95DGxF7RVe3nj4ymsvK3tyMnP7atYL2L4cwy8mmYdhP7sEkzmGq94r94DGG4kWJaSzky9c8";
    private final static char[] password = key.toCharArray();
    
    // https://docs.oracle.com/javase/7/docs/api/java/security/KeyStore.html
    private static KeyStore ks;
    private static String filename = "keystore/PublicPlayerKey.KeyStore";
    private static ProtectionParameter protParam;
    
    static{
        try{
            // JCEKS allows secret keys
            // JKS keystore can only store private keys and certificates but not secret keys
            // http://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html#KeystoreImplementation
            ks = KeyStore.getInstance("JCEKS");
            
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(filename);
            } catch (IOException e) {}
            
            try {
                ks.load(fis, password);
            } catch (IOException | CertificateException | NoSuchAlgorithmException ex) {
                Logger.getLogger(PlayerPublicKey.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            protParam =  new KeyStore.PasswordProtection(password);
        } catch (KeyStoreException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PublicKey getKey(){
        try {
            // get my private key
            SecretKeyEntry skEntry = (SecretKeyEntry) ks.getEntry("playerPublicKey", protParam);
            SecretKey myPrivateKey = skEntry.getSecretKey();
            
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(myPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(myPrivateKey.getAlgorithm());
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            return pubKey;
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException | InvalidKeySpecException ex) {
            Logger.getLogger(PlayerPublicKey.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        return null;
    }
}
