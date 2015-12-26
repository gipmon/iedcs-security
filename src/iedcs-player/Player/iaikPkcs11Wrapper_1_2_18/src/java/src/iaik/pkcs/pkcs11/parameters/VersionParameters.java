package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.Version;
import iaik.pkcs.pkcs11.wrapper.CK_VERSION;

/**
 * This class is used for the Mechnism.SSL3_PRE_MASTER_KEY_GEN.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class VersionParameters extends Version implements Parameters {

	/**
	 * Create a new VersionParameters object with the major and minor
	 * version set to zero.
	 *
	 * @preconditions
	 * @postconditions
	 */
	public VersionParameters() {
		super();
	}

	/**
	 * Create a new VersionParameters object with the given major and minor
	 * version.
	 *
	 * @param major The major version number.
	 * @param minor The minor version number.
	 * @preconditions
	 * @postconditions
	 */
	public VersionParameters(byte major, byte minor) {
		super();
		major_ = major;
		minor_ = minor;
	}

	/**
	 * Get this parameters object as a CK_VERSION object.
	 *
	 * @return This object as a CK_VERSION object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_VERSION params = new CK_VERSION();

		params.major = major_;
		params.minor = minor_;

		return params;
	}

	/**
	 * This method allows setting the major and minor version numbers using
	 * a version object of the lower level API.
	 *
	 * @param input The version objet providing the major and minor version.
	 * @preconditions (input <> null)
	 * @postconditions
	 */
	public void setPKCS11ParamsObject(CK_VERSION input) {
		major_ = input.major;
		minor_ = input.minor;
	}

	/**
	 * Set the major version number.
	 *
	 * @param major The major version number.
	 * @preconditions
	 * @postconditions
	 */
	public void setMajor(byte major) {
		major_ = major;
	}

	/**
	 * Set the minor version number.
	 *
	 * @param minor The minor version number.
	 * @preconditions
	 * @postconditions
	 */
	public void setMinor(byte minor) {
		minor_ = minor;
	}

}
