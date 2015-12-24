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
import player.FrontPageController;

/**
 *
 * @author André Zúquete with some changes made by Rodrigo Cunha and Rafael Ferreira
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
        
        return certificates[0];
    }

    private static X509Certificate getCertificate ( PKCS11 module, long token, String certLabel ) throws ccException
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
            String a = "PKCS#11 error: " + e.getLocalizedMessage();
            throw new ccException(a);
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

    private static ArrayList<X509Certificate> loadCertPath ( String certsPath ) throws ccException
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
	        String a = "Could not load certificate from " + path;
                throw new ccException(a);
	    }
	}

	return certs;
    }

    private static X509Certificate loadCertPathRoot ( String certsPath ) throws ccException
    {
	String path = certsPath + "/BaltimoreCyberTrustRoot.der";
	X509Certificate cert = loadCertFromFile ( path );
	if (cert == null) {
	    String a = "Could not load root certificate from " + path ;
            throw new ccException(a);
	}

	return cert;
    }

    private static X509CRL getCRL ( String crlUrl, X509Certificate issuer ) throws ccException
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
	    String a = "Invalid URL for getting a CRL:" + e.getLocalizedMessage();
            throw new ccException(a);
	} catch (IOException e) {
	    String a ="Cannot access URL for getting a CRL:" + e.getLocalizedMessage();
            throw new ccException(a);
	} catch (CertificateException e) {
	    String a = "Cannot create a certificate factory:" + e.getLocalizedMessage();
            throw new ccException(a);
	} catch (CRLException e) {
	    String a = "Cannot build a local CRL:" + e.getLocalizedMessage();
            throw new ccException(a);
	} catch (NoSuchAlgorithmException e) {
	    String a = "Invalid algorithm for validating the CRL:" + e.getLocalizedMessage();
            throw new ccException(a);
	} catch (InvalidKeyException e) {
	    String a = "Invalid key for validating the CRL:" + e.getLocalizedMessage();
            throw new ccException(a);
	} catch (NoSuchProviderException e) {
	    String a = "Invalid provider for validating the CRL:" + e.getLocalizedMessage();
            throw new ccException(a);
	} catch (SignatureException e) {
	    String a = "Invalid signature in CRL:" + e.getLocalizedMessage();
            throw new ccException(a);
	}

        return crl;
    }

    private static boolean validateCRL ( List<X509Certificate> certs ) throws ccException
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
		String a = "Certificate " + cert.getSubjectX500Principal () +
					" revoked: " + entry.getRevocationReason () ;
                throw new ccException(a);
	    }


	    if (deltaUrl != null) {
		crl = getCRL ( deltaUrl, issuer );
		if (crl == null)
		    return false;

		entry = crl.getRevokedCertificate ( cert );
		if (entry != null) {
		    String a = "Certificate " + cert.getSubjectX500Principal () +
					" revoked: " + entry.getRevocationReason ();
                    throw new ccException(a);
	    	}
	    }
	}

        return true;
    }

    public static void validateCertificate ( PKCS11 module, long token, String certLabel, String issuerLabel ) throws ccException
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
	    String a = "Certificate has already expired (at " + cert.getNotAfter () + ")";
            throw new ccException(a);
	} catch (CertificateNotYetValidException e) {
	    String a = "Certificate has not yet started (only at " + cert.getNotBefore () + ")";
            throw new ccException(a);
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
	    String a = "Problem while building certificate path:" + e.getLocalizedMessage();
            throw new ccException(a);

	}

	try {

	cpv = CertPathValidator.getInstance ( "PKIX" );
        TrustAnchor ta = new TrustAnchor ( root , null );
	PKIXParameters vp = new PKIXParameters ( Collections.singleton ( ta ) );
	vp.setRevocationEnabled ( false );

	cpv.validate ( cp, vp );

	} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertPathValidatorException e) {
            String a = "Certificate path validation error: " + e.getLocalizedMessage() ;
            throw new ccException(a);
	}
       
	List<X509Certificate> certsList = (List<X509Certificate>) cp.getCertificates ();
        validateCRL ( certsList );
    }
    public static class ccException extends Exception{
        private String message;
        public ccException(String e){
            super();
            this.message = e;
        }
        public String getMessage(){
            return this.message;
        }   
        
        
    }
}



