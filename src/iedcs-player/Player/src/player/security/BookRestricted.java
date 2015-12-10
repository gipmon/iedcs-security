package player.security;

public class BookRestricted extends Exception{
    
    private String cause;
    
    public BookRestricted(String cause){
        super();
        this.cause = cause;
    }
    
    public String cause(){
        return this.cause;
    }
}