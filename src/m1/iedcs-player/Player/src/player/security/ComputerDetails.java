package player.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;
import sigar.*;
import java.util.Base64;

public class ComputerDetails extends SigarCommandBase{
    private static String mac_address;
    private static String cpu_vendor;
    private static String cpu_model;
    private static int cpu_mhz;
    private static int cpu_total_cpus;
    private static String host_name;
    private static ComputerDetails singleton = null;
    
    static{
        File a = new File("sigar");
        try {
            System.out.println(a.getCanonicalPath());
            System.setProperty( "java.library.path", a.getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(ComputerDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(System.getProperty("java.library.path"));
        singleton = new ComputerDetails();
    }
    
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
            
            org.hyperic.sigar.NetInfo net_info = this.sigar.getNetInfo();
            host_name = net_info.getHostName();
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
    
    @Override
    public void output(String[] args) throws SigarException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static String getMac_address() {
        return mac_address;
    }

    public static String getCpu_vendor() {
        return cpu_vendor;
    }

    public static String getCpu_model() {
        return cpu_model;
    }

    public static int getCpu_mhz() {
        return cpu_mhz;
    }

    public static int getCpu_total_cpus() {
        return cpu_total_cpus;
    }
    
    public static String getHostName() {
        return host_name;
    }
    
    public static String getUniqueIdentifier(){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String unique_identifier = mac_address + cpu_vendor + cpu_mhz + cpu_model + cpu_total_cpus;
            
            md.update(unique_identifier.getBytes("UTF-8"));
            byte[] digest = md.digest();
            
            String encoded = Base64.getEncoder().encodeToString(digest);
            
            return encoded;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(ComputerDetails.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return "";
    }
}
