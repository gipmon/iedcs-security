package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.CK_KEY_WRAP_SET_OAEP_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for the Mechanism.KEY_WRAP_SET_OAEP.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class KeyWrapSetOaepParameters implements Parameters {

	/**
	 * The block contents byte.
	 */
	protected byte blockContents_;

	/**
	 * The concatenation of hash of plaintext data (if present) and extra data (if
	 * present).
	 */
	protected byte[] x_;

	/**
	 * Create a new KEADeriveParameters object with the given attributes.
	 *
	 * @param blockContents The block contents byte.
	 * @param x The concatenation of hash of plaintext data (if present) and extra
	 *          data (if present).
	 * @preconditions
	 * @postconditions
	 */
	public KeyWrapSetOaepParameters(byte blockContents, byte[] x) {
		blockContents_ = blockContents;
		x_ = x;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof KeyWrapSetOaepParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		KeyWrapSetOaepParameters clone;

		try {
			clone = (KeyWrapSetOaepParameters) super.clone();

			clone.x_ = (byte[]) this.x_.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as an object of the CK_KEY_WRAP_SET_OAEP_PARAMS
	 * class.
	 *
	 * @return This object as a CK_KEY_WRAP_SET_OAEP_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_KEY_WRAP_SET_OAEP_PARAMS params = new CK_KEY_WRAP_SET_OAEP_PARAMS();

		params.bBC = blockContents_;
		params.pX = x_;

		return params;
	}

	/**
	 * Get the block contents byte.
	 *
	 * @return The block contents byte.
	 * @preconditions
	 * @postconditions
	 */
	public byte getBlockContents() {
		return blockContents_;
	}

	/**
	 * Get the concatenation of hash of plaintext data (if present) and extra
	 * data (if present).
	 *
	 * @return The concatenation of hash of plaintext data (if present) and extra
	 *         data (if present).
	 * @preconditions
	 * @postconditions
	 */
	public byte[] getX() {
		return x_;
	}

	/**
	 * Set the block contents byte.
	 *
	 * @param blockContents The block contents byte.
	 * @preconditions
	 * @postconditions
	 */
	public void setBlockContents(byte blockContents) {
		blockContents_ = blockContents;
	}

	/**
	 * Set the concatenation of hash of plaintext data (if present) and extra data
	 * (if present).
	 *
	 * @param x The concatenation of hash of plaintext data (if present) and extra
	 *          data (if present).
	 * @preconditions
	 * @postconditions
	 */
	public void setX(byte[] x) {
		x_ = x;
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
		buffer.append("Block Contents Byte (hex): ");
		buffer.append(Functions.toHexString(blockContents_));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("X (hex): ");
		buffer.append(Functions.toHexString(x_));
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

		if (otherObject instanceof KeyWrapSetOaepParameters) {
			KeyWrapSetOaepParameters other = (KeyWrapSetOaepParameters) otherObject;
			equal = (this == other)
			    || ((this.blockContents_ == other.blockContents_) && Functions.equals(this.x_,
			        other.x_));
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
		return blockContents_ ^ Functions.hashCode(x_);
	}

}
