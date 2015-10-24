

public class PlayerRelease{
  public static void main(String[] args) {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        Key publicKeyDevice = kp.getPublic();
        Key privateKeyDevice = kp.getPrivate();

        
  }
}
