package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.wrapper.CK_AES_CBC_ENCRYPT_DATA_PARAMS;

/**
 * This class encapsulates parameters for the algorithm
 * Mechanism.AES_CBC_ENCRYPT_DATA.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class AesCbcEncryptDataParameters extends CbcEncryptDataParameters {

	/**
	 * Create a new AesCbcEncryptDataParameters object with the given IV and
	 * data.
	 *
	 * @param iv The initialization vector.
	 * @param data The key derivation data.
	 * @preconditions (iv <> null) (iv.length == 16)
	 *                and (data <> null) and (data.length%16 == 0)
	 * @postconditions
	 */
	public AesCbcEncryptDataParameters(byte[] iv, byte[] data) {
		super(16, iv, data);
	}

	/**
	 * Get this parameters object as Long object.
	 *
	 * @return This object as Long object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		CK_AES_CBC_ENCRYPT_DATA_PARAMS params = new CK_AES_CBC_ENCRYPT_DATA_PARAMS();

		params.iv = iv_;
		params.pData = data_;

		return params;
	}

}
