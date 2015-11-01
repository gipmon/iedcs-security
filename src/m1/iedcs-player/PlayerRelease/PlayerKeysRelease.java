import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class PlayerKeysRelease{
  private final static String key = "77RhYLLHHuMx3FmYP7pXUjUWstWVqC26RQbMgeEAZaXfTLgBULYz735Yd95DGxF7RVe3nj4ymsvK3tyMnP7atYL2L4cwy8mmYdhP7sEkzmGq94r94DGG4kWJaSzky9c8";
  private final static char[] password = key.toCharArray();

  // https://docs.oracle.com/javase/7/docs/api/java/security/KeyStore.html
  private static KeyStore ks;
  private static String filename = "PublicPlayerKey.KeyStore";
  private static ProtectionParameter protParam;

  public static void main(String[] args) throws IOException, FileNotFoundException, NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        PrivateKey private_key = kp.getPrivate();
        PublicKey public_key = kp.getPublic();

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
                Logger.getLogger(PlayerKeysRelease.class.getName()).log(Level.SEVERE, null, ex);
            }

            protParam =  new KeyStore.PasswordProtection(password);
        } catch (KeyStoreException ex) {
            Logger.getLogger(PlayerKeysRelease.class.getName()).log(Level.SEVERE, null, ex);
        }

        storeKey("playerPublicKey", public_key);

        File fprivate = new File("private.key");
        File fpublic = new File("public.key");

        // write to private key
        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(private_key.getEncoded());
        ASN1Encodable privateKeyPKCS1ASN1Encodable = pkInfo.parsePrivateKey();
        ASN1Primitive privateKeyPKCS1ASN1 = privateKeyPKCS1ASN1Encodable.toASN1Primitive();
        byte[] privateKeyPKCS1 = privateKeyPKCS1ASN1.getEncoded();

        PemObject pemObject = new PemObject("RSA PRIVATE KEY", privateKeyPKCS1);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        String pemString = stringWriter.toString();

        PrintWriter ptwriter = new PrintWriter(fprivate);
        ptwriter.print(pemString);
        ptwriter.close();

        // write to public key
        FileOutputStream fos = new FileOutputStream(fpublic);
        fos.write(public_key.getEncoded());
        fos.close();
  }

  public static void storeKey(String alias, Key key){
      try {
          // save my secret key
          SecretKeySpec sks = new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
          SecretKey mySecretKey = (SecretKey)sks;

          SecretKeyEntry skEntry = new SecretKeyEntry(mySecretKey);
          ks.setEntry(alias, skEntry, protParam);

          // store away the keystore
          FileOutputStream fos = null;
          try {
              fos = new java.io.FileOutputStream(filename);
              ks.store(fos, password);
          } catch (IOException | CertificateException | NoSuchAlgorithmException ex) {
              Logger.getLogger(PlayerKeysRelease.class.getName()).log(Level.SEVERE, null, ex);
              System.exit(0x1);
          } finally {
              if (fos != null) {
                  try {
                      fos.close();
                  } catch (IOException ex) {
                      System.err.println("Can't close the file!");
                      System.exit(0x1);
                  }
              }
          }
      } catch (KeyStoreException ex) {
          Logger.getLogger(PlayerKeysRelease.class.getName()).log(Level.SEVERE, null, ex);
          System.exit(0x1);
      }
  }
}
