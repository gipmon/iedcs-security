package player;

public class IEDCSPlayer {
    
    private static String baseURL_https = "https://www.bkiedcs.tk/";
    private static String baseURL_http = "http://localhost:8000/";
    
    // change here to http or https
    private static final boolean https = false;
            
    public static String getBaseUrl(){
        return (https)?baseURL_https:baseURL_http;
    }
    
    public static boolean isHttps(){
        return https;
    }
}
