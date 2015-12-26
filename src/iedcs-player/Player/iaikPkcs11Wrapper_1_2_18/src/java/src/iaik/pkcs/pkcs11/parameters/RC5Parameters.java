package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.CK_RC5_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for the algorithms
 * Mechanism.RC5_ECB and Mechanism.RC5_MAC.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class RC5Parameters implements Parameters {

	/**
	 * The wordsize of RC5 cipher in bytes.
	 */
	protected long wordSize_;

	/**
	 * The number of rounds of RC5 encipherment.
	 */
	protected long rounds_;

	/**
	 * Create a new RC5Parameters object with the given word size
	 * and given rounds.
	 *
	 * @param wordSize The wordsize of RC5 cipher in bytes.
	 * @param rounds The number of rounds of RC5 encipherment.
	 * @preconditions
	 * @postconditions
	 */
	public RC5Parameters(long wordSize, long rounds) {
		wordSize_ = wordSize;
		rounds_ = rounds;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof RC5Parameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		RC5Parameters clone;

		try {
			clone = (RC5Parameters) super.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get the wordsize of RC5 cipher in bytes.
	 *
	 * @return The wordsize of RC5 cipher in bytes.
	 * @preconditions
	 * @postconditions
	 */
	public long getWordSize() {
		return wordSize_;
	}

	/**
	 * Get number of rounds of RC5 encipherment.
	 *
	 * @return The number of rounds of RC5 encipherment.
	 * @preconditions
	 * @postconditions
	 */
	public long getRounds() {
		return rounds_;
	}

	/**
	 * Get this parameters object as an object of the CK_RC5_PARAMS
	 * class.
	 *
	 * @return This object as a CK_RC5_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_RC5_PARAMS params = new CK_RC5_PARAMS();

		params.ulWordsize = wordSize_;
		params.ulRounds = rounds_;

		return params;
	}

	/**
	 * Set the wordsize of RC5 cipher in bytes.
	 *
	 * @param wordSize The wordsize of RC5 cipher in bytes.
	 * @preconditions
	 * @postconditions
	 */
	public void setWordSize(long wordSize) {
		wordSize_ = wordSize;
	}

	/**
	 * Set the number of rounds of RC5 encipherment.
	 *
	 * @param rounds The number of rounds of RC5 encipherment.
	 * @preconditions
	 * @postconditions
	 */
	public void setMacLength(long rounds) {
		rounds_ = rounds;
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
		buffer.append("Word Size (dec): ");
		buffer.append(wordSize_);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Rounds (dec): ");
		buffer.append(rounds_);
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

		if (otherObject instanceof RC5Parameters) {
			RC5Parameters other = (RC5Parameters) otherObject;
			equal = (this == other)
			    || ((this.wordSize_ == other.wordSize_) && (this.rounds_ == other.rounds_));
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
		return ((int) wordSize_) ^ ((int) rounds_);
	}

}
