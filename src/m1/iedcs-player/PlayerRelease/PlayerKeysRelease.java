import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.io.*;

public class PlayerKeysRelease{
  public static void main(String[] args) throws IOException, FileNotFoundException, NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        PrivateKey private_key = kp.getPrivate();
        PublicKey public_key = kp.getPublic();

        File fpublic = new File("private.key");
        File fprivate = new File("public.key");

        // write to private key
        FileOutputStream fos = new FileOutputStream(fprivate);
        fos.write(private_key.getEncoded());
        fos.close();

        // write to public key
        fos = new FileOutputStream(fpublic);
        fos.write(public_key.getEncoded());
        fos.close();
  }
}
