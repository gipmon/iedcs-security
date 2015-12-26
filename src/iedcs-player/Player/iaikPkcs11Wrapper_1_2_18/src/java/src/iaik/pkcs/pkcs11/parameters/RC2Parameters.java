package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for the algorithms
 * Mechanism.RC2_ECB and Mechanism.RC2_MAC.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class RC2Parameters implements Parameters {

	/**
	 * The effective number of bits in the RC2 search space.
	 */
	protected long effectiveBits_;

	/**
	 * Create a new RC2Parameters object with the given effective bits.
	 *
	 * @param effectiveBits The effective number of bits in the RC2 search space.
	 * @preconditions (effectiveBits >= 1) and (effectiveBits <= 1024)
	 * @postconditions
	 */
	public RC2Parameters(long effectiveBits) {
		if ((effectiveBits < 1) || (effectiveBits > 1024)) {
			throw new IllegalArgumentException(
			    "Argument \"effectiveBits\" must be in the range [1, 1024].");
		}
		effectiveBits_ = effectiveBits;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof RC2Parameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		RC2Parameters clone;

		try {
			clone = (RC2Parameters) super.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as Long object.
	 *
	 * @return This object as Long object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		return new Long(effectiveBits_);
	}

	/**
	 * Get the effective number of bits in the RC2 search space.
	 *
	 * @return The effective number of bits in the RC2 search space.
	 * @preconditions
	 * @postconditions (result >= 1) and (result <= 1024)
	 */
	public long getEffectiveBits() {
		return effectiveBits_;
	}

	/**
	 * Set the effective number of bits in the RC2 search space.
	 *
	 * @param effectiveBits The effective number of bits in the RC2 search space.
	 * @preconditions (effectiveBits >= 1) and (effectiveBits <= 1024)
	 * @postconditions
	 */
	public void setEffectiveBits(long effectiveBits) {
		if ((effectiveBits < 1) || (effectiveBits > 1024)) {
			throw new IllegalArgumentException(
			    "Argument \"effectiveBits\" must be in the range [1, 1024].");
		}
		effectiveBits_ = effectiveBits;
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
		//buffer.append(Constants.NEWLINE);

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

		if (otherObject instanceof RC2Parameters) {
			RC2Parameters other = (RC2Parameters) otherObject;
			equal = (this == other) || (this.effectiveBits_ == other.effectiveBits_);
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
		return (int) effectiveBits_;
	}

}
