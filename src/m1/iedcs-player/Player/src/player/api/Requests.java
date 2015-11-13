package player.api;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
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
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.TrustStrategy;
import player.IEDCSPlayer;
import player.security.ComputerDetails;
import player.security.DecryptBook;
import player.security.PBKDF2;
import player.security.PlayerKeyStore;


public class Requests {
    
    public static final String LOGIN_ENDPOINT = IEDCSPlayer.getBaseUrl() + "api/v1/auth/login/";
    public static final String ME_ENDPOINT = IEDCSPlayer.getBaseUrl() + "api/v1/me/";
    public static final String USER_BOOKS = IEDCSPlayer.getBaseUrl() + "api/v1/user_books/";
    public static final String VIEW_BOOK = IEDCSPlayer.getBaseUrl() + "api/v1/get_book/";
    
    private static JSONObject USER;
    private static HttpClient client = buildHttpsClient();
    private static String csrftoken = "";
    
    private static HttpClient buildHttpsClient(){
        if(!IEDCSPlayer.isHttps()){
            return HttpClientBuilder.create().build();
        }
        try {
            FileInputStream fis = null;
            fis = new FileInputStream("keystore/cacerts.keystore");
            KeyStore keystore  = KeyStore.getInstance("JKS");
            char[] pwd = "p4g1rr".toCharArray();
            keystore.load(fis, pwd);
            
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);
            TrustManager[] tm = tmf.getTrustManagers();
            
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init(keystore, pwd);
            KeyManager[] km = kmfactory.getKeyManagers();
            
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(km, tm, null);

            // set up a TrustManager that trusts everything
            sslcontext.init(km, tm, new SecureRandom());
            
            HttpClientBuilder builder = HttpClientBuilder.create();
            SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            builder.setSSLSocketFactory(sslConnectionFactory);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslConnectionFactory)
                    .build();

            HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);

            builder.setConnectionManager(ccm);

            return builder.build();
        }catch (KeyStoreException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HttpClientBuilder.create().build();
    }
    
    public static Result login(String email, String password) throws MalformedURLException, ProtocolException, IOException, JSONException{
        HashMap<String, String> parameters = new HashMap<String, String>();
        
        parameters.put("email", email);
        PBKDF2 password_pbkdf2 = new PBKDF2(password, password, 500);
        
        String pass = new String (Base64.getEncoder().encode(password_pbkdf2.read(32)));
        parameters.put("password", pass);
        Result rs = postJSON(LOGIN_ENDPOINT, parameters);
        
        // unique identifier
        /*
        System.out.println(ComputerDetails.getCpu_mhz());
        System.out.println(ComputerDetails.getCpu_model());
        System.out.println(ComputerDetails.getCpu_total_cpus());
        System.out.println(ComputerDetails.getCpu_vendor());
        System.out.println(ComputerDetails.getMac_address());
        System.out.println(ComputerDetails.getPublicIP());
        System.out.println(ComputerDetails.getHostName());
        */
        if(rs.getStatusCode()==200){
            USER = (JSONObject)rs.getResult();
            
            // verificar se o player ja esta registado  
            parameters = new HashMap<String, String>();
            parameters.put("unique_identifier", ComputerDetails.getUniqueIdentifier());
            Result rs_player = postJSON(IEDCSPlayer.getBaseUrl() + "api/v1/retrieveDevice/", parameters);
            // System.out.println(rs_player.toString());
            if(rs_player.getStatusCode()!=200){
                parameters = new HashMap<String, String>();
                parameters.put("unique_identifier", ComputerDetails.getUniqueIdentifier());
                parameters.put("cpu_model", ComputerDetails.getCpu_vendor());
                parameters.put("op_system", ComputerDetails.getCpu_model());
                parameters.put("ip", ComputerDetails.getPublicIP());
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                parameters.put("time", dateFormat.format(date));
                parameters.put("host_name", ComputerDetails.getHostName());
                parameters.put("public_key", PlayerKeyStore.getPemPubKey());
                postJSON(IEDCSPlayer.getBaseUrl() + "api/v1/devices/", parameters);
            }else{
                updateDeviceData();
            }
        }
        return rs;
    }
    
    private static void updateDeviceData(){
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
            putJSON(IEDCSPlayer.getBaseUrl() + "api/v1/devices/update/", parameters);
        } catch (ProtocolException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        // System.out.println("\nSending 'GET' request to URL : " + url);
        // System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        
        // print cookies

        // System.out.println("Printing Response Header...\n");
        /*
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
        }
                
        System.out.println("\nGet Response Header By Key ...\n");
        */
        String server = response.getFirstHeader("Server").getValue();

        // output file
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }

        PrintWriter fs = new PrintWriter("output.html");
        fs.print(result.toString());
        fs.close();
        
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
            post.setHeader("Referer", "https://iedcs.rafaelferreira.pt/");
        }
        
        if(csrftoken.length()>0){
            post.setHeader("X-CSRFToken", csrftoken);
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
        post.setEntity(se);

        HttpResponse response = client.execute(post);
        /*
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        
        // print cookies

        System.out.println("Printing Response Header...\n");
        */
        
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            String[] value_key = header.getValue().toString().split("=");
            if(value_key.length == 5 && value_key[0].equals("csrftoken")){
                csrftoken = value_key[1].split(";")[0];
            }
            //System.out.println("Key : " + header.getName() 
            //           + " ,Value : " + header.getValue());
        }

        // System.out.println("\nGet Response Header By Key ...\n");
        String server = response.getFirstHeader("Server").getValue();

        // output file response, only for DEBUG!!!! REMOVE!!
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }

        PrintWriter fs = new PrintWriter("output.html");
        fs.print(result.toString());
        fs.close();
        
        Object response_json;
        try{
            response_json = new JSONObject(result.toString());
        }catch(JSONException e){
            response_json = new JSONArray(result.toString());
        }
        
        return (new Result(response.getStatusLine().getStatusCode(), response_json));
    }
    
    public static Result putJSON(String url, HashMap<String, String> parameters) throws MalformedURLException, ProtocolException, IOException, JSONException{
        HttpPut put = new HttpPut(url);

        // add header
        put.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        put.setHeader("Accept-Language", "application/json");
        put.setHeader("Content-Type", "application/json;charset=UTF-8");
        
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
        System.out.println("\nSending 'PUT' request to URL : " + url);
        System.out.println("Post parameters : " + put.getEntity());
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        
        // print cookies

        System.out.println("Printing Response Header...\n");

        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            String[] value_key = header.getValue().toString().split("=");
            if(value_key.length == 5 && value_key[0].equals("csrftoken")){
                csrftoken = value_key[1].split(";")[0];
            }
            System.out.println("Key : " + header.getName() 
                       + " ,Value : " + header.getValue());
        }

        System.out.println("\nGet Response Header By Key ...\n");
        String server = response.getFirstHeader("Server").getValue();

        // output file response, only for DEBUG!!!! REMOVE!!
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }
        System.out.println(result.toString());
        PrintWriter fs = new PrintWriter("output.html");
        fs.print(result.toString());
        fs.close();
        
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
            updateDeviceData();
            HttpGet get = new HttpGet(Requests.VIEW_BOOK+book_identifier+"/");
            // add header
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
            get.setHeader("Accept-Language", "application/json");
            get.setHeader("Content-Type", "application/json;charset=UTF-8");
            HttpResponse response = client.execute(get);
            System.out.println("\nSending 'GET' request to URL : " + Requests.VIEW_BOOK+book_identifier+"/");
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            // print cookies
            
            System.out.println("Printing Response Header...\n");
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
            }   System.out.println("\nGet Response Header By Key ...\n");
            String server = response.getFirstHeader("Server").getValue();
            // output file
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }   PrintWriter fs = new PrintWriter("output.html");
            fs.print(result.toString());
            fs.close();
            BookContent book = new BookContent(headers, result.toString());
            return (new Result(response.getStatusLine().getStatusCode(), book));
        } catch (IOException | UnsupportedOperationException ex) {
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
}
