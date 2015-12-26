package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.wrapper.CK_ECDH1_DERIVE_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This abstract class encapsulates parameters for the DH mechanisms
 * Mechanism.ECDH1_DERIVE and Mechanism.ECDH1_COFACTOR_DERIVE.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class EcDH1KeyDerivationParameters extends DHKeyDerivationParameters {

	/**
	 * The data shared between the two parties.
	 */
	protected byte[] sharedData_;

	/**
	 * Create a new EcDH1KeyDerivationParameters object with the given attributes.
	 *
	 * @param keyDerivationFunction The key derivation function used on the shared
	 *                              secret value.
	 *                              One of the values defined in
	 *                              KeyDerivationFunctionType.
	 * @param sharedData The data shared between the two parties.
	 * @param publicData The other partie's public key value.
	 * @preconditions ((keyDerivationFunction == KeyDerivationFunctionType.NULL)
	 *                 or (keyDerivationFunction == KeyDerivationFunctionType.SHA1_KDF)
	 *                 or (keyDerivationFunction == KeyDerivationFunctionType.SHA1_KDF_ASN1)
	 *                 or (keyDerivationFunction == KeyDerivationFunctionType.SHA1_KDF_CONCATENATE))
	 *                and (publicData <> null)
	 * @postconditions
	 */
	public EcDH1KeyDerivationParameters(long keyDerivationFunction,
	                                    byte[] sharedData,
	                                    byte[] publicData)
	{
		super(keyDerivationFunction, publicData);
		sharedData_ = sharedData;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof EcDH1KeyDerivationParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		EcDH1KeyDerivationParameters clone = (EcDH1KeyDerivationParameters) super.clone();

		clone.sharedData_ = (byte[]) this.sharedData_.clone();

		return clone;
	}

	/**
	 * Get this parameters object as an object of the CK_ECDH1_DERIVE_PARAMS
	 * class.
	 *
	 * @return This object as a CK_ECDH1_DERIVE_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_ECDH1_DERIVE_PARAMS params = new CK_ECDH1_DERIVE_PARAMS();

		params.kdf = keyDerivationFunction_;
		params.pSharedData = sharedData_;
		params.pPublicData = publicData_;

		return params;
	}

	/**
	 * Get the data shared between the two parties.
	 *
	 * @return The data shared between the two parties.
	 * @preconditions
	 * @postconditions
	 */
	public byte[] getSharedData() {
		return sharedData_;
	}

	/**
	 * Set the data shared between the two parties.
	 *
	 * @param sharedData The data shared between the two parties.
	 * @preconditions (sharedData <> null)
	 * @postconditions
	 */
	public void setSharedData(byte[] sharedData) {
		sharedData_ = sharedData;
	}

	/**
	 * Returns the string representation of this object. Do not parse data from
	 * this string, it is for debugging only.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(super.toString());
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Shared Data: ");
		buffer.append(Functions.toHexString(sharedData_));
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

		if (otherObject instanceof EcDH1KeyDerivationParameters) {
			EcDH1KeyDerivationParameters other = (EcDH1KeyDerivationParameters) otherObject;
			equal = (this == other)
			    || (super.equals(other) && Functions
			        .equals(this.sharedData_, other.sharedData_));
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
		return super.hashCode() ^ Functions.hashCode(sharedData_);
	}

}
