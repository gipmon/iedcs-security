package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.wrapper.CK_DES_CBC_ENCRYPT_DATA_PARAMS;

/**
 * This class encapsulates parameters for the algorithms
 * Mechanism.DES_CBC_ENCRYPT_DATA and Mechanism.DES3_CBC_ENCRYPT_DATA.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class DesCbcEncryptDataParameters extends CbcEncryptDataParameters {

	/**
	 * Create a new DesCbcEncryptDataParameters object with the given IV and
	 * data.
	 *
	 * @param iv The initialization vector.
	 * @param data The key derivation data.
	 * @preconditions (iv <> null) (iv.length == 8)
	 *                and (data <> null) and (data.length%8 == 0)
	 * @postconditions
	 */
	public DesCbcEncryptDataParameters(byte[] iv, byte[] data) {
		super(8, iv, data);
	}

	/**
	 * Get this parameters object as Long object.
	 *
	 * @return This object as Long object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_DES_CBC_ENCRYPT_DATA_PARAMS params = new CK_DES_CBC_ENCRYPT_DATA_PARAMS();

		params.iv = iv_;
		params.pData = data_;

		return params;
	}

}
