package iaik.pkcs.pkcs11.wrapper;

/**
 * interface CK_CREATEMUTEX.<p>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public interface CK_CREATEMUTEX {

	/**
	 * Method CK_CREATEMUTEX
	 *
	 * @return The mutex (lock) object.
	 * @exception PKCS11Exception If creating the mutex fails.
	 */
	public Object CK_CREATEMUTEX()
	    throws PKCS11Exception;

}
