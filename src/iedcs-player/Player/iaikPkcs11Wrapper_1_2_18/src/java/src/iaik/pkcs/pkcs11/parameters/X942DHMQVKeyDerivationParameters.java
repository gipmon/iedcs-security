package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.wrapper.CK_X9_42_DHMQV_DERIVE_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This abstract class encapsulates parameters for the X9.42 DH mechanisms
 * Mechanism.X9_42_DH_HYBRID_DERIVE and Mechanism.X9_42_MQV_DERIVE.
 */
public class X942DHMQVKeyDerivationParameters extends X942DH2KeyDerivationParameters {

	private Object publicKey_;

	public X942DHMQVKeyDerivationParameters(long keyDerivationFunction,
	                                        byte[] sharedData,
	                                        byte[] publicData,
	                                        long privateDataLength,
	                                        iaik.pkcs.pkcs11.objects.Object privateData,
	                                        byte[] publicData2,
	                                        iaik.pkcs.pkcs11.objects.Object publicKey)
	{
		super(keyDerivationFunction, sharedData, publicData, privateDataLength, privateData,
		    publicData2);

		publicKey_ = publicKey;
	}

	/*
	 * (non-Javadoc)
	 * @see iaik.pkcs.pkcs11.parameters.X942DH2KeyDerivationParameters#clone()
	 */
	public java.lang.Object clone() {
		X942DHMQVKeyDerivationParameters clone = (X942DHMQVKeyDerivationParameters) super
		    .clone();

		clone.publicKey_ = (iaik.pkcs.pkcs11.objects.Object) this.publicKey_.clone();

		return clone;
	}

	/**
	 * Get this parameters object as an object of the CK_X9_42_DH2_DERIVE_PARAMS
	 * class.
	 * 
	 * @return This object as a CK_X9_42_DH2_DERIVE_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public java.lang.Object getPKCS11ParamsObject() {
		CK_X9_42_DHMQV_DERIVE_PARAMS params = new CK_X9_42_DHMQV_DERIVE_PARAMS();

		params.kdf = keyDerivationFunction_;
		params.pOtherInfo = otherInfo_;
		params.pPublicData = publicData_;
		params.ulPrivateDataLen = privateDataLength_;
		params.hPrivateData = privateData_.getObjectHandle();
		params.pPublicData2 = publicData2_;
		params.hPublicKey = publicKey_.getObjectHandle();

		return params;
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
		buffer.append("Private Data Length (dec): ");
		buffer.append(privateDataLength_);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Private Data: ");
		buffer.append(privateData_);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Public Data 2: ");
		buffer.append(Functions.toHexString(publicData2_));
		// buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

	/**
	 * Compares all member variables of this object with the other object. Returns
	 * only true, if all are equal in both objects.
	 * 
	 * @param otherObject
	 *          The other object to compare to.
	 * @return True, if other is an instance of this class and all member
	 *         variables of both objects are equal. False, otherwise.
	 * @preconditions
	 * @postconditions
	 */
	public boolean equals(java.lang.Object otherObject) {
		boolean equal = false;

		if (otherObject instanceof X942DHMQVKeyDerivationParameters) {
			X942DHMQVKeyDerivationParameters other = (X942DHMQVKeyDerivationParameters) otherObject;
			equal = (this == other)
			    || (super.equals(other) && (this.publicKey_.equals(other.publicKey_)));
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
		return super.hashCode() ^ publicKey_.hashCode();
	}

}
