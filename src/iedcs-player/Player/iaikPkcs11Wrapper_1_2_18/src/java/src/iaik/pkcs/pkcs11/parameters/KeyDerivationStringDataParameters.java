package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.CK_KEY_DERIVATION_STRING_DATA;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for several key derivation mechanisms that
 * need string data as parameter.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (data_ <> null)
 */
public class KeyDerivationStringDataParameters implements Parameters {

	/**
	 * The data.
	 */
	protected byte[] data_;

	/**
	 * Create a new KeyDerivationStringDataParameters object with the given data.
	 *
	 * @param data The string data.
	 * @preconditions (data <> null)
	 * @postconditions
	 */
	public KeyDerivationStringDataParameters(byte[] data) {
		if (data == null) {
			throw new NullPointerException("Argument \"data\" must not be null.");
		}
		data_ = data;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof KeyDerivationStringDataParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		KeyDerivationStringDataParameters clone;

		try {
			clone = (KeyDerivationStringDataParameters) super.clone();

			clone.data_ = (byte[]) this.data_.clone();
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
		CK_KEY_DERIVATION_STRING_DATA params = new CK_KEY_DERIVATION_STRING_DATA();

		params.pData = data_;

		return params;
	}

	/**
	 * Get the string data.
	 *
	 * @return The string data.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public byte[] getData() {
		return data_;
	}

	/**
	 * Set the string data.
	 *
	 * @param data The string data.
	 * @preconditions (data <> null)
	 * @postconditions
	 */
	public void setData(byte[] data) {
		if (data == null) {
			throw new NullPointerException("Argument \"data\" must not be null.");
		}
		data_ = data;
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
		buffer.append("String data (hex): ");
		buffer.append(Functions.toHexString(data_));
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

		if (otherObject instanceof KeyDerivationStringDataParameters) {
			KeyDerivationStringDataParameters other = (KeyDerivationStringDataParameters) otherObject;
			equal = (this == other) || Functions.equals(this.data_, other.data_);
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
		return Functions.hashCode(data_);
	}

}
