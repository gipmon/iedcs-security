package player.api;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

public class Utils {
    
    private static final boolean DEBUG = false;
    
    public static void openBrowser(String uri) throws IOException, URISyntaxException{
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(new URI(uri));
        }
    }
    
    public static void println(Object out){
        if(!DEBUG){
            System.out.println(out);
        }
    }
}
