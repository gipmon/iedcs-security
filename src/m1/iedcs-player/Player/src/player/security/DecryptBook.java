package player.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.Header;
import org.json.JSONException;
import player.api.Requests;

public class DecryptBook {
    
    private String r2;
    private String bs;
    private String book_identifier;
    private final String book_ciphered;
    
    public DecryptBook(Header[] headers, String book_ciphered){
        for(Header h : headers){
            if(h.getName().equals("r2")){
                this.r2 = h.getValue();
            }else if(h.getName().equals("bs")){
                this.bs = h.getValue();
            }else if(h.getName().equals("identifier")){
                this.book_identifier = h.getValue();
            }
        }
        this.book_ciphered = book_ciphered;
    }
    
    public String decrypt(){
        try {
            byte[] r2_encripted = Base64.getDecoder().decode(this.r2);
            // k1 = PBKDF2(user.username + "jnpc" + book.identifier, book.identifier).read(32)
            PBKDF2 k1 = new PBKDF2(Requests.getUser().getString("username") + "jnpc" + this.book_identifier,this.book_identifier);
            byte[] key = k1.read(32);
            String teste = new String(Base64.getEncoder().encode(key));
            
            // f7AXUX5xFh6RLFgp9e3iwc303JhYLaHDWw5W4QwuZA0=
            // 3pxmcUsutVwga/6bvOjs1hu1T0vfduLTlqqh7asNScY=
            byte[] rd2_bytes = AES(r2_encripted, key);
            String test = new String(rd2_bytes);
            // db8e655d617b98ecd1014e3de5870ac732efcbb706352cb293029e15
            System.out.println("ok");
            return this.book_ciphered;
        } catch (JSONException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(DecryptBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.book_ciphered;
    }
    
    
    
    
    private static byte[] AES(byte[] cipheredText, byte[] key){
        try {
            Cipher cipher_aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            int bs = 16;
            byte[] iv = new byte[bs];
            System.arraycopy(cipheredText, 0, iv, 0, bs);
            IvParameterSpec iv_spec = new IvParameterSpec(iv);
            SecretKeySpec sks = new SecretKeySpec(key, "AES");
            
            byte[] tmp_block = new byte[cipheredText.length-bs];
            System.arraycopy(cipheredText, bs, tmp_block, 0, cipheredText.length-bs);
            
            // Instantiate the cipher
            cipher_aes.init(Cipher.DECRYPT_MODE, sks, iv_spec);
            System.out.println(cipher_aes.getBlockSize());
            
            return cipher_aes.doFinal(tmp_block);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(DecryptBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
