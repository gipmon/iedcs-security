package iaik.pkcs.pkcs11.wrapper;

/**
 * interface CK_UNLOCKMUTEX<p>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public interface CK_UNLOCKMUTEX {

	/**
	 * Method CK_UNLOCKMUTEX
	 *
	 * @param pMutex The mutex (lock) object to unlock.
	 * @exception PKCS11Exception If unlocking the mutex fails.
	 */
	public void CK_UNLOCKMUTEX(Object pMutex)
	    throws PKCS11Exception;

}
