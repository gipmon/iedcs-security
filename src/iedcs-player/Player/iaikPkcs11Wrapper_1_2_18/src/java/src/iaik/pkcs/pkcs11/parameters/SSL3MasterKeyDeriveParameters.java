package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.CK_SSL3_MASTER_KEY_DERIVE_PARAMS;
import iaik.pkcs.pkcs11.wrapper.CK_SSL3_RANDOM_DATA;
import iaik.pkcs.pkcs11.wrapper.CK_VERSION;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for the Mechanism.SSL3_MASTER_KEY_DERIVE
 * mechanism and the Mechanism.TLS_MASTER_KEY_DERIVE.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (randomInfo_ <> null)
 *             and (version_ <> null)
 */
public class SSL3MasterKeyDeriveParameters implements Parameters {

	/**
	 * The client's and server's random data information.
	 */
	protected SSL3RandomDataParameters randomInfo_;

	/**
	 * The SSL protocol version information.
	 */
	protected VersionParameters version_;

	/**
	 * Create a new SSL3MasterKeyDeriveParameters object with the given
	 * random info and version.
	 *
	 * @param randomInfo The client's and server's random data information.
	 * @param version The SSL protocol version information.
	 * @preconditions (randomInfo <> null)
	 *                and (version <> null)
	 * @postconditions
	 */
	public SSL3MasterKeyDeriveParameters(SSL3RandomDataParameters randomInfo,
	                                     VersionParameters version)
	{
		if (randomInfo == null) {
			throw new NullPointerException("Argument \"randomInfo\" must not be null.");
		}
		if (version == null) {
			throw new NullPointerException("Argument \"version\" must not be null.");
		}
		randomInfo_ = randomInfo;
		version_ = version;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof SSL3MasterKeyDeriveParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		SSL3MasterKeyDeriveParameters clone;

		try {
			clone = (SSL3MasterKeyDeriveParameters) super.clone();

			clone.randomInfo_ = (SSL3RandomDataParameters) this.randomInfo_.clone();
			clone.version_ = (VersionParameters) this.version_.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as a CK_SSL3_RANDOM_DATA object.
	 *
	 * @return This object as a CK_SSL3_RANDOM_DATA object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_SSL3_MASTER_KEY_DERIVE_PARAMS params = new CK_SSL3_MASTER_KEY_DERIVE_PARAMS();

		params.RandomInfo = (CK_SSL3_RANDOM_DATA) randomInfo_.getPKCS11ParamsObject();
		params.pVersion = (CK_VERSION) version_.getPKCS11ParamsObject();

		return params;
	}

	/**
	 * Get the client's and server's random data information.
	 *
	 * @return The client's and server's random data information.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public SSL3RandomDataParameters getRandomInfo() {
		return randomInfo_;
	}

	/**
	 * Get the SSL protocol version information.
	 *
	 * @return The SSL protocol version information.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public VersionParameters getVersion() {
		return version_;
	}

	/**
	 * Set the client's and server's random data information.
	 *
	 * @param randomInfo The client's and server's random data information.
	 * @preconditions (randomInfo <> null)
	 * @postconditions
	 */
	public void setRandomInfo(SSL3RandomDataParameters randomInfo) {
		if (randomInfo == null) {
			throw new NullPointerException("Argument \"randomInfo\" must not be null.");
		}
		randomInfo_ = randomInfo;
	}

	/**
	 * Set the SSL protocol version information.
	 *
	 * @param version The SSL protocol version information.
	 * @preconditions (version <> null)
	 * @postconditions
	 */
	public void setVersion(VersionParameters version) {
		if (version == null) {
			throw new NullPointerException("Argument \"version\" must not be null.");
		}
		version_ = version;
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
		buffer.append("Random Information:");
		buffer.append(Constants.NEWLINE);
		buffer.append(randomInfo_);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Version: ");
		buffer.append(version_);
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

		if (otherObject instanceof SSL3MasterKeyDeriveParameters) {
			SSL3MasterKeyDeriveParameters other = (SSL3MasterKeyDeriveParameters) otherObject;
			equal = (this == other)
			    || (this.randomInfo_.equals(other.randomInfo_) && this.version_
			        .equals(other.version_));
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
		return randomInfo_.hashCode() ^ version_.hashCode();
	}

}
