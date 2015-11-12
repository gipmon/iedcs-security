package player;

public class IEDCSPlayer {
    
    private static String baseURL_https = "https://iedcs.rafaelferreira.pt/";
    private static String baseURL_http = "http://localhost:8000/";
    
    // change here to http or https
    private static final boolean https = true;
            
    public static String getBaseUrl(){
        return (https)?baseURL_https:baseURL_http;
    }
    
    public static boolean isHttps(){
        return https;
    }
}
