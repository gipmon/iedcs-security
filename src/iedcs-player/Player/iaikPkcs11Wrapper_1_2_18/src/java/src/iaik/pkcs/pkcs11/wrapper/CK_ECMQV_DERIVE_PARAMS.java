package iaik.pkcs.pkcs11.wrapper;

/**
 * class CK_ECMQV_DERIVE_PARAMS provides the parameters to the
 * CKM_ECMQV_DERIVE mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_ECMQV_DERIVE_PARAMS {
 *   CK_EC_KDF_TYPE kdf;
 *   CK_ULONG ulSharedDataLen;
 *   CK_BYTE_PTR pSharedData;
 *   CK_ULONG ulPublicDataLen;
 *   CK_BYTE_PTR pPublicData;
 *   CK_ULONG ulPrivateDataLen;
 *   CK_OBJECT_HANDLE hPrivateData;
 *   CK_ULONG ulPublicDataLen2;
 *   CK_BYTE_PTR pPublicData2;
 *   CK_OBJECT_HANDLE publicKey;
 * } CK_ECMQV_DERIVE_PARAMS;
 * </PRE>
 */
public class CK_ECMQV_DERIVE_PARAMS {

	/**
	 * key derivation function used on the shared secret value
	 * <PRE>
	 *   CK_EC_KDF_TYPE kdf;
	 * </PRE>
	 */
	public long kdf;

	/**
	 * some data shared between the two parties
	 * <PRE>
	 *   CK_BYTE_PTR pSharedData;
	 * </PRE>
	 */
	public byte[] pSharedData;

	/**
	 * pointer to other partyâs first EC public key value
	 * <PRE>
	 *   CK_ULONG ulPublicDataLen;
	 *   CK_BYTE_PTR pPublicData;
	 * </PRE>
	 */
	public byte[] pPublicData;

	/**
	 * the length in bytes of the second EC private key
	 * <PRE>
	 *   CK_ULONG ulPrivateDataLen;
	 * </PRE>
	 */
	public long ulPrivateDataLen;

	/**
	 * key handle for second EC private key value
	 * <PRE>
	 *   CK_OBJECT_HANDLE hPrivateData;
	 * </PRE>
	 */
	public long hPrivateData;

	/**
	 * pointer to other partyâs second EC public key value
	 * <PRE>
	 *   CK_ULONG ulPublicDataLen2;
	 *   CK_BYTE_PTR pPublicData2;
	 * </PRE>
	 */
	public byte[] pPublicData2;

	/**
	 * Handle to the first partyâs ephemeral public key
	 * <PRE>
	 *   CK_OBJECT_HANDLE publicKey;
	 * </PRE>
	 */
	public long publicKey;

	/**
	 * Returns the string representation of CK_ECMQV_DERIVE_PARAMS.
	 *
	 * @return the string representation of CK_ECMQV_DERIVE_PARAMS
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("kdf: 0x");
		buffer.append(Functions.toFullHexString(kdf));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pSharedDataLen: ");
		buffer.append(pSharedData.length);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pSharedData: ");
		buffer.append(Functions.toHexString(pSharedData));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pPublicDataLen: ");
		buffer.append(pPublicData.length);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pPublicData: ");
		buffer.append(Functions.toHexString(pPublicData));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pPrivateDataLen: ");
		buffer.append(ulPrivateDataLen);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("hPrivateData: ");
		buffer.append(hPrivateData);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pPublicDataLen2: ");
		buffer.append(pPublicData2.length);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pPublicDat2a: ");
		buffer.append(Functions.toHexString(pPublicData2));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("publicKey: ");
		buffer.append(publicKey);
		//    buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
