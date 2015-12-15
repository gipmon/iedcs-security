package player.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import player.api.Utils;

public class CitizenCard {
    
    public CitizenCard(){
        try {
            
            String f = "citizen/CitizenCard.cfg";
            Provider p = new sun.security.pkcs11.SunPKCS11( f );
            Security.addProvider( p );
            
            KeyStore.CallbackHandlerProtection func = new KeyStore.CallbackHandlerProtection( new CitizenCallbackHandler() );
            KeyStore.Builder builder = KeyStore.Builder.newInstance( "PKCS11", p, func);
            KeyStore ks = builder.getKeyStore();
            
            Enumeration<String> aliases = ks.aliases();
            
            while (aliases.hasMoreElements()) {
                Utils.println( aliases.nextElement() );
            }
            
            // sign
            
            Signature sig = Signature.getInstance("SHA1withRSA");
            Key key = ks.getKey("CITIZEN AUTHENTICATION CERTIFICATE", null);
            sig.initSign((PrivateKey) key);
            
            PublicKey pub_key;
            
            KeyStore cc = null;
            String pin = "";
            try {
                cc = KeyStore.getInstance("PKCS11",p);
                KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(pin.toCharArray());
                cc.load(null ,  pp.getPassword() );
                aliases = cc.aliases();
                while (aliases.hasMoreElements()) {
                    Object alias = aliases.nextElement();
                    try {
                        X509Certificate cert0 = (X509Certificate) cc.getCertificate(alias.toString());
                        System.out.println("I am: " + cert0.getSubjectDN().getName());
                        pub_key = cert0.getPublicKey();
                        cert0.verify( pub_key );
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte txt[] = "seg".getBytes();
            sig.update(txt);
            
            byte signed[] = sig.sign();
            Utils.println(signed);
            
        } catch (KeyStoreException | NoSuchAlgorithmException | InvalidKeyException | UnrecoverableKeyException ex) {
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void getCitizenCertificates(){
        try {
            ProcessBuilder pb = new ProcessBuilder("./make_certificates.sh");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                Utils.println(line);
            }
            
            KeyStore ks_cartao_cidadao = KeyStore.getInstance(KeyStore.getDefaultType());
            
            // get user password and file input stream
            java.io.FileInputStream fis = null;
            try {
                fis = new java.io.FileInputStream("citizen/CC_KS");
                ks_cartao_cidadao.load(fis, null);
            } catch (NoSuchAlgorithmException | CertificateException ex) {
                Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        } catch (IOException | KeyStoreException ex) {
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    class CitizenCallbackHandler implements CallbackHandler{
        
        public CitizenCallbackHandler(){
            
        }

        public void handle(Callback[] callbacks) {
            
        }

    }
    
    public static void main(String[] args){
        CitizenCard cc = new CitizenCard();
        
    }
}
