package iaik.pkcs.pkcs11;

import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;
import iaik.pkcs.pkcs11.wrapper.PKCS11Exception;

/**
 * This class offers a default-implementation of a MutexHandler.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class DefaultMutexHandler implements MutexHandler {

	/**
	 * A simple mutex implementation, but it satisfies the requirements stated in
	 * PKCS#11 v2.11.
	 *
	 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
	 * @version 1.0
	 * @invariants
	 */
	class Mutex {

		/**
		 * Indicates that this mutex is currently locked.
		 */
		protected boolean locked_;

		/**
		 * Default constructor.
		 *
		 * @preconditions
		 * @postconditions (locked_ == false)
		 */
		public Mutex() {
			locked_ = false;
		}

		/**
		 * Locks this mutex. Waits until the mutex gets unlocked, if
		 * it is already locked.
		 *
		 * @exception InterruptedException If the current thread has been inerupted.
		 * @preconditions
		 * @postconditions (locked_ == true)
		 */
		synchronized public void lock()
		    throws InterruptedException
		{
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			try {
				while (locked_)
					wait();
				locked_ = true;
			} catch (InterruptedException ex) {
				notify();
				throw ex;
			}
		}

		/**
		 * Unlocks this mutex. It returns the previous lock-state.
		 *
		 * @return True, if the mutex was locked. False, otherwise.
		 * @preconditions
		 * @postconditions (locked_ == false)
		 *                 and (result == locked_@pre)
		 */
		synchronized public boolean unlock() {
			boolean previousState = locked_;

			locked_ = false;
			notify();

			return previousState;
		}

		/**
		 * Returns the string representation of this object.
		 *
		 * @return the string representation of this object
		 */
		public String toString() {

			return ((locked_) ? "locked" : "unlocked");
		}

	}

	/**
	 * Create a new mutex object.
	 *
	 * @return The new mutex object.
	 * @exception PKCS11Exception If the wrapper should return a differnet value
	 *                            than CKR_OK to the library. It gets the
	 *                            error-code and returns it as CK_RV.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object createMutex()
	    throws PKCS11Exception
	{
		return new Mutex();
	}

	/**
	 * Destroy a mutex object.
	 *
	 * @param mutex The mutex object to destroy.
	 * @exception PKCS11Exception If the wrapper should return a differnet value
	 *                            than CKR_OK to the library. It gets the
	 *                            error-code and returns it as CK_RV.
	 * @preconditions (mutex <> null)
	 * @postconditions
	 */
	public void destroyMutex(Object mutex)
	    throws PKCS11Exception
	{
		// trust in the garbabe collector
	}

	/**
	 * If this method is called on with a mutex object which is not locked, the
	 * calling thread obtains a lock on that mutex object and returns.
	 * If this method is called with a mutex object which is locked by some
	 * thread other than the calling thread, the calling thread blocks and waits
	 * for that mutex to be unlocked.
	 * If this method is called with a a mutex object which is locked by the calling
	 + thread, the behavior of this method call is undefined.
	 *
	 * @param mutex The mutex object to lock.
	 * @exception PKCS11Exception If the wrapper should return a differnet value
	 *                            than CKR_OK to the library. It gets the
	 *                            error-code and returns it as CK_RV.
	 * @preconditions (mutex <> null)
	 * @postconditions
	 */
	public void lockMutex(Object mutex)
	    throws PKCS11Exception
	{
		try {
			Mutex castedMutex = (Mutex) mutex;
			while (true) {
				try {
					castedMutex.lock();
					break;
				} catch (InterruptedException ex) {
					// try again, until we succeed
				}
			}
		} catch (ClassCastException ex) {
			throw new PKCS11Exception(PKCS11Constants.CKR_MUTEX_BAD);
		}
	}

	/**
	 * If this method is called with a mutex object which is locked by the
	 * calling thread, that mutex object is unlocked and the function call
	 * returns. Furthermore: If exactly one thread was blocking on that
	 * particular mutex object, then that thread stops blocking, obtains a lock
	 * on that mutex object, and its lockMutex(Object) call returns.
	 * If more than one thread was blocking on that particular mutex objet, then
	 * exactly one of the blocking threads is selected somehow. That lucky thread
	 * stops blocking, obtains a lock on the mutex object, and its
	 * lockMutex(Object) call returns. All other threads blocking on that
	 * particular mutex object continue to block.
	 * If this method is called with a mutex object which is not locked, then
	 * the method call throws an exception with the error code
	 * PKCS11Constants.CKR_MUTEX_NOT_LOCKED.
	 * If this method is called with a mutex object which is locked by some
	 * thread other than the calling thread, the behavior of this method call is
	 * undefined.
	 *
	 * @param mutex The mutex object to unlock.
	 * @exception PKCS11Exception If the wrapper should return a differnet value
	 *                            than CKR_OK to the library. It gets the
	 *                            error-code and returns it as CK_RV.
	 * @preconditions (mutex <> null)
	 * @postconditions
	 */
	public void unlockMutex(Object mutex)
	    throws PKCS11Exception
	{
		try {
			Mutex castedMutex = (Mutex) mutex;
			castedMutex.unlock();
		} catch (ClassCastException ex) {
			throw new PKCS11Exception(PKCS11Constants.CKR_MUTEX_BAD);
		}
	}

}
