package player;

public class IEDCSPlayer {
    
    private static final String baseURL_https = "https://iedcs.rafaelferreira.pt:8080/";
    private static final String baseURL_http = "http://localhost:8000/";
    
    private static final String registerURL_http = "http://localhost:8000/";
    private static final String registerURL_https = "https://iedcs.rafaelferreira.pt/";

    // change here to http or https
    private static final boolean https = true;
    public static boolean DEBUG = false;
            
    public static String getBaseUrl(){
        return (https)?baseURL_https:baseURL_http;
    }
    
    public static String getRegisterUrl(){
        return (https)?registerURL_https:registerURL_http;
    }
    
    public static boolean isHttps(){
        return https;
    }
}
