package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters CBC key derivation algorithms.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public abstract class CbcEncryptDataParameters implements Parameters {

	/**
	 * This is the block size in byte of the underlying cipher, 
	 * e.g. 8 for DES and Triple DES and 16 for AES. 
	 */
	protected int blockSize_;

	/**
	 * The initialization vector for CBC mode of the cipher.
	 */
	protected byte[] iv_;

	/**
	 * The data to be used in the key derivation. It must have a length that is
	 * a multiple of the block-size of the underlying cipher.
	 */
	protected byte[] data_;

	/**
	 * Create a new CbcEncryptDataParameters object with the given IV and
	 * data.
	 *
	 * @param blockSize The block size of the cipher.
	 * @param iv The initialization vector which's length must be block size.
	 * @param data The key derivation data which's length must be multiple of the block size.
	 * @preconditions (blockSize > 0)
	 *                and (iv <> null) and (iv.length == blockSize)
	 *                and (data <> null) and (data.length%blockSize == 0)
	 * @postconditions
	 */
	protected CbcEncryptDataParameters(int blockSize, byte[] iv, byte[] data) {
		if (iv == null) {
			throw new NullPointerException("Argument \"iv\" must not be null.");
		}
		if (iv.length != blockSize) {
			throw new IllegalArgumentException("Argument \"iv\" must have length blockSize.");
		}
		if (data == null) {
			throw new NullPointerException("Argument \"data\" must not be null.");
		}
		if (data.length % blockSize != 0) {
			throw new IllegalArgumentException(
			    "Argument \"data\" must have a length that is a multiple of blockSize.");
		}
		blockSize_ = blockSize;
		iv_ = iv;
		data_ = data;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof DesCbcEncryptDataParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}
	}

	/**
	 * Get the block size of the underlying cipher in bytes.
	 * 
	 * @return The block size in bytes.
	 */
	public int getBlockSize() {
		return blockSize_;
	}

	/**
	 * Get the initialization vector for CBC mode.
	 *
	 * @return The initialization vector.
	 * @preconditions
	 * @postconditions (result <> null) and (result.length == getBlockSize())
	 */
	public byte[] getInitializationVector() {
		return iv_;
	}

	/**
	 * Set the initialization vector for CBC mode.
	 *
	 * @param iv The initialization vector.
	 * @preconditions (iv <> null) and (iv.length == getBlockSize())
	 * @postconditions
	 */
	public void setInitializationVector(byte[] iv) {
		if (iv == null) {
			throw new NullPointerException("Argument \"iv\" must not be null.");
		}
		if (iv.length != blockSize_) {
			throw new IllegalArgumentException(
			    "Argument \"iv\" must have length getBlockSize().");
		}
		iv_ = iv;
	}

	/**
	 * Get the data for key derivation.
	 *
	 * @return The data.
	 * @preconditions
	 * @postconditions (result <> null) and (result.length%getBlockSize() == 0)
	 */
	public byte[] getData() {
		return data_;
	}

	/**
	 * Set the key derivation data.
	 *
	 * @param data The key derivation data.
	 * @preconditions (data <> null) and (data.length%getBlockSize() == 0)
	 * @postconditions
	 */
	public void setData(byte[] data) {
		if (data == null) {
			throw new NullPointerException("Argument \"data\" must not be null.");
		}
		if (data.length % blockSize_ != 0) {
			throw new IllegalArgumentException(
			    "Argument \"data\" must have a length that is a multiple of getBlockSize().");
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
		buffer.append("Initialization Vector (hex): ");
		buffer.append(Functions.toHexString(iv_));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Data (hex): ");
		buffer.append(Functions.toHexString(data_));
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

		if (otherObject instanceof CbcEncryptDataParameters) {
			CbcEncryptDataParameters other = (CbcEncryptDataParameters) otherObject;
			equal = (this == other)
			    || ((this.blockSize_ == other.blockSize_)
			        && Functions.equals(this.iv_, other.iv_) && Functions.equals(this.data_,
			        other.data_));
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
		return Functions.hashCode(iv_) ^ Functions.hashCode(data_);
	}

}
