package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.CK_PBE_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for the Mechanism.PBA_* and
 * Mechanism.PBA_SHA1_WITH_SHA1_HMAC mechanisms.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (initializationVector_ == null)
 *             or ((initializationVector_ <> null)
 *                 and (initializationVector_.length == 8))
 *             and (password_ <> null)
 *             and (salt_ <> null)
 */
public class PBEParameters implements Parameters {

	/**
	 * The 8-byte initialization vector (IV), if an IV is required.
	 */
	protected char[] initializationVector_;

	/**
	 * The password to be used in the PBE key generation.
	 */
	protected char[] password_;

	/**
	 * The salt to be used in the PBE key generation.
	 */
	protected char[] salt_;

	/**
	 * The number of iterations required for the generation.
	 */
	protected long iterations_;

	/**
	 * Create a new PBEDeriveParameters object with the given attributes.
	 *
	 * @param initializationVector The 8-byte initialization vector (IV), if an
	 *                             IV is required.
	 * @param password The password to be used in the PBE key generation.
	 * @param salt The salt to be used in the PBE key generation.
	 * @param iterations The number of iterations required for the generation.
	 * @preconditions (initializationVector == null)
	 *                or ((initializationVector <> null)
	 *                    and (initializationVector.length == 8))
	 *                and (password <> null)
	 *                and (salt <> null)
	 * @postconditions
	 */
	public PBEParameters(char[] initializationVector,
	                     char[] password,
	                     char[] salt,
	                     long iterations)
	{
		if ((initializationVector != null) && (initializationVector.length != 8)) {
			throw new IllegalArgumentException(
			    "Argument \"initializationVector\" must be null or must have length "
			        + "8, if it is not null.");
		}
		if (password == null) {
			throw new NullPointerException("Argument \"password\" must not be null.");
		}
		if (salt == null) {
			throw new NullPointerException("Argument \"salt\" must not be null.");
		}
		initializationVector_ = initializationVector;
		password_ = password;
		salt_ = salt;
		iterations_ = iterations;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof PBEParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		PBEParameters clone;

		try {
			clone = (PBEParameters) super.clone();

			clone.initializationVector_ = (char[]) this.initializationVector_.clone();
			clone.password_ = (char[]) this.password_.clone();
			clone.salt_ = (char[]) this.salt_.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as an object of the CK_PBE_PARAMS
	 * class.
	 *
	 * @return This object as a CK_PBE_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_PBE_PARAMS params = new CK_PBE_PARAMS();

		params.pInitVector = initializationVector_;
		params.pPassword = password_;
		params.pSalt = salt_;
		params.ulIteration = iterations_;

		return params;
	}

	/**
	 * Get the 8-byte initialization vector (IV), if an IV is required.
	 *
	 * @return The 8-byte initialization vector (IV), if an IV is required.
	 * @preconditions
	 * @postconditions (result == null)
	 *                 or ((result <> null)
	 *                     and (result.length == 8))
	 */
	public char[] getInitializationVector() {
		return initializationVector_;
	}

	/**
	 * Get the password to be used in the PBE key generation.
	 *
	 * @return The password to be used in the PBE key generation.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public char[] getPassword() {
		return password_;
	}

	/**
	 * Get the salt to be used in the PBE key generation.
	 *
	 * @return The salt to be used in the PBE key generation.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public char[] getSalt() {
		return salt_;
	}

	/**
	 * Get the number of iterations required for the generation.
	 *
	 * @return The number of iterations required for the generation.
	 * @preconditions
	 * @postconditions
	 */
	public long getIterations() {
		return iterations_;
	}

	/**
	 * Set the 8-byte initialization vector (IV), if an IV is required.
	 *
	 * @param initializationVector The 8-byte initialization vector (IV), if an
	 *                             IV is required.
	 * @preconditions (initializationVector == null)
	 *                or ((initializationVector <> null)
	 *                    and (initializationVector.length == 8))
	 * @postconditions
	 */
	public void setInitializationVector(char[] initializationVector) {
		if ((initializationVector != null) && (initializationVector.length != 8)) {
			throw new IllegalArgumentException(
			    "Argument \"initializationVector\" must be null or must have length "
			        + "8, if it is not null.");
		}
		initializationVector_ = initializationVector;
	}

	/**
	 * Set the password to be used in the PBE key generation.
	 *
	 * @param password The password to be used in the PBE key generation.
	 * @preconditions (password <> null)
	 * @postconditions
	 */
	public void setPassword(char[] password) {
		if (password == null) {
			throw new NullPointerException("Argument \"password\" must not be null.");
		}
		password_ = password;
	}

	/**
	 * Set the salt to be used in the PBE key generation.
	 *
	 * @param salt The salt to be used in the PBE key generation.
	 * @preconditions (salt <> null)
	 * @postconditions
	 */
	public void setSalt(char[] salt) {
		if (salt == null) {
			throw new NullPointerException("Argument \"salt\" must not be null.");
		}
		salt_ = salt;
	}

	/**
	 * Set the number of iterations required for the generation.
	 *
	 * @param iterations The number of iterations required for the generation.
	 * @preconditions
	 * @postconditions
	 */
	public void setIterations(long iterations) {
		iterations_ = iterations;
	}

	/**
	 * Returns the string representation of this object. Do not parse data from
	 * this string, it is for debugging only.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("Initialization Vector: ");
		buffer.append((initializationVector_ != null) ? new String(initializationVector_)
		    : null);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Password: ");
		buffer.append((password_ != null) ? new String(password_) : null);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Salt: ");
		buffer.append((salt_ != null) ? new String(salt_) : null);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Iterations (dec): ");
		buffer.append(iterations_);
		// buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

	/**
	 * Compares all member variables of this object with the other object.
	 * Returns only true, if all are equal in both objects.
	 *
	 * @param otherObject The other object to compare to.
	 * @return True, if other is an instance of this class and all member
	 *         variables of both objects are equal. False, otherwise.
	 * @preconditions
	 * @postconditions
	 */
	public boolean equals(java.lang.Object otherObject) {
		boolean equal = false;

		if (otherObject instanceof PBEParameters) {
			PBEParameters other = (PBEParameters) otherObject;
			equal = (this == other)
			    || ((Functions.equals(this.initializationVector_, other.initializationVector_)
			        && Functions.equals(this.password_, other.password_)
			        && Functions.equals(this.salt_, other.salt_) && this.iterations_ == other.iterations_));
		}

		return equal;
	}

	/**
	 * The overriding of this method should ensure that the objects of this class
	 * work correctly in a hashtable.
	 *
	 * @return The hash code of this object.
	 * @preconditions
	 * @postconditions
	 */
	public int hashCode() {
		return Functions.hashCode(initializationVector_) ^ Functions.hashCode(password_)
		    ^ Functions.hashCode(salt_) ^ ((int) iterations_);
	}

}
