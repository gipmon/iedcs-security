package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.CK_SSL3_RANDOM_DATA;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters for the Mechanism.SSL3_MASTER_KEY_DERIVE
 * and Mechanism.SSL3_KEY_AND_MAC_DERIVE mechanisms.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (clientRandom_ <> null)
 *             and (serverRandom_ <> null)
 */
public class SSL3RandomDataParameters implements Parameters {

	/**
	 * The client's random data.
	 */
	protected byte[] clientRandom_;

	/**
	 * The server's random data.
	 */
	protected byte[] serverRandom_;

	/**
	 * Create a new SSL3RandomDataParameters object with the given
	 * cleint and server random.
	 *
	 * @param clientRandom The client's random data.
	 * @param serverRandom The server's random data.
	 * @preconditions (clientRandom <> null)
	 *                and (serverRandom <> null)
	 * @postconditions
	 */
	public SSL3RandomDataParameters(byte[] clientRandom, byte[] serverRandom) {
		if (clientRandom == null) {
			throw new NullPointerException("Argument \"clientRandom\" must not be null.");
		}
		if (serverRandom == null) {
			throw new NullPointerException("Argument \"serverRandom\" must not be null.");
		}
		clientRandom_ = clientRandom;
		serverRandom_ = serverRandom;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof SSL3RandomDataParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		SSL3RandomDataParameters clone;

		try {
			clone = (SSL3RandomDataParameters) super.clone();

			clone.clientRandom_ = (byte[]) this.clientRandom_.clone();
			clone.serverRandom_ = (byte[]) this.serverRandom_.clone();
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
		CK_SSL3_RANDOM_DATA params = new CK_SSL3_RANDOM_DATA();

		params.pClientRandom = clientRandom_;
		params.pServerRandom = serverRandom_;

		return params;
	}

	/**
	 * Get the client's random data.
	 *
	 * @return The client's random data.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public byte[] getClientRandom() {
		return clientRandom_;
	}

	/**
	 * Get the server's random data.
	 *
	 * @return The server's random data.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public byte[] getServerRandom() {
		return serverRandom_;
	}

	/**
	 * Set the client's random data.
	 *
	 * @param clientRandom The client's random data.
	 * @preconditions (clientRandom <> null)
	 * @postconditions
	 */
	public void setClientRandom(byte[] clientRandom) {
		if (clientRandom == null) {
			throw new NullPointerException("Argument \"clientRandom\" must not be null.");
		}
		clientRandom_ = clientRandom;
	}

	/**
	 * Set the server's random data.
	 *
	 * @param serverRandom The server's random data.
	 * @preconditions (serverRandom <> null)
	 * @postconditions
	 */
	public void setServerRandom(byte[] serverRandom) {
		if (serverRandom == null) {
			throw new NullPointerException("Argument \"serverRandom\" must not be null.");
		}
		serverRandom_ = serverRandom;
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
		buffer.append("Client Random (hex): ");
		buffer.append(Functions.toHexString(clientRandom_));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("Server Random (hex): ");
		buffer.append(Functions.toHexString(serverRandom_));
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

		if (otherObject instanceof SSL3RandomDataParameters) {
			SSL3RandomDataParameters other = (SSL3RandomDataParameters) otherObject;
			equal = (this == other)
			    || (Functions.equals(this.clientRandom_, other.clientRandom_) && Functions
			        .equals(this.serverRandom_, other.serverRandom_));
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
		return Functions.hashCode(clientRandom_) ^ Functions.hashCode(serverRandom_);
	}

}
