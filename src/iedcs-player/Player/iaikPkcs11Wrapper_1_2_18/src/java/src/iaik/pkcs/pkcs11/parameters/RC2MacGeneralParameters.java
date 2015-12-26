package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.wrapper.CK_RC2_MAC_GENERAL_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for the algorithm
 * Mechanism.RC2_MAC_GENERAL.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class RC2MacGeneralParameters extends RC2Parameters {

	/**
	 * The length of the MAC produced, in bytes.
	 */
	protected long macLength_;

	/**
	 * Create a new RC2MacGeneralParameters object with the given effective bits
	 * and given MAC length.
	 *
	 * @param effectiveBits The effective number of bits in the RC2 search space.
	 * @param macLength The length of the MAC produced, in bytes.
	 * @preconditions (effectiveBits >= 1) and (effectiveBits <= 1024)
	 * @postconditions
	 */
	public RC2MacGeneralParameters(long effectiveBits, long macLength) {
		super(effectiveBits);
		macLength_ = macLength;
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
	 * Get this parameters object as an object of the CK_RC2_MAC_GENERAL_PARAMS
	 * class.
	 *
	 * @return This object as a CK_RC2_MAC_GENERAL_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_RC2_MAC_GENERAL_PARAMS params = new CK_RC2_MAC_GENERAL_PARAMS();

		params.ulEffectiveBits = effectiveBits_;
		params.ulMacLength = macLength_;

		return params;
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
		buffer.append("Effective Bits (dec): ");
		buffer.append(effectiveBits_);
		buffer.append(Constants.NEWLINE);

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

		if (otherObject instanceof RC2MacGeneralParameters) {
			RC2MacGeneralParameters other = (RC2MacGeneralParameters) otherObject;
			equal = (this == other)
			    || (super.equals(other) && (this.macLength_ == other.macLength_));
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
		return super.hashCode() ^ ((int) macLength_);
	}

}
