package player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import java.io.PrintWriter;
import player.json.*;
import org.apache.http.client.methods.HttpPost;

public class Requests {
    
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String LOGIN_ENDPOINT = IEDCSPlayer.getBaseUrl() + "api/v1/auth/login/";
      
    public static void login(String email, String password) throws MalformedURLException, ProtocolException, IOException{
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("email", email);
        parameters.put("password", password);
        post(LOGIN_ENDPOINT, parameters);
    }
    
    public static void post(String url, HashMap<String, String> parameters) throws MalformedURLException, ProtocolException, IOException{
        URL obj = new URL(url);
        
        HttpURLConnection con;
        
        if(url.startsWith("http://")){
            con = (HttpURLConnection) obj.openConnection();
        }else{
            con = (HttpsURLConnection) obj.openConnection(); 
        }

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        
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
        String urlParameters = json_obj.toString();
                
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(((responseCode>=300) ? con.getErrorStream() : con.getInputStream())));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();
         
        
        PrintWriter fs = new PrintWriter("output.html");
        fs.print(response);
        fs.close();
    }
}
