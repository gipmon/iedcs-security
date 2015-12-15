package player.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
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
    
    private static KeyStore ks;
    private static PublicKey citizen_authentication_certificate;
            
    static{
        try {
            String f = "citizen/CitizenCard.cfg";
            Provider p = new sun.security.pkcs11.SunPKCS11(f);
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
            ks = builder.getKeyStore();
            
            // Enumeration<String> aliases = this.ks.aliases();
            
            X509Certificate cert = (X509Certificate) ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
            // System.out.println("I am: " + cert.getSubjectDN().getName());
            citizen_authentication_certificate = cert.getPublicKey();
            
            
//
//            while (aliases.hasMoreElements()) {
//                Object alias = aliases.nextElement();
//                try {
//                } catch (Exception e) {
//                    continue;
//                }
//            }
            
            // sign
            
            // Signature sig = Signature.getInstance("SHA1withRSA");
            // Key key = ks.getKey("CITIZEN AUTHENTICATION CERTIFICATE", null);
            // sig.initSign((PrivateKey) key);
//            
//            
//            KeyStore cc = null;
//            String pin = "";
//            try {
//                cc = KeyStore.getInstance("PKCS11",p);
//                KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(pin.toCharArray());
//                cc.load(null ,  pp.getPassword() );
//                aliases = cc.aliases();
//                while (aliases.hasMoreElements()) {
//                    Object alias = aliases.nextElement();
//                    try {
//                        X509Certificate cert0 = (X509Certificate) cc.getCertificate(alias.toString());
//                        System.out.println("I am: " + cert0.getSubjectDN().getName());
//                        pub_key = cert0.getPublicKey();
//                        cert0.verify( pub_key );
//                    } catch (Exception e) {
//                        continue;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            byte txt[] = "seg".getBytes();
//            sig.update(txt);
//            
//            byte signed[] = sig.sign();
//            Utils.println(signed);
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(CitizenCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getPublicKeyPem(){
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
    
}
