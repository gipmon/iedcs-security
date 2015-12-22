/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player.security;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import javax.security.auth.x500.*;

import iaik.pkcs.pkcs11.wrapper.*;

/**
 *
 * @author André Zúquete
 */
public class ccCertValidate {

    private static long getTokenCertificate ( PKCS11 module, long sessHandle, String label )
            throws PKCS11Exception
    {
        long [] certificates;
        CK_ATTRIBUTE [] attrs = new CK_ATTRIBUTE[1];
        
        // Prepare only 1 search attributes: LABEL (the last function argument)
        attrs[0] = new CK_ATTRIBUTE ();
        attrs[0].type = PKCS11Constants.CKA_LABEL;
        attrs[0].pValue = label.toCharArray();
        
        // Find objects with those attributes (should be only 1, in our case)
        module.C_FindObjectsInit ( sessHandle, attrs );
        certificates = module.C_FindObjects ( sessHandle,  1 );
        module.C_FindObjectsFinal( sessHandle );
        
        System.out.println ( "Found " + certificates.length + " certificate objects with label \"" + label + "\"" );
        return certificates[0];
    }

    private static X509Certificate getCertificate ( PKCS11 module, long token, String certLabel )
    {
        long sessHandle; // session handle
        long certHandle; // certificate handle
        CK_ATTRIBUTE [] attrs = new CK_ATTRIBUTE[1];
	CertificateFactory cf;
	X509Certificate cert;
	String isoName;

	try {

        // Open serial, read-only PKCS#11 session to manipulate token objects
        sessHandle = module.C_OpenSession ( token, PKCS11Constants.CKF_SERIAL_SESSION, null, null );
	
        // Get handle to public key certificate for with a label equal to certLabel
        certHandle = getTokenCertificate ( module, sessHandle, certLabel );

        // Get the contents (value) of the certificate (DER encoded byte array)
        attrs[0] = new CK_ATTRIBUTE ();
        attrs[0].type = PKCS11Constants.CKA_VALUE;
        module.C_GetAttributeValue ( sessHandle , certHandle, attrs );
        
        // Close the session, no more needed
        module.C_CloseSession ( sessHandle );

        } catch (PKCS11Exception e) {
            System.out.println ( "PKCS#11 error: " + e );
	}

	try {

	cf = CertificateFactory.getInstance ( "X.509" );
	cert = (X509Certificate) cf.generateCertificate ( new ByteArrayInputStream ( (byte []) attrs[0].pValue ) );
	//isoName = ((X500Principal) cert.getSubjectX500Principal ()).getName ( javax.security.auth.x500.X500Principal.RFC2253 );
	//System.out.println ( "Certificate of \"" + isoName + "\"" );

	} catch (CertificateException e) {
	    return null;
	}

        return cert;
    }

    private static X509Certificate loadCertFromFile ( String fileName )
    {
        FileInputStream fis;
	CertificateFactory cf;
	X509Certificate cert;

	try {

	fis = new FileInputStream ( fileName );
	cf = CertificateFactory.getInstance ( "X.509" );
	cert = (X509Certificate) cf.generateCertificate ( fis );

	} catch (Exception e) {
	    return null;
	}

	return cert;
    }

    private static ArrayList<X509Certificate> loadCertPath ( String certsPath )
    {
        ArrayList<X509Certificate> certs = new ArrayList<X509Certificate> ();
        String [] fileNames = {
	    //"GTEGlobalRoot.der",
            //"BaltimoreCyberTrustRoot.der",
            "ECRaizEstado.der",
            "Cartao-de-Cidadao-002.cer",
        };

        for (int i = 0; i < fileNames.length; i++) {
	    String path = certsPath + "/" + fileNames[i];
	    X509Certificate cert = loadCertFromFile ( path );
	    if (cert != null) {
		certs.add ( 0, cert );
            }
	    else {
	        System.out.println ( "Could not load certificate from " + path );
	    }
	}

	return certs;
    }

    private static X509Certificate loadCertPathRoot ( String certsPath )
    {
	String path = certsPath + "/BaltimoreCyberTrustRoot.der";
	X509Certificate cert = loadCertFromFile ( path );
	if (cert == null) {
	    System.out.println ( "Could not load root certificate from " + path );
	}

	return cert;
    }

    private static X509CRL getCRL ( String crlUrl, X509Certificate issuer )
    {
	X509CRL crl = null;
	CertificateFactory cf;
    	URL url;

	try {

    	url = new URL ( crlUrl );
	InputStream crlStream = url.openStream ();
	cf = CertificateFactory.getInstance ( "X.509" );
	crl = (X509CRL) cf.generateCRL ( crlStream );
	crlStream.close ();

	crl.verify ( issuer.getPublicKey () );

	} catch (MalformedURLException e) {
	    System.out.println ( "Invalid URL for getting a CRL:" + e );
	} catch (IOException e) {
	    System.out.println ( "Cannot access URL for getting a CRL:" + e );
	} catch (CertificateException e) {
	    System.out.println ( "Cannot create a certificate factory:" + e );
	} catch (CRLException e) {
	    System.out.println ( "Cannot build a local CRL:" + e );
	} catch (NoSuchAlgorithmException e) {
	    System.out.println ( "Invalid algorithm for validating the CRL:" + e );
	} catch (InvalidKeyException e) {
	    System.out.println ( "Invalid key for validating the CRL:" + e );
	} catch (NoSuchProviderException e) {
	    System.out.println ( "Invalid provider for validating the CRL:" + e );
	} catch (SignatureException e) {
	    System.out.println ( "Invalid signature in CRL:" + e );
	}

        return crl;
    }

    private static boolean validateCRL ( List<X509Certificate> certs )
    {
	X509Certificate cert, issuer;
    	Set<String> extensions;
	byte [] extension;
	String crlUrl = null, deltaUrl = null;
	X509CRL crl;
	X509CRLEntry entry;

	if (certs.size () > 2) {
	    List<X509Certificate> reduced = certs.subList ( 1, certs.size () - 1 );
	    validateCRL ( reduced );
	}

	cert = certs.get ( 0 );
	issuer = certs.get ( 1 );

    	// Get non-critical extensions

	extensions = cert.getNonCriticalExtensionOIDs ();
	for (String oid : extensions) {
	     if (oid.equals ( "2.5.29.31" )) {
	         System.out.println ( "CRL Distribution Points:" );
		 extension = cert.getExtensionValue ( oid );
		 crlUrl = (new String ( extension )).substring ( 12 );
	         System.out.println ( "\t" + crlUrl );
	     }
	     else if (oid.equals ( "2.5.29.46" )) {
	         System.out.println ( "FreshestCRL:" );
		 extension = cert.getExtensionValue ( oid );
		 deltaUrl = (new String ( extension )).substring ( 12 );
	         System.out.println ( "\t" + deltaUrl );
	     }
	}

	// Check CRL and Delta CRL

	if (crlUrl != null) {
	    crl = getCRL ( crlUrl, issuer );
	    if (crl == null)
		return false;

	    entry = crl.getRevokedCertificate ( cert );
	    if (entry != null) {
		System.out.println ( "Certificate " + cert.getSubjectX500Principal () +
					" revoked: " + entry.getRevocationReason () );
	    }


	    if (deltaUrl != null) {
		crl = getCRL ( deltaUrl, issuer );
		if (crl == null)
		    return false;

		entry = crl.getRevokedCertificate ( cert );
		if (entry != null) {
		    System.out.println ( "Certificate " + cert.getSubjectX500Principal () +
					" revoked: " + entry.getRevocationReason () );
	    	}
	    }
	}

        return true;
    }

    private static void validateCertificate ( PKCS11 module, long token, String certLabel, String issuerLabel )
    {
	Set<String> extensions;
	byte [] extension;
	X509Certificate cert, issuer;
	CertificateFactory cf = null;
	X509Certificate root;
	ArrayList<X509Certificate> certs;
	CertPath cp = null;
	CertPathValidator cpv = null;

        cert = getCertificate ( module, token, certLabel );
	issuer = getCertificate ( module, token, issuerLabel );
	
	// Check validity

	try {
	    cert.checkValidity ();
	} catch (CertificateExpiredException e) {
	    System.out.println ( "Certificate has already expired (at " + cert.getNotAfter () + ")" );
	} catch (CertificateNotYetValidException e) {
	    System.out.println ( "Certificate has not yet started (only at " + cert.getNotBefore () + ")" );
	}

	root = loadCertPathRoot ( "citizen" );
	certs = loadCertPath ( "citizen" );
	certs.add ( 0, issuer );
	certs.add ( 0, cert );

	try {

	cf = CertificateFactory.getInstance ( "X.509" );
	cp = cf.generateCertPath ( certs );
	//System.out.println ( "Certificate validation path:" + cp );

	} catch (CertificateException e) {
	    System.out.println ( "Problem while building certificate path:" + e );
	}

	try {

	cpv = CertPathValidator.getInstance ( "PKIX" );
        TrustAnchor ta = new TrustAnchor ( root , null );
	PKIXParameters vp = new PKIXParameters ( Collections.singleton ( ta ) );
	vp.setRevocationEnabled ( false );

	cpv.validate ( cp, vp );

	} catch (Exception e) {
	    System.out.println ( "Certificate path validation error:" + e );
	}
       
	List<X509Certificate> certsList = (List<X509Certificate>) cp.getCertificates ();
        validateCRL ( certsList );
}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        long [] tokens;
        
        try {
        
        // Select the correct PKCS#11 module for dealing with Citizen Card tokens
        PKCS11 module = PKCS11Connector.connectToPKCS11Module ( System.getProperty ( "os.name" ).contains ( "Mac OS X" ) ?
								"pteidpkcs11.dylib" : "/usr/local/lib/libpteidpkcs11.dylib" );
      
        // Initialize module
        module.C_Initialize(null);
        
        // Find all Citizen Card tokens
        tokens = module.C_GetSlotList(true);
        
        if (tokens.length == 0) {
            System.out.println ( "No card inserted" );
            return;
        }
        
        // Perform a challenge-response operation using the authentication key pair
        for (int i = 0; i < tokens.length; i++) {
            CK_TOKEN_INFO tokenInfo = module.C_GetTokenInfo ( tokens[i] );
            System.out.println ( "Token label = \"" + new String ( tokenInfo.label ) + "\"" );
            if (String.valueOf ( tokenInfo.label ).startsWith ( "CARTAO DE CIDADAO" )) {
                System.out.println ( "Found CC, model " + new String ( tokenInfo.model ) );
                
                validateCertificate ( module, tokens[i], "CITIZEN AUTHENTICATION CERTIFICATE", "AUTHENTICATION SUB CA" );
                validateCertificate ( module, tokens[i], "CITIZEN SIGNATURE CERTIFICATE", "SIGNATURE SUB CA" );
            }
        
        }
        module.C_Finalize ( null );
        
        } catch (IOException e) {
            System.out.println ( "I/O error: " + e );
        }
        catch (PKCS11Exception e) {
            System.out.println ( "PKCS#11 error: " + e );
        }
    }
}

