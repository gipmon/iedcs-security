package player.security;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MyTrustManager implements X509TrustManager{
    private X509TrustManager cartaocidadao;
    private X509TrustManager server;
    
    public MyTrustManager(KeyStore cartaodecidadao, KeyStore server){
        TrustManagerFactory tmf, tmf1;
        try {
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(cartaodecidadao);
            this.cartaocidadao = (X509TrustManager) tmf.getTrustManagers()[0];
            tmf1 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf1.init(server);
            this.server = (X509TrustManager) tmf1.getTrustManagers()[0];
        } catch (KeyStoreException | NoSuchAlgorithmException ex) {
            Logger.getLogger(MyTrustManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        this.cartaocidadao.checkClientTrusted(xcs, string);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        this.server.checkServerTrusted(xcs, string);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        X509Certificate[] acceptIssuesCC = this.cartaocidadao.getAcceptedIssuers();
        X509Certificate[] acceptIssuesServer = this.server.getAcceptedIssuers();
        
        X509Certificate[] all = new X509Certificate[acceptIssuesCC.length + acceptIssuesServer.length];
        System.arraycopy(acceptIssuesCC, 0, all, 0, acceptIssuesCC.length-1);
        System.arraycopy(acceptIssuesServer, 0, all, acceptIssuesCC.length, acceptIssuesServer.length-1);
        return all;
    }
}
