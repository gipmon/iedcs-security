package iaik.pkcs.pkcs11;

import iaik.pkcs.pkcs11.wrapper.CK_INFO;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * Objects of this class provide information about a PKCS#11 moduel; i.e. the
 * driver for a spcific token.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants (cryptokiVersion_ <> null)
 *             and (manufacturerID_ <> null)
 *             and (libraryDescription_ <> null)
 *             and (libraryVersion_ <> null)
 */
public class Info implements Cloneable {

	/**
	 * The module claims to be compliant to this version of PKCS#11.
	 */
	protected Version cryptokiVersion_;

	/**
	 * The identifer for the manufacturer of this module.
	 */
	protected String manufacturerID_;

	/**
	 * A description of this module.
	 */
	protected String libraryDescription_;

	/**
	 * The version number of this module.
	 */
	protected Version libraryVersion_;

	/**
	 * Constructor taking the CK_INFO object of the token.
	 *
	 * @param ckInfo The info object as got from PKCS11.C_GetInfo().
	 * @preconditions (ckInfo <> null)
	 * @postconditions
	 */
	protected Info(CK_INFO ckInfo) {
		if (ckInfo == null) {
			throw new NullPointerException("Argument \"ckInfo\" must not be null.");
		}
		cryptokiVersion_ = new Version(ckInfo.cryptokiVersion);
		manufacturerID_ = new String(ckInfo.manufacturerID);
		libraryDescription_ = new String(ckInfo.libraryDescription);
		libraryVersion_ = new Version(ckInfo.libraryVersion);
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof Info)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		Info clone;

		try {
			clone = (Info) super.clone();

			clone.cryptokiVersion_ = (Version) this.cryptokiVersion_.clone();
			clone.libraryVersion_ = (Version) this.libraryVersion_.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get the version of PKCS#11 that this module claims to be compliant to.
	 *
	 * @return The version object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Version getCryptokiVersion() {
		return cryptokiVersion_;
	}

	/**
	 * Get the identifier of the manufacturer.
	 *
	 * @return A string identifying the manufacturer of this module.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public String getManufacturerID() {
		return manufacturerID_;
	}

	/**
	 * Get a short descrption of this module.
	 *
	 * @return A string describing the module.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public String getLibraryDescription() {
		return libraryDescription_;
	}

	/**
	 * Get the version of this PKCS#11 module.
	 *
	 * @return The version of this module.
	 * @preconditions
	 * @postconditions
	 */
	public Version getLibraryVersion() {
		return libraryVersion_;
	}

	/**
	 * Returns the string representation of this object.
	 *
	 * @return the string representation of object
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Cryptoki Version: ");
		buffer.append(cryptokiVersion_);
		buffer.append(Constants.NEWLINE);

		buffer.append("ManufacturerID: ");
		buffer.append(manufacturerID_);
		buffer.append(Constants.NEWLINE);

		buffer.append("Library Description: ");
		buffer.append(libraryDescription_);
		buffer.append(Constants.NEWLINE);

		buffer.append("Library Version: ");
		buffer.append(libraryVersion_);

		return buffer.toString();
	}

	/**
	 * Compares all member variables of this object with the other object.
	 * Returns only true, if all are equal in both objects.
	 *
	 * @param otherObject The other Info object.
	 * @return True, if other is an instance of Info and all member variables of
	 *         both objects are equal. False, otherwise.
	 * @preconditions
	 * @postconditions
	 */
	public boolean equals(java.lang.Object otherObject) {
		boolean equal = false;

		if (otherObject instanceof Info) {
			Info other = (Info) otherObject;
			equal = (this == other)
			    || (this.cryptokiVersion_.equals(other.cryptokiVersion_)
			        && this.manufacturerID_.equals(other.manufacturerID_)
			        && this.libraryDescription_.equals(other.libraryDescription_) && this.libraryVersion_
			          .equals(other.libraryVersion_));
		}

		return equal;
	}

	/**
	 * The overriding of this method should ensure that the objects of this class
	 * work correctly in a hashtable.
	 *
	 * @return The hash code of this object. Gained from all member variables.
	 * @preconditions
	 * @postconditions
	 */
	public int hashCode() {
		return cryptokiVersion_.hashCode() ^ manufacturerID_.hashCode()
		    ^ libraryDescription_.hashCode() ^ libraryVersion_.hashCode();
	}

}
