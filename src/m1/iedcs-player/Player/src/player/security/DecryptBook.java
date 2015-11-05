package player.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.Header;
import org.bouncycastle.jcajce.provider.digest.SHA224;
import org.bouncycastle.jcajce.provider.digest.BCMessageDigest;
import org.json.JSONException;
import player.IEDCSPlayer;
import player.api.Requests;
import player.api.Requests;
import player.api.Result;

public class DecryptBook {
    
    private byte[] r2;
    private String bs;
    private String book_identifier;
    private final String book_ciphered;
    private byte[] iv_array;
    private static final int max_iterations = 10;
    final protected static char[] hexArray = "0123456789abcdef".toCharArray();
    
    public DecryptBook(Header[] headers, String book_ciphered){
        for(Header h : headers){
            if(h.getName().equals("r2")){
                byte[] f = Base64.getDecoder().decode(h.getValue());
                byte[] tmp1 = new byte[32];
                System.arraycopy(f, 0, tmp1, 0, 32);
                this.iv_array = tmp1;
                byte[] tmp2 = new byte[f.length-32];
                System.arraycopy(f, 32, tmp2, 0, f.length-32);
                this.r2 = tmp2;
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
            byte[] r2_encripted = this.r2;
            // k1 = PBKDF2(user.username + "jnpc" + book.identifier, book.identifier).read(32)
            PBKDF2 k1 = new PBKDF2(Requests.getUser().getString("username") + "jnpc" + this.book_identifier,this.book_identifier);
            byte[] key = k1.read(32);
            
            byte[] rd2_bytes = decryptAES(r2_encripted, key);
            String rd2 = new String(rd2_bytes);
            
            int number = 0;
            
            for(char c : rd2.toCharArray()){
                if(Character.isDigit(c)){
                    number += c;
                }
            }
            
            int n = number % max_iterations;
            
            byte[] rd1 = rd1_process(rd2_bytes, rd2_bytes);
            
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("book_identifier", this.book_identifier);
            parameters.put("rd1", new String(Base64.getEncoder().encode(rd1)));
            parameters.put("device_identifier", ComputerDetails.getUniqueIdentifier());
            Result r = Requests.postJSON(IEDCSPlayer.getBaseUrl() + "api/v1/security_exchange_r1r2/", parameters);
            
            System.out.println("ok");
            return this.book_ciphered;
        } catch (JSONException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(DecryptBook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(DecryptBook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DecryptBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.book_ciphered;
    }
    
    private byte[] rd1_process(byte[] rd, byte[] random2){
        try {
            MessageDigest messageDigest224 = MessageDigest.getInstance( "SHA-224");
            messageDigest224.update((Requests.getUser().getString("username") + "fcp" + this.book_identifier).getBytes());
            byte[] sha_tmp = bytesToHex(messageDigest224.digest()).getBytes();
            PBKDF2 pbk = new PBKDF2(new String(sha_tmp), new String(random2));
            byte[] k1 = pbk.read(32);
            
            String rd2 = new String(random2);
            String sha = new String(sha_tmp);
            String teste = new String(Base64.getEncoder().encode(k1));
            
            byte[] iv1 = new byte[16];
            System.arraycopy(this.iv_array, 0, iv1, 0, 16);
            
            byte[] rd1 = encryptAES(rd, k1, iv1);
            byte[] final_rd1 = new byte[iv1.length + rd1.length];
            System.arraycopy(iv1, 0, final_rd1, 0, iv1.length);
            System.arraycopy(rd1, 0, final_rd1, iv1.length, rd1.length);
            
            return final_rd1;
        } catch (NoSuchAlgorithmException | JSONException | InvalidKeySpecException ex) {
            Logger.getLogger(DecryptBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String bytesToHex(byte[] bytes) {
        // http://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    private static byte[] encryptAES(byte[] clearText, byte[] key, byte[] iv){
        try {
            Cipher cipher_aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            IvParameterSpec iv_spec = new IvParameterSpec(iv);
            SecretKeySpec sks = new SecretKeySpec(key, "AES");
            
            // Instantiate the cipher
            cipher_aes.init(Cipher.ENCRYPT_MODE, sks, iv_spec);
            System.out.println(cipher_aes.getBlockSize());
            
            return cipher_aes.doFinal(clearText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(DecryptBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static byte[] decryptAES(byte[] cipheredText, byte[] key){
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
