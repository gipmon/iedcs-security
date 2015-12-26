package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.wrapper.CK_RC5_CBC_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for the algorithms
 * Mechanism.RC5_CBC and Mechanism.RC5_CBC_PAD.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class RC5CbcParameters extends RC5Parameters {

	/**
	 * The initialization vector.
	 */
	protected byte[] initializationVector_;

	/**
	 * Create a new RC5CbcParameters object with the given word size,
	 * given rounds and the initialization vector.
	 *
	 * @param wordSize The wordsize of RC5 cipher in bytes.
	 * @param rounds The number of rounds of RC5 encipherment.
	 * @param initializationVector The initialization vector.
	 * @preconditions (initializationVector <> null)
	 * @postconditions
	 */
	public RC5CbcParameters(long wordSize, long rounds, byte[] initializationVector) {
		super(wordSize, rounds);
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
	 *                 and (result instanceof RC5CbcParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		RC5CbcParameters clone = (RC5CbcParameters) super.clone();

		clone.initializationVector_ = (byte[]) this.initializationVector_.clone();

		return clone;
	}

	/**
	 * Get this parameters object as an object of the CK_RC5_CBC_PARAMS
	 * class.
	 *
	 * @return This object as a CK_RC5_CBC_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_RC5_CBC_PARAMS params = new CK_RC5_CBC_PARAMS();

		params.ulWordsize = wordSize_;
		params.ulRounds = rounds_;
		params.pIv = initializationVector_;

		return params;
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

		buffer.append(super.toString());
		buffer.append(Constants.NEWLINE);

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

		if (otherObject instanceof RC5CbcParameters) {
			RC5CbcParameters other = (RC5CbcParameters) otherObject;
			equal = (this == other)
			    || (super.equals(other) && Functions.equals(this.initializationVector_,
			        other.initializationVector_));
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
		return super.hashCode() ^ Functions.hashCode(initializationVector_);
	}

}
