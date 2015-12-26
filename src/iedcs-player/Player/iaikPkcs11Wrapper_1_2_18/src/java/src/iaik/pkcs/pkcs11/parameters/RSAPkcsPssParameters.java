package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.wrapper.CK_RSA_PKCS_PSS_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for the Mechanism.RSA_PKCS_PSS.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class RSAPkcsPssParameters extends RSAPkcsParameters {

	/**
	 * The length of the salt value in octets.
	 */
	protected long saltLength_;

	/**
	 * Create a new RSAPkcsOaepParameters object with the given attributes.
	 *
	 * @param hashAlgorithm The message digest algorithm used to calculate the
	 *                      digest of the encoding parameter.
	 * @param maskGenerationFunction The mask to apply to the encoded block. One
	 *                               of the constants defined in the
	 *                               MessageGenerationFunctionType interface.
	 * @param saltLength The length of the salt value in octets.
	 * @preconditions (hashAlgorithm <> null)
	 *                and (maskGenerationFunction == MessageGenerationFunctionType.Sha1)
	 * @postconditions
	 */
	public RSAPkcsPssParameters(Mechanism hashAlgorithm,
	                            long maskGenerationFunction,
	                            long saltLength)
	{
		super(hashAlgorithm, maskGenerationFunction);
		saltLength_ = saltLength;
	}

	/**
	 * Get this parameters object as an object of the CK_RSA_PKCS_PSS_PARAMS
	 * class.
	 *
	 * @return This object as a CK_RSA_PKCS_PSS_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_RSA_PKCS_PSS_PARAMS params = new CK_RSA_PKCS_PSS_PARAMS();

		params.hashAlg = hashAlgorithm_.getMechanismCode();
		params.mgf = maskGenerationFunction_;
		params.sLen = saltLength_;

		return params;
	}

	/**
	 * Get the length of the salt value in octets.
	 *
	 * @return The length of the salt value in octets.
	 * @preconditions
	 * @postconditions
	 */
	public long getSaltLength() {
		return saltLength_;
	}

	/**
	 * Set the length of the salt value in octets.
	 *
	 * @param saltLength The length of the salt value in octets.
	 * @preconditions
	 * @postconditions
	 */
	public void setSaltLength(long saltLength) {
		saltLength_ = saltLength;
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
		buffer.append("Salt Length (octets, dec): ");
		buffer.append(saltLength_);
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

		if (otherObject instanceof RSAPkcsPssParameters) {
			RSAPkcsPssParameters other = (RSAPkcsPssParameters) otherObject;
			equal = (this == other)
			    || (super.equals(other) && (this.saltLength_ == other.saltLength_));
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
		return super.hashCode() ^ ((int) saltLength_);
	}

}
