package iaik.pkcs.pkcs11;

import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

/**
 * Objects of this class show the state of a session. This state is only a
 * snapshot of the session's state at the time this state object was created.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class State implements Cloneable {

	/**
	 * Constant for a read-only public session.
	 */
	public final static State RO_PUBLIC_SESSION = new State(
	    PKCS11Constants.CKS_RO_PUBLIC_SESSION);

	/**
	 * Constant for a read-only user session.
	 */
	public final static State RO_USER_FUNCTIONS = new State(
	    PKCS11Constants.CKS_RO_USER_FUNCTIONS);

	/**
	 * Constant for a read-write public session.
	 */
	public final static State RW_PUBLIC_SESSION = new State(
	    PKCS11Constants.CKS_RW_PUBLIC_SESSION);

	/**
	 * Constant for a read-write user session.
	 */
	public final static State RW_USER_FUNCTIONS = new State(
	    PKCS11Constants.CKS_RW_USER_FUNCTIONS);

	/**
	 * Constant for a read-write security officer session.
	 */
	public final static State RW_SO_FUNCTIONS = new State(
	    PKCS11Constants.CKS_RW_SO_FUNCTIONS);

	/**
	 * The status code of this state as defined in PKCS#11.
	 */
	protected long code_;

	/**
	 * Constructor that simply takes the status code as defined in PKCS#11.
	 *
	 * @param code One of: PKCS11Constants.CKS_RO_PUBLIC_SESSION,
	 *                     PKCS11Constants.CKS_RO_USER_FUNCTIONS,
	 *                     PKCS11Constants.CKS_RW_PUBLIC_SESSION,
	 *                     PKCS11Constants.CKS_RW_USER_FUNCTIONS or
	 *                     PKCS11Constants.CKS_RW_SO_FUNCTIONS.
	 *
	 * @preconditions
	 * @postconditions
	 */
	protected State(long code) {
		code_ = code;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof State)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		State clone;

		try {
			clone = (State) super.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Compares the state code of this object with the other
	 * object. Returns only true, if those are equal in both objects.
	 *
	 * @param otherObject The other State object.
	 * @return True, if other is an instance of State and the state code
	 *         of both objects are equal. False, otherwise.
	 * @preconditions
	 * @postconditions
	 */
	public boolean equals(Object otherObject) {
		boolean equal = false;

		if (otherObject instanceof State) {
			State other = (State) otherObject;
			equal = (this == other) || (this.code_ == other.code_);
		}

		return equal;
	}

	/**
	 * The overriding of this method should ensure that the objects of this class
	 * work correctly in a hashtable.
	 *
	 * @return The hash code of this object. Gained from the state code.
	 * @preconditions
	 * @postconditions
	 */
	public int hashCode() {
		return (int) code_;
	}

	/**
	 * Returns the string representation of this object.
	 *
	 * @return The string representation of object
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		String name;
		if (code_ == PKCS11Constants.CKS_RO_PUBLIC_SESSION) {
			name = "Read-Only Public Session";
		} else if (code_ == PKCS11Constants.CKS_RO_USER_FUNCTIONS) {
			name = "Read-Only User Session";
		} else if (code_ == PKCS11Constants.CKS_RW_PUBLIC_SESSION) {
			name = "Read/Write Public Session";
		} else if (code_ == PKCS11Constants.CKS_RW_USER_FUNCTIONS) {
			name = "Read/Write User Functions";
		} else if (code_ == PKCS11Constants.CKS_RW_SO_FUNCTIONS) {
			name = "Read/Write Security Officer Functions";
		} else {
			name = "ERROR: unknown session state with code: " + code_;
		}

		buffer.append(name);

		return buffer.toString();
	}

}
