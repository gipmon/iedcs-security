package iaik.pkcs.pkcs11;

import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;
import iaik.pkcs.pkcs11.wrapper.PKCS11Exception;

/**
 * Interface for notification callbacks. Object implementing this interface
 * can be passed to the openSession method of a token.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public interface Notify {

	/**
	 * This is the code to return in a PKCS11Exception to signal surrender
	 * to the library.
	 */
	public static final long CANCEL = PKCS11Constants.CKR_CANCEL;

	/**
	 * The module calls this method in certain events. 'Surrender' is the only
	 * event defined by now. If the application wants to return an error code,
	 * it can do this using PKCS11Exceptions. Throwing no exception means a
	 * return value of CKR_OK, and trowing an PKCS11Exception means a return
	 * value of the error code of the exception; e.g.<code><br>
	 * throw new PKCS11Exception(PKCS11Constants.CKR_CANCEL);<br>
	 * </code><br>
	 * causes a return value of CKR_CANCEL.
	 *
	 * @param session The session performing the callback.
	 * @param surrender See CK_NOTIFICATION in PKCS#11. A return value of CKR_OK
	 *                  is generatd, if this method call returns regularly.
	 *                  CKR_CANCEL can be returned to the module by throwing a
	 *                  PKCS11Exception with the error-code CKR_CANCEL.
	 * @param application The application-object passed to openSession.
	 * @exception PKCS11Exception If the method fails for some reason, or as
	 *                            PKCS11Exception with error-code CKR_CANCEL
	 *                            to signal the module to cancel the ongoing
	 *                            operation.
	 * @preconditions (session <> null)
	 * @postconditions
	 */
	public void notify(Session session, boolean surrender, Object application)
	    throws PKCS11Exception;

}
