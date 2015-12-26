package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.wrapper.CK_RC2_CBC_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for the algorithm Mechanism.RC2_CBC.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (initializationVector_ <> null)
 */
public class RC2CbcParameters extends RC2Parameters {

	/**
	 * The initialization vector.
	 */
	protected byte[] initializationVector_;

	/**
	 * Create a new RC2CbcParameters object with the given effective bits and the
	 * initialization vector.
	 *
	 * @param effectiveBits The effective number of bits in the RC2 search space.
	 * @param initializationVector The initialization vector.
	 * @preconditions (effectiveBits >= 1) and (effectiveBits <= 1024)
	 *                and (initializationVector <> null)
	 * @postconditions
	 */
	public RC2CbcParameters(long effectiveBits, byte[] initializationVector) {
		super(effectiveBits);
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
	 *                 and (result instanceof RC2CbcParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		RC2CbcParameters clone = (RC2CbcParameters) super.clone();

		clone.initializationVector_ = (byte[]) this.initializationVector_.clone();

		return clone;
	}

	/**
	 * Get this parameters object as CK_RC2_CBC_PARAMS object.
	 *
	 * @return This object as CK_RC2_CBC_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_RC2_CBC_PARAMS params = new CK_RC2_CBC_PARAMS();

		params.ulEffectiveBits = effectiveBits_;
		params.iv = initializationVector_;

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

		buffer.append(Constants.INDENT);
		buffer.append("Effective Bits (dec): ");
		buffer.append(effectiveBits_);
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

		if (otherObject instanceof RC2CbcParameters) {
			RC2CbcParameters other = (RC2CbcParameters) otherObject;
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
