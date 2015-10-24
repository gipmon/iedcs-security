package player;

import org.apache.http.Header;

public class BookContent {
    private static Header headers[];
    private static String content;
    
    public BookContent(Header h[], String c){
        headers = h;
        content = c;
    }
    
    public Header[] getHeaders(){
        return headers;
    }
    
    public String getContent(){
        return content;
    }
}
