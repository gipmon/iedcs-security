package player.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;


public class BouncyCastleRSAGenerator {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        PrivateKey private_key = kp.getPrivate();
        PublicKey public_key = kp.getPublic();

        File fprivate = new File("player");
        File fpublic = new File("player.pub");

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
   
}