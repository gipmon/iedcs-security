package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for the MAC algorithms for the following
 * mechanisms: DES, DES3 (triple-DES), CAST, CAST3, CAST128 (CAST5), IDEA, and
 * CDMF ciphers.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class MacGeneralParameters implements Parameters {

	/**
	 * The length of the MAC produced, in bytes.
	 */
	protected long macLength_;

	/**
	 * Create a new MacGeneralParameters object with the given MAC length.
	 *
	 * @param macLength The length of the MAC produced, in bytes.
	 * @preconditions
	 * @postconditions
	 */
	public MacGeneralParameters(long macLength) {
		macLength_ = macLength;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof MacGeneralParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		MacGeneralParameters clone;

		try {
			clone = (MacGeneralParameters) super.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as an Long object.
	 *
	 * @return This object as a Long object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		return new Long(macLength_);
	}

	/**
	 * Get the length of the MAC produced, in bytes.
	 *
	 * @return The length of the MAC produced, in bytes.
	 * @preconditions
	 * @postconditions
	 */
	public long getMacLength() {
		return macLength_;
	}

	/**
	 * Set the length of the MAC produced, in bytes.
	 *
	 * @param macLength The length of the MAC produced, in bytes.
	 * @preconditions
	 * @postconditions
	 */
	public void setMacLength(long macLength) {
		macLength_ = macLength;
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
		buffer.append("Mac Length (dec): ");
		buffer.append(macLength_);
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

		if (otherObject instanceof MacGeneralParameters) {
			MacGeneralParameters other = (MacGeneralParameters) otherObject;
			equal = (this == other) || (this.macLength_ == other.macLength_);
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
		return (int) macLength_;
	}

}
