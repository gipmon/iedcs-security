package player.security;

import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import player.api.Utils;

public class CitizenCard {
    
    private static PublicKey citizen_authentication_certificate;
    private static String GIVENNAME;
    private static String SURNAME;
    private static String SERIALNUMBER;
            
    static{
        reloadPEM();
    }
    
    private static void reloadPEM(){
        try {
            String f = "citizen/CitizenCard.cfg";
            final Provider p = new sun.security.pkcs11.SunPKCS11(f);
            Security.addProvider(p);
            
            class CitizenCallbackHandler implements CallbackHandler{
                public CitizenCallbackHandler(){

                }

                @Override
                public void handle(Callback[] callbacks) {

                }
            }
            
            KeyStore.CallbackHandlerProtection func = new KeyStore.CallbackHandlerProtection( new CitizenCallbackHandler() );
            KeyStore.Builder builder = KeyStore.Builder.newInstance( "PKCS11", p, func);
            KeyStore ks = builder.getKeyStore();
            
            // Enumeration<String> aliases = this.ks.aliases();
            
            X509Certificate cert = (X509Certificate) ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
            cert.getSubjectDN();
            
            String[] alias = cert.getSubjectDN().getName().split(",");
            for(String alia : alias){
                String[] key_values = alia.split("=");
                String key = key_values[0].trim();
                if(key.equals("SERIALNUMBER")){
                    SERIALNUMBER = key_values[1];
                }else if(key.equals("GIVENNAME")){
                    GIVENNAME = key_values[1];
                }else if(key.equals("SURNAME")){
                    SURNAME = key_values[1];
                }
            }
            
            citizen_authentication_certificate = cert.getPublicKey();
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getPublicKeyPem(){
        reloadPEM();
        try {
            RSAPublicKey pub_key = (RSAPublicKey) getPublicKey();
            RSAPublicKeyStructure struct = new RSAPublicKeyStructure(pub_key.getModulus(), pub_key.getPublicExponent());
            ASN1Primitive publicKeyPKCS1ASN1 = struct.toASN1Primitive();
            byte[] publicKeyPKCS1 = publicKeyPKCS1ASN1.getEncoded();
            
            PemObject pemObject = new PemObject("RSA PUBLIC KEY", publicKeyPKCS1);
            StringWriter stringWriter = new StringWriter();
            PemWriter pemWriter = new PemWriter(stringWriter);
            pemWriter.writeObject(pemObject);
            pemWriter.close();
            String pemString = stringWriter.toString();
            
            return pemString;
        } catch (IOException ex) {
            Logger.getLogger(PlayerKeyStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public static PublicKey getPublicKey(){
        return citizen_authentication_certificate;
    }
    
    public static String getSurName(){
        return SURNAME;
    }
    
    public static String getGivenName(){
        return GIVENNAME;
    }
    
    public static String getSerialNumber(){
        return SERIALNUMBER;
    }
    
    public static String[] getRandomAndSign(){
        String[] s = new String[2];
        s[0] = SERIALNUMBER;
        
        return s;
    }
}
