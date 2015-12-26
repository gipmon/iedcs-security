package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for Mechanisms.EXTRACT_KEY_FROM_KEY.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class ExtractParameters implements Parameters {

	/**
	 * The bit of the base key that should be used as the first bit of the derived
	 * key.
	 */
	protected long bitIndex_;

	/**
	 * Create a new ExtractParameters object with the given bit index.
	 *
	 * @param bitIndex The bit of the base key that should be used as the first
	 *                 bit of the derived key.
	 * @preconditions
	 * @postconditions
	 */
	public ExtractParameters(long bitIndex) {
		bitIndex_ = bitIndex;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof ExtractParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		ExtractParameters clone;

		try {
			clone = (ExtractParameters) super.clone();
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
		return new Long(bitIndex_);
	}

	/**
	 * Get the bit of the base key that should be used as the first bit of the
	 * derived key.
	 *
	 * @return The bit of the base key that should be used as the first bit of the
	 *         derived key.
	 * @preconditions
	 * @postconditions
	 */
	public long getBitIndex() {
		return bitIndex_;
	}

	/**
	 * Set the bit of the base key that should be used as the first bit of the
	 * derived key.
	 *
	 * @param bitIndex The bit of the base key that should be used as the first
	 *                 bit of the derived key.
	 * @preconditions
	 * @postconditions
	 */
	public void setBitIndex(long bitIndex) {
		bitIndex_ = bitIndex;
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
		buffer.append("Bit Index (dec): ");
		buffer.append(bitIndex_);
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

		if (otherObject instanceof ExtractParameters) {
			ExtractParameters other = (ExtractParameters) otherObject;
			equal = (this == other) || (this.bitIndex_ == other.bitIndex_);
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
		return (int) bitIndex_;
	}

}
