package player.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;
import sigar.*;

public class ComputerDetails extends SigarCommandBase{
    private static String mac_address;
    private static String cpu_vendor;
    private static String cpu_model;
    private static int cpu_mhz;
    private static int cpu_total_cpus;
    private static String sn_motherboard;
    
    public ComputerDetails(){
        super();
        try {
            NetInterfaceConfig config = this.sigar.getNetInterfaceConfig(null);
            mac_address = config.getHwaddr();
            
            org.hyperic.sigar.CpuInfo[] infos = this.sigar.getCpuInfoList();

            org.hyperic.sigar.CpuInfo info = infos[0];
            cpu_vendor = info.getVendor();
            cpu_model = info.getModel();
            cpu_mhz = info.getMhz();
            cpu_total_cpus = info.getTotalCores();
            
            sn_motherboard = getMotherboardSN();
            
        } catch (SigarException ex) {
            Logger.getLogger(ComputerDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static String getPublicIP(){
        try {
            // http://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            return in.readLine();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComputerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComputerDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public static String getMotherboardSN() {
        // http://www.rgagnon.com/javadetails/java-0580.html
        String result = "";
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs =
               "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
              + "Set colItems = objWMIService.ExecQuery _ \n"
              + "   (\"Select * from Win32_BaseBoard\") \n"
              + "For Each objItem in colItems \n"
              + "    Wscript.Echo objItem.SerialNumber \n"
              + "    exit for  ' do the first cpu only! \n"
              + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
               result += line;
            }
            input.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result.trim();
    }
    
    @Override
    public void output(String[] args) throws SigarException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
