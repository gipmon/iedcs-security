package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for general block ciphers in CBC mode.
 * Those are all Mechanism.*_CBC and Mechanism.*_CBC_PAD mechanisms. This class
 * also applies to other mechanisms which require just an initialization vector
 * as parameter.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (initializationVector_ <> null)
 */
public class InitializationVectorParameters implements Parameters {

	/**
	 * The initialization vector.
	 */
	protected byte[] initializationVector_;

	/**
	 * Create a new InitializationVectorParameters object with the given
	 * initialization vector.
	 *
	 * @param initializationVector The initialization vector.
	 * @preconditions (initializationVector <> null)
	 * @postconditions
	 */
	public InitializationVectorParameters(byte[] initializationVector) {
		if (initializationVector == null) {
			throw new NullPointerException(
			    "Argument \"initializationVector\" must not be null.");
		}
		initializationVector_ = initializationVector;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof InitializationVectorParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		InitializationVectorParameters clone;

		try {
			clone = (InitializationVectorParameters) super.clone();

			clone.initializationVector_ = (byte[]) this.initializationVector_.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as a byte array.
	 *
	 * @return This object as a byte array.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		return initializationVector_;
	}

	/**
	 * Get the initialization vector.
	 *
	 * @return The initialization vector.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public byte[] getInitializationVector() {
		return initializationVector_;
	}

	/**
	 * Set the initialization vector.
	 *
	 * @param initializationVector The initialization vector.
	 * @preconditions (initializationVector <> null)
	 * @postconditions
	 */
	public void setInitializationVector(byte[] initializationVector) {
		if (initializationVector == null) {
			throw new NullPointerException(
			    "Argument \"initializationVector\" must not be null.");
		}
		initializationVector_ = initializationVector;
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
		buffer.append("Initialization Vector (hex): ");
		buffer.append(Functions.toHexString(initializationVector_));
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

		if (otherObject instanceof InitializationVectorParameters) {
			InitializationVectorParameters other = (InitializationVectorParameters) otherObject;
			equal = (this == other)
			    || Functions.equals(this.initializationVector_, other.initializationVector_);
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
		return Functions.hashCode(initializationVector_);
	}

}
