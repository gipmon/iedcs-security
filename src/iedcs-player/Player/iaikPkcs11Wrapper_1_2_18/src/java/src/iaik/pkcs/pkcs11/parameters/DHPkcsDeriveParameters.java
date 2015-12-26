package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for the algorithms
 * Mechanism.DH_PKCS_DERIVE.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (publicValue_ <> null)
 */
public class DHPkcsDeriveParameters implements Parameters {

	/**
	 * The initialization vector.
	 */
	protected byte[] publicValue_;

	/**
	 * Create a new DHPkcsDeriveParameters object with the given public value.
	 *
	 * @param publicValue The public value of the other party in the key agreement
	 *                    protocol.
	 * @preconditions (publicValue <> null)
	 * @postconditions
	 */
	public DHPkcsDeriveParameters(byte[] publicValue) {
		publicValue_ = publicValue;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof DHPkcsDeriveParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		DHPkcsDeriveParameters clone;

		try {
			clone = (DHPkcsDeriveParameters) super.clone();

			clone.publicValue_ = (byte[]) this.publicValue_.clone();
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
		return publicValue_;
	}

	/**
	 * Get the public value of the other party in the key agreement protocol.
	 *
	 * @return The public value of the other party in the key agreement protocol.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public byte[] getPublicValue() {
		return publicValue_;
	}

	/**
	 * Set the public value of the other party in the key agreement protocol.
	 *
	 * @param publicValue The public value of the other party in the key agreement
	 *                    protocol.
	 * @preconditions (publicValue <> null)
	 * @postconditions
	 */
	public void setPublicValue(byte[] publicValue) {
		if (publicValue == null) {
			throw new NullPointerException("Argument \"publicValue\" must not be null.");
		}
		publicValue_ = publicValue;
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
		buffer.append("Public Value (hex): ");
		buffer.append(Functions.toHexString(publicValue_));
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

		if (otherObject instanceof DHPkcsDeriveParameters) {
			DHPkcsDeriveParameters other = (DHPkcsDeriveParameters) otherObject;
			equal = (this == other) || Functions.equals(this.publicValue_, other.publicValue_);
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
		return Functions.hashCode(publicValue_);
	}

}
