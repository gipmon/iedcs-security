package player.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.math.BigInteger;
import static java.nio.channels.spi.AsynchronousChannelProvider.provider;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStore.Builder;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.KeyStoreBuilderParameters;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import org.json.*;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import player.IEDCSPlayer;
import player.security.CitizenCard;
import player.security.ComputerDetails;
import player.security.MyTrustManager;
import player.security.PBKDF2;
import player.security.PlayerKeyStore;
import player.security.Primes;

public class Requests {
    
    public static final String LOGIN_ENDPOINT = IEDCSPlayer.getBaseUrl() + "api/v1/auth/login/";
    public static final String LOGOUT_ENDPOINT = IEDCSPlayer.getBaseUrl() + "api/v1/auth/logout/";
    public static final String ME_ENDPOINT = IEDCSPlayer.getBaseUrl() + "api/v1/me/";
    public static final String USER_BOOKS = IEDCSPlayer.getBaseUrl() + "api/v1/user_books/";
    public static final String VIEW_BOOK = IEDCSPlayer.getBaseUrl() + "api/v1/player/get_book/";
    public static final String CITIZEN_AUTHENTICATION = IEDCSPlayer.getBaseUrl() + "api/v1/player/citizen_authentication/";
    public static final String CITIZEN_AUTHENTICATE = IEDCSPlayer.getBaseUrl() + "api/v1/player/citizen_authenticate/";
    public static final String RETRIEVE_DEVICE = IEDCSPlayer.getBaseUrl() + "api/v1/player/retrieveDevice/";
    public static final String DEVICES = IEDCSPlayer.getBaseUrl() + "api/v1/player/devices/";
    public static final String UPDATE_DEVICE = IEDCSPlayer.getBaseUrl() + "api/v1/player/devices/update/";
    public static final String SECURITY_EXCHANGE_R1_R2 = IEDCSPlayer.getBaseUrl() + "api/v1/player/security_exchange_r1r2/";
    public static final String GET_TOKEN = IEDCSPlayer.getBaseUrl() + "api/v1/player/citizen_token/";
    
    private static JSONObject USER;
    private static HttpClient client = buildHttpsClient();
    private static String csrftoken = "";
    public static int fin;
    
    private static HttpClient buildHttpsClient(){
        if(!IEDCSPlayer.isHttps()){
            return HttpClientBuilder.create().build();
        }
        
        try {
            FileInputStream fis = null;
            fis = new FileInputStream("keystore/cacerts.keystore");
            KeyStore keystore  = KeyStore.getInstance(KeyStore.getDefaultType());
            char[] pwd = "p4g1rr".toCharArray();
            keystore.load(fis, pwd);  
            
            FileInputStream fis1 = null;
            fis1 = new FileInputStream("citizen/CC_KS");
            KeyStore keystore1  = KeyStore.getInstance(KeyStore.getDefaultType());
            char[] pwd1 = "password".toCharArray();
            keystore1.load(fis1, pwd1);  
            
            
            CitizenCard.reloadPEM();
            class CitizenCallbackHandler implements CallbackHandler{
            public CitizenCallbackHandler(){
            }
            @Override            
            public void handle(Callback[] callbacks) {
            }
            }
            
            String f = "citizen/CitizenCard.cfg";
            CitizenCard.p = new sun.security.pkcs11.SunPKCS11(f);
            Security.addProvider(CitizenCard.p);
            KeyStore.CallbackHandlerProtection func = new KeyStore.CallbackHandlerProtection( new CitizenCallbackHandler() );
            KeyStore.Builder builders = KeyStore.Builder.newInstance( "PKCS11", CitizenCard.p, func);
            KeyStore ks = builders.getKeyStore();
       
            Enumeration<String> a = ks.aliases();
            
            while(a.hasMoreElements()){
                String alias = a.nextElement();
                System.out.println(alias);
                keystore1.setCertificateEntry(alias, ks.getCertificate(alias));
            }

            MyTrustManager mtm = new MyTrustManager(keystore1, keystore);
            TrustManager[] tm = new TrustManager[1];
            tm[0] = mtm;
                        
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init(ks, null);
            KeyManager[] km = kmfactory.getKeyManagers();
            
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(km, tm, null);
            final SSLEngine engine = sslcontext.createSSLEngine();
            final SSLParameters sslParams = new SSLParameters();
            sslParams.setNeedClientAuth(true);

            engine.setSSLParameters(sslParams);
            engine.setUseClientMode(true);


            HttpClientBuilder builder = HttpClientBuilder.create();
            SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslcontext); 
            builder.setSSLSocketFactory(sslConnectionFactory);
           
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslConnectionFactory)
                    .build();
            HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);

            builder.setConnectionManager(ccm);

            return builder.build();
        }catch (NoSuchAlgorithmException | KeyStoreException | CertificateException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HttpClientBuilder.create().build();
    }
    

    public static Result login_with_cc(){
        try {
            Result rs = Requests.postJSON(Requests.CITIZEN_AUTHENTICATE, CitizenCard.getRandomAndSign());
            
            if(rs.getStatusCode()==200){
                USER = (JSONObject)rs.getResult();

                // verificar se o player ja esta registado  
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("unique_identifier", ComputerDetails.getUniqueIdentifier());
                Result rs_player = postJSON(RETRIEVE_DEVICE, parameters);

                Utils.println(rs_player.toString());

                if(rs_player.getStatusCode()!=200){
                    parameters = new HashMap<>();
                    parameters.put("unique_identifier", ComputerDetails.getUniqueIdentifier());
                    parameters.put("cpu_model", ComputerDetails.getCpu_vendor());
                    parameters.put("op_system", ComputerDetails.getCpu_model());
                    parameters.put("ip", ComputerDetails.getPublicIP());
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    parameters.put("time", dateFormat.format(date));
                    parameters.put("host_name", ComputerDetails.getHostName());
                    parameters.put("public_key", PlayerKeyStore.getPemPubKey());
                    postJSON(DEVICES, parameters);
                }else{
                    updateDeviceData();
                }
            }
            return rs;
        } catch (ProtocolException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Result login(String email, String password) throws MalformedURLException, ProtocolException, IOException, JSONException{
        HashMap<String, String> parameters = new HashMap<>();
        
        parameters.put("email", email);
        PBKDF2 password_pbkdf2 = new PBKDF2(password, password, 500);
        
        String pass = new String (Base64.getEncoder().encode(password_pbkdf2.read(32)));
        parameters.put("password", pass);
        Result rs = postJSON(LOGIN_ENDPOINT, parameters);
        
        if(Utils.debug()){
            Utils.println(ComputerDetails.getCpu_mhz());
            Utils.println(ComputerDetails.getCpu_model());
            Utils.println(ComputerDetails.getCpu_total_cpus());
            Utils.println(ComputerDetails.getCpu_vendor());
            Utils.println(ComputerDetails.getMac_address());
            Utils.println(ComputerDetails.getPublicIP());
            Utils.println(ComputerDetails.getHostName());
        }
        
        if(rs.getStatusCode()==200){
            USER = (JSONObject)rs.getResult();
            
            // verificar se o player ja esta registado  
            parameters = new HashMap<String, String>();
            parameters.put("unique_identifier", ComputerDetails.getUniqueIdentifier());
            Result rs_player = postJSON(RETRIEVE_DEVICE, parameters);
            
            Utils.println(rs_player.toString());
            
            if(rs_player.getStatusCode()!=200){
                parameters = new HashMap<>();
                parameters.put("unique_identifier", ComputerDetails.getUniqueIdentifier());
                parameters.put("cpu_model", ComputerDetails.getCpu_vendor());
                parameters.put("op_system", ComputerDetails.getCpu_model());
                parameters.put("ip", ComputerDetails.getPublicIP());
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                parameters.put("time", dateFormat.format(date));
                parameters.put("host_name", ComputerDetails.getHostName());
                parameters.put("public_key", PlayerKeyStore.getPemPubKey());
                postJSON(DEVICES, parameters);
            }else{
                updateDeviceData();
            }
        }
        return rs;
    }
    
    public static Result logout() throws MalformedURLException, ProtocolException, IOException, JSONException{
        HashMap<String, String> parameters = new HashMap<String, String>();
        Result rs = postJSON(LOGOUT_ENDPOINT, parameters);
        
        return rs;
    }
    
    private static Result updateDeviceData(){
        HashMap<String, String> parameters = new HashMap<String, String>();
        try {
            parameters.put("cpu_model", ComputerDetails.getCpu_vendor());
            parameters.put("op_system", ComputerDetails.getCpu_model());
            parameters.put("ip", ComputerDetails.getPublicIP());
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            parameters.put("time", dateFormat.format(date));
            parameters.put("host_name", ComputerDetails.getHostName());
            parameters.put("unique_identifier", ComputerDetails.getUniqueIdentifier());
            return putJSON(UPDATE_DEVICE, parameters);
        } catch (ProtocolException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static JSONObject getUser(){
        return USER;
    }
    
    public static Result getJson(String url) throws MalformedURLException, ProtocolException, IOException, JSONException{
        HttpGet get = new HttpGet(url);

        // add header
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        get.setHeader("Accept-Language", "application/json");
        get.setHeader("Content-Type", "application/json;charset=UTF-8");
        
        HttpResponse response = client.execute(get);
        Utils.println("\nSending 'GET' request to URL : " + url);
        Utils.println("Response Code : " + response.getStatusLine().getStatusCode());
        
        Header[] headers = response.getAllHeaders();
        
        if(Utils.debug()){
            Utils.println("Printing Response Header...\n");
            for (Header header : headers) {
                Utils.println("Key : " + header.getName() + " ,Value : " + header.getValue());
            }

            Utils.println("\nGet Response Header By Key ...\n"); 
        }
        
        // output file
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }

        if(Utils.debug()){
            PrintWriter fs = new PrintWriter("output.html");
            fs.print(result.toString());
            fs.close();
        }
        
        Object response_json;
        try{
            response_json = new JSONObject(result.toString());
        }catch(JSONException e){
            response_json = new JSONArray(result.toString());
        }
        
        return (new Result(response.getStatusLine().getStatusCode(), response_json));
    }
    
    public static Result postJSON(String url, HashMap<String, String> parameters) throws MalformedURLException, ProtocolException, IOException, JSONException{
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        post.setHeader("Accept-Language", "application/json");
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        
        if(IEDCSPlayer.isHttps()){
            post.setHeader("Referer", IEDCSPlayer.getBaseUrl());
        }
        
        if(csrftoken.length()>0){
            post.setHeader("X-CSRFToken", csrftoken);
        }
        
        if(parameters != null){
            // to parse parameters to JSON 
            JSONObject json_obj=new JSONObject();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                try {
                    json_obj.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }                           
            }
            StringEntity se = new StringEntity(json_obj.toString(), "UTF-8");
            se.setContentType("application/json; charset=UTF-8");
            post.setEntity(se);
        }
        

        HttpResponse response = client.execute(post);
        
        Utils.println("\nSending 'POST' request to URL : " + url);
        Utils.println("Post parameters : " + post.getEntity());
        Utils.println("Response Code : " + response.getStatusLine().getStatusCode());
        
        // print cookies

        Utils.println("Printing Response Header...\n");
        
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            String[] value_key = header.getValue().toString().split("=");
            if(value_key.length == 5 && value_key[0].equals("csrftoken")){
                csrftoken = value_key[1].split(";")[0];
            }
            Utils.println("Key : " + header.getName() 
                       + " ,Value : " + header.getValue());
        }

        Utils.println("\nGet Response Header By Key ...\n");
        String server = response.getFirstHeader("Server").getValue();

        if(response.getStatusLine().getStatusCode() >= 200  && response.getStatusLine().getStatusCode() < 500 && response.getStatusLine().getStatusCode() != 204){
        // output file response, only for DEBUG!!!! REMOVE!!
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                    result.append(line);
            }
            
            if(Utils.debug()){
                PrintWriter fs = new PrintWriter("output.html");
                fs.print(result.toString());
                fs.close();
            }
         
            Object response_json;
            try{
                response_json = new JSONObject(result.toString());
            }catch(JSONException e){
                response_json = new JSONArray(result.toString());
            
            }
        
            return (new Result(response.getStatusLine().getStatusCode(), response_json));
        }
        
        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                    result.append(line);
            }

            if(Utils.debug()){
                PrintWriter fs = new PrintWriter("output.html");
                fs.print(result.toString());
                fs.close();
            }
        }catch(Exception e){}
        
        return (new Result(response.getStatusLine().getStatusCode(), ""));
        
    }
    
    public static Result putJSON(String url, HashMap<String, String> parameters) throws MalformedURLException, ProtocolException, IOException, JSONException{
        HttpPut put = new HttpPut(url);

        // add header
        put.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        put.setHeader("Accept-Language", "application/json");
        put.setHeader("Content-Type", "application/json;charset=UTF-8");
        
        if(IEDCSPlayer.isHttps()){
            put.setHeader("Referer", IEDCSPlayer.getBaseUrl());
        }
        
        if(csrftoken.length()>0){
            put.setHeader("X-CSRFToken", csrftoken);
        }
        
        // to parse parameters to JSON 
        JSONObject json_obj=new JSONObject();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
                json_obj.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }                           
        }
        StringEntity se = new StringEntity(json_obj.toString(), "UTF-8");
        se.setContentType("application/json; charset=UTF-8");
        put.setEntity(se);

        HttpResponse response = client.execute(put);
        
        Utils.println("\nSending 'PUT' request to URL : " + url);
        Utils.println("Post parameters : " + put.getEntity());
        Utils.println("Response Code : " + response.getStatusLine().getStatusCode());
        
        // print cookies

        Utils.println("Printing Response Header...\n");

        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            String[] value_key = header.getValue().toString().split("=");
            if(value_key.length == 5 && value_key[0].equals("csrftoken")){
                csrftoken = value_key[1].split(";")[0];
            }
            Utils.println("Key : " + header.getName() 
                       + " ,Value : " + header.getValue());
        }

        Utils.println("\nGet Response Header By Key ...\n");

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }
        
        if(Utils.debug()){
           Utils.println(result.toString());
            PrintWriter fs = new PrintWriter("output.html");
            fs.print(result.toString());
            fs.close();
        }
        
        Object response_json;
        try{
            response_json = new JSONObject(result.toString());
        }catch(JSONException e){
            response_json = new JSONArray(result.toString());
        }
        
        return (new Result(response.getStatusLine().getStatusCode(), response_json));
    }
    
    public static Result getBook(String book_identifier){
        BufferedReader rd = null;
        try {
            Result r = updateDeviceData();
            String tmp = r.getResult().toString();
            JSONObject jsonObject = new JSONObject(tmp);
            int p = jsonObject.getInt("p");
            int g = jsonObject.getInt("g");
            int n = jsonObject.getInt("n");
            
            int m = Primes.generateN(g, p);
            
            fin = Primes.generateFinal(p, n);
            
            HttpGet get = new HttpGet(Requests.VIEW_BOOK+book_identifier+"/");
            
            // add header
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
            get.setHeader("Accept-Language", "application/json");
            get.setHeader("Content-Type", "application/json;charset=UTF-8");
            get.setHeader("n", String.valueOf(m));
            
            HttpResponse response = client.execute(get);
            
            Utils.println("\nSending 'GET' request to URL : " + Requests.VIEW_BOOK+book_identifier+"/");
            Utils.println("Response Code : " + response.getStatusLine().getStatusCode());
            
            Header[] headers = response.getAllHeaders();
            
            if(Utils.debug()){
                Utils.println("Printing Response Header...\n");
                for (Header header : headers) {
                   Utils.println("Key : " + header.getName() + " ,Value : " + header.getValue());
                }
            }
            
            // output file and response
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }   
            
            if(Utils.debug()){
                PrintWriter fs = new PrintWriter("output.html");
                fs.print(result.toString());
                fs.close();
            }
            
            BookContent book = new BookContent(headers, result.toString());
            return (new Result(response.getStatusLine().getStatusCode(), book));
        } catch (IOException | UnsupportedOperationException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rd.close();
            } catch (IOException ex) {
                Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    private static TrustManager[] addElement(TrustManager[] a, TrustManager[] e){
        for (TrustManager e1 : e) {
            a  = Arrays.copyOf(a, a.length + 1);
            a[a.length - 1] = e1;
        }
        return a;
    }
    
    private static KeyManager[] addElementKF(KeyManager[] a, KeyManager[] e){
        for (KeyManager e1 : e) {
            a  = Arrays.copyOf(a, a.length + 1);
            a[a.length - 1] = e1;
        }
        return a;
    }
    
}
    