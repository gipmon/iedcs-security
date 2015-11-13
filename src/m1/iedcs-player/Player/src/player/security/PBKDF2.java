package player.security;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2 {
    
    private char[] password;
    private byte[] salt;
    private int iterations = 1000;
    
    public PBKDF2(String password, String salt){
        this.password = password.toCharArray();
        this.salt = salt.getBytes();
    }
    
    public PBKDF2(String password, String salt, int iterations){
        this(password, salt);
        this.iterations = iterations;
    }
    
    public byte[] read(int bytes)
    {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes*8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(PBKDF2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new byte[0];
    }
     
    
}
