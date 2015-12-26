package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.CK_PKCS5_PBKD2_PARAMS;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

/**
 * This class encapsulates parameters for the Mechanism.PKCS5_PKKD2 mechanism.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (saltSource_ == SaltSourceType.SaltSpecified)
 *           and (saltSourceData_ <> null)
 *           and (pseudoRandomFunction_ == PseudoRandomFunctionType.HMACSha1)
 *           and (pseudoRandomFunctionData_ <> null)
 */
public class PKCS5PBKD2Parameters implements Parameters {

	/**
	 * This interface defines the available pseudo-random function types as
	 * defined by PKCS#11: CKP_PKCS5_PBKD2_HMAC_SHA1.
	 *
	 * @author Karl Scheibelhofer
	 * @version 1.0
	 * @invariants
	 */
	public interface PseudoRandomFunctionType {

		/**
		 * The indentifier for HMAC Sha-1 version.
		 */
		static public final long HMAC_SHA1 = PKCS11Constants.CKP_PKCS5_PBKD2_HMAC_SHA1;

	}

	/**
	 * This interface defines the available sources of the salt value as
	 * defined by PKCS#11: CKZ_SALT_SPECIFIED.
	 *
	 * @author Karl Scheibelhofer
	 * @version 1.0
	 * @invariants
	 */
	public interface SaltSourceType {

		/**
		 * The indentifier for specified salt.
		 */
		static public final long SALT_SPECIFIED = PKCS11Constants.CKZ_SALT_SPECIFIED;

	}

	/**
	 * The source of the salt value.
	 */
	protected long saltSource_;

	/**
	 * The data used as the input for the salt source.
	 */
	protected byte[] saltSourceData_;

	/**
	 * The number of iterations to perform when generating each block of random
	 * data.
	 */
	protected long iterations_;

	/**
	 * The pseudo-random function (PRF) to used to generate the key.
	 */
	protected long pseudoRandomFunction_;

	/**
	 * The data used as the input for PRF in addition to the salt value.
	 */
	protected byte[] pseudoRandomFunctionData_;

	/**
	 * Create a new PBEDeriveParameters object with the given attributes.
	 *
	 * @param saltSource The source of the salt value. One of the constants
	 *                   defined in the SaltSourceType interface.
	 * @param saltSourceData The data used as the input for the salt source.
	 * @param iterations The number of iterations to perform when generating each
	 *                   block of random data.
	 * @param pseudoRandomFunction The pseudo-random function (PRF) to used to
	 *                             generate the key. One of the constants defined
	 *                             in the PseudoRandomFunctionType interface.
	 * @param pseudoRandomFunctionData The data used as the input for PRF in
	 *                                 addition to the salt value.
	 * @preconditions (saltSource == SaltSourceType.SaltSpecified)
	 *                and (saltSourceData <> null)
	 *                and (pseudoRandomFunction == PseudoRandomFunctionType.HMACSha1)
	 *                and (pseudoRandomFunctionData <> null)
	 * @postconditions
	 */
	public PKCS5PBKD2Parameters(long saltSource,
	                            byte[] saltSourceData,
	                            long iterations,
	                            long pseudoRandomFunction,
	                            byte[] pseudoRandomFunctionData)
	{
		if (saltSource != SaltSourceType.SALT_SPECIFIED) {
			throw new IllegalArgumentException("Illegal value for argument\"saltSource\": "
			    + Functions.toHexString(saltSource));
		}
		if (saltSourceData == null) {
			throw new NullPointerException("Argument \"saltSourceData\" must not be null.");
		}
		if (pseudoRandomFunction != PseudoRandomFunctionType.HMAC_SHA1) {
			throw new IllegalArgumentException(
			    "Illegal value for argument\"pseudoRandomFunction\": "
			        + Functions.toHexString(pseudoRandomFunction));
		}
		if (pseudoRandomFunctionData == null) {
			throw new NullPointerException(
			    "Argument \"pseudoRandomFunctionData\" must not be null.");
		}
		saltSource_ = saltSource;
		saltSourceData_ = saltSourceData;
		iterations_ = iterations;
		pseudoRandomFunction_ = pseudoRandomFunction;
		pseudoRandomFunctionData_ = pseudoRandomFunctionData;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof PKCS5PBKD2Parameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		PKCS5PBKD2Parameters clone;

		try {
			clone = (PKCS5PBKD2Parameters) super.clone();

			clone.saltSourceData_ = (byte[]) this.saltSourceData_.clone();
			clone.pseudoRandomFunctionData_ = (byte[]) this.pseudoRandomFunctionData_.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as an object of the CK_PKCS5_PBKD2_PARAMS
	 * class.
	 *
	 * @return This object as a CK_PKCS5_PBKD2_PARAMS object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_PKCS5_PBKD2_PARAMS params = new CK_PKCS5_PBKD2_PARAMS();

		params.saltSource = saltSource_;
		params.pSaltSourceData = saltSourceData_;
		params.iterations = iterations_;
		params.prf = pseudoRandomFunction_;
		params.pPrfData = pseudoRandomFunctionData_;

		return params;
	}

	/**
	 * Get the source of the salt value.
	 *
	 * @return The source of the salt value.
	 * @preconditions
	 * @postconditions (result == SaltSourceType.SaltSpecified)
	 */
	public long getSaltSource() {
		return saltSource_;
	}

	/**
	 * Get the data used as the input for the salt source.
	 *
	 * @return data used as the input for the salt source.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public byte[] getSaltSourceData() {
		return saltSourceData_;
	}

	/**
	 * Get the number of iterations to perform when generating each block of
	 * random data.
	 *
	 * @return The number of iterations to perform when generating each block of
	 *         random data.
	 * @preconditions
	 * @postconditions
	 */
	public long getIterations() {
		return iterations_;
	}

	/**
	 * Get the pseudo-random function (PRF) to used to generate the key.
	 *
	 * @return The pseudo-random function (PRF) to used to generate the key.
	 * @preconditions
	 * @postconditions (result == PseudoRandomFunctionType.HMACSha1)
	 */
	public long getPseudoRandomFunction() {
		return pseudoRandomFunction_;
	}

	/**
	 * Get the data used as the input for PRF in addition to the salt value.
	 *
	 * @return The data used as the input for PRF in addition to the salt value.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public byte[] getPseudoRandomFunctionData() {
		return pseudoRandomFunctionData_;
	}

	/**
	 * Set the source of the salt value.
	 *
	 * @param saltSource The source of the salt value. One of the constants
	 *                   defined in the SaltSourceType interface
	 * @preconditions (saltSource == SaltSourceType.SaltSpecified)
	 * @postconditions
	 */
	public void setSaltSource(long saltSource) {
		if (saltSource != SaltSourceType.SALT_SPECIFIED) {
			throw new IllegalArgumentException("Illegal value for argument\"saltSource\": "
			    + Functions.toHexString(saltSource));
		}
		saltSource_ = saltSource;
	}

	/**
	 * Set the data used as the input for the salt source.
	 *
	 * @param saltSourceData The data used as the input for the salt source.
	 * @preconditions (saltSourceData <> null)
	 * @postconditions
	 */
	public void setSaltSourceData(byte[] saltSourceData) {
		if (saltSourceData == null) {
			throw new NullPointerException("Argument \"saltSourceData\" must not be null.");
		}
		saltSourceData_ = saltSourceData;
	}

	/**
	 * Set the number of iterations to perform when generating each block of
	 * random data.
	 *
	 * @param iterations The number of iterations to perform when generating each
	 *                   block of random data.
	 * @preconditions
	 * @postconditions
	 */
	public void setIterations(long iterations) {
		iterations_ = iterations;
	}

	/**
	 * Set the pseudo-random function (PRF) to used to generate the key.
	 *
	 * @param pseudoRandomFunction The pseudo-random function (PRF) to used to
	 *                             generate the key. One of the constants defined
	 *                             in the PseudoRandomFunctionType interface.
	 * @preconditions (pseudoRandomFunction == PseudoRandomFunctionType.HMACSha1)
	 * @postconditions
	 */
	public void setPseudoRandomFunction(long pseudoRandomFunction) {
		if (pseudoRandomFunction != PseudoRandomFunctionType.HMAC_SHA1) {
			throw new IllegalArgumentException(
			    "Illegal value for argument\"pseudoRandomFunction\": "
			        + Functions.toHexString(pseudoRandomFunction));
		}
		pseudoRandomFunction_ = pseudoRandomFunction;
	}

	/**
	 * Set the data used as the input for PRF in addition to the salt value.
	 *
	 * @param pseudoRandomFunctionData The data used as the input for PRF in
	 *                                 addition to the salt value.
	 * @preconditions (pseudoRandomFunctionData <> null)
	 * @postconditions
	 */
	public void setPseudoRandomFunctionData(byte[] pseudoRandomFunctionData) {
		if (pseudoRandomFunctionData == null) {
			throw new NullPointerException(
			    "Argument \"pseudoRandomFunctionData\" must not be null.");
		}
		pseudoRandomFunctionData_ = pseudoRandomFunctionData;
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
		buffer.append("Salt Source: ");
		if (saltSource_ == SaltSourceType.SALT_SPECIFIED) {
			buffer.append("Salt Specified");
		} else {
			buffer.append("<unknown>");
		}
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Salt Source Data (hex): ");
		buffer.append(Functions.toHexString(saltSourceData_));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Iterations (dec): ");
		buffer.append(iterations_);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Pseudo-Random Function: ");
		if (pseudoRandomFunction_ == PseudoRandomFunctionType.HMAC_SHA1) {
			buffer.append("HMAC SHA-1");
		} else {
			buffer.append("<unknown>");
		}
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Pseudo-Random Function Data (hex): ");
		buffer.append(Functions.toHexString(pseudoRandomFunctionData_));
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

		if (otherObject instanceof PKCS5PBKD2Parameters) {
			PKCS5PBKD2Parameters other = (PKCS5PBKD2Parameters) otherObject;
			equal = (this == other)
			    || ((this.saltSource_ == other.saltSource_)
			        && Functions.equals(this.saltSourceData_, other.saltSourceData_)
			        && (this.iterations_ == other.iterations_)
			        && (this.pseudoRandomFunction_ == other.pseudoRandomFunction_) && Functions
			          .equals(this.pseudoRandomFunctionData_, other.pseudoRandomFunctionData_));
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
		return ((int) saltSource_) ^ Functions.hashCode(saltSourceData_)
		    ^ ((int) iterations_) ^ ((int) pseudoRandomFunction_)
		    ^ Functions.hashCode(pseudoRandomFunctionData_);
	}

}
