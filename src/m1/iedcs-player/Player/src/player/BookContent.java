package player;

import org.apache.http.Header;

public class BookContent {
    static Header headers[];
    static String content;
    
    public BookContent(Header headers[], String content){
        this.headers = headers;
        this.content = content;
    }
    
    public Header[] getHeaders(){
        return this.headers;
    }
    
    public String getContent(){
        return this.content;
    }
}
