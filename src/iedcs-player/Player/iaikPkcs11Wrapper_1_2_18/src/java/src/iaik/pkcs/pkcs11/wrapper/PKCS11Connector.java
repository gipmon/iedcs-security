package iaik.pkcs.pkcs11.wrapper;

import java.io.IOException;

/**
 * This class is a sort of factory to get a implementation of the PKCS11
 * interface. By now, this method simply instanciates PKCS11Implementation.
 * For future version, it can be extended to support different implementations
 * for different versions of PKCS#11.
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class PKCS11Connector {

	/**
	 * Empty constructor for internal use only.
	 *
	 * @preconditions
	 * @postconditions
	 */
	protected PKCS11Connector() { /* left empty intentionally */
	}

	/**
	 * Connect to a PKCS#11 module and get an interface to it.
	 *
	 * @param pkcs11ModulePath The path to the PKCS#11 library.
	 * @return The interface object to access the PKCS#11 module.
	 * @exception IOException If finding the module or connecting to it fails.
	 */
	public static PKCS11 connectToPKCS11Module(String pkcs11ModulePath)
	    throws IOException
	{
		return new PKCS11Implementation(pkcs11ModulePath);
	}

	/**
	 * Connect to a PKCS#11 module with the specified PKCS#11-wrapper native library and get an interface to it.
	 *
	 * @param pkcs11ModulePath The path to the PKCS#11 library.
	 * @param pkcs11WrapperPath The absolute path to the PKCS#11-wrapper native library including the filename
	 * @return The interface object to access the PKCS#11 module.
	 * @exception IOException If finding the module or connecting to it fails.
	 */
	public static PKCS11 connectToPKCS11Module(String pkcs11ModulePath,
	                                           String pkcs11WrapperPath)
	    throws IOException
	{
		return new PKCS11Implementation(pkcs11ModulePath, pkcs11WrapperPath);
	}

}
