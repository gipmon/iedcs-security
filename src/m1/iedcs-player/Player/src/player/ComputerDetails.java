package player;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;
import sigar.*;

public class ComputerDetails extends SigarCommandBase{
    private static String mac_address;
    
    public ComputerDetails(){
        super();
        try {
            NetInterfaceConfig config = this.sigar.getNetInterfaceConfig(null);
            mac_address = config.getHwaddr();
            
        } catch (SigarException ex) {
            Logger.getLogger(ComputerDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void output(String[] args) throws SigarException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
