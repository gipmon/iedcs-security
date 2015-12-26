package iaik.pkcs.pkcs11.wrapper;

/**
 * interface CK_DESTROYMUTEX.<p>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public interface CK_DESTROYMUTEX {

	/**
	 * Method CK_DESTROYMUTEX
	 *
	 * @param pMutex The mutex (lock) object.
	 * @exception PKCS11Exception If destroying the mutex fails.
	 */
	public void CK_DESTROYMUTEX(Object pMutex)
	    throws PKCS11Exception;

}
