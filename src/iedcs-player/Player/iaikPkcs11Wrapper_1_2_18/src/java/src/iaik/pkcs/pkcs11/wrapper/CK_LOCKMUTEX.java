package iaik.pkcs.pkcs11.wrapper;

/**
 * interface CK_LOCKMUTEX<p>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public interface CK_LOCKMUTEX {

	/**
	 * Method CK_LOCKMUTEX
	 *
	 * @param pMutex The mutex (lock) object to lock.
	 * @exception PKCS11Exception If locking the mutex fails.
	 */
	public void CK_LOCKMUTEX(Object pMutex)
	    throws PKCS11Exception;

}
