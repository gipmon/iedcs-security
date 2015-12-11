package player.security;

public class SecurityException extends Exception{
        
    private final String cause;
    
    public SecurityException(String cause){
        super();
        this.cause = cause;
    }
    
    public String cause(){
        return this.cause;
    }
}
