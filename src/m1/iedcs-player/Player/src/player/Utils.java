package player;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;
import org.json.JSONObject;

public class Utils {
    public static void printExceptionStack(Exception e){
      System.err.println(e.getMessage());

      StringWriter writer = new StringWriter();
      PrintWriter printWriter = new PrintWriter( writer );
      e.printStackTrace( printWriter );
      printWriter.flush();

      String stackTrace = writer.toString();
      System.err.println(stackTrace);
    }
    
    public static void openBrowser(String uri) throws IOException, URISyntaxException{
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(new URI(uri));
        }
    }
    
}
