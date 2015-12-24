package player.security;

import iaik.pkcs.pkcs11.wrapper.CK_TOKEN_INFO;
import iaik.pkcs.pkcs11.wrapper.PKCS11;
import iaik.pkcs.pkcs11.wrapper.PKCS11Connector;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.json.JSONException;
import org.json.JSONObject;
import player.api.Requests;
import player.api.Result;
import sun.security.pkcs11.wrapper.PKCS11Exception;
import iaik.pkcs.pkcs11.wrapper.*;
import static player.security.ccCertValidate.validateCertificate;


public class CitizenCard {
    
    private static PublicKey citizen_authentication_certificate;
    private static String GIVENNAME;
    private static String SURNAME;
    private static String SERIALNUMBER;
    private static Key private_key;
    public static boolean cc_is_inserted = true;
    public static boolean canceled = false;

    private static  Provider p;
    
    static{
        reloadPEM();
    }
    
    private static void reloadPEM(){
        try {
            String f = "citizen/CitizenCard.cfg";
            p = new sun.security.pkcs11.SunPKCS11(f);
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
            
            private_key = ks.getKey("CITIZEN AUTHENTICATION CERTIFICATE", null);
            
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
            cc_is_inserted = true;
        } catch (KeyStoreException ex) {
            cc_is_inserted = false;
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            cc_is_inserted = false;
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex){
            cc_is_inserted = false;
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
    
    public static HashMap<String, String> getRandomAndSign() throws IOException{
        reloadPEM();
        try {
            canceled = false;
            HashMap<String, String> parameters = new HashMap<String, String>();
            
            Result rs = Requests.getJson(Requests.GET_TOKEN);
            JSONObject result = (JSONObject)rs.getResult();
            
            parameters.put("random", result.getString("identifier"));
            parameters.put("citizen_card_serial_number", SERIALNUMBER);
            
            Signature sig = Signature.getInstance("SHA256withRSA", p);
            sig.initSign((PrivateKey) private_key);

            sig.update(parameters.get("random").getBytes());
            long [] tokens;
        
            
        
            byte signed[] = sig.sign();

            parameters.put("sign", new String(Base64.getEncoder().encode(signed)));
            return parameters;
        }catch (SignatureException ex) {
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);            
        }catch(JSONException | NoSuchAlgorithmException | InvalidKeyException ex){
            cc_is_inserted = false;
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProviderException ex) {
            canceled = true;
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("random", "");
        parameters.put("sign", "");
        parameters.put("citizen_card_serial_number", "");

        return parameters;
    }
}
