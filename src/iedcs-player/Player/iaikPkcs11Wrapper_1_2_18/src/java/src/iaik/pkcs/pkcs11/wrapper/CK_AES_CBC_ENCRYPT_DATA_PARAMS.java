package iaik.pkcs.pkcs11.wrapper;

/**
 * The class CK_AES_CBC_ENCRYPT_DATA_PARAMS provides the parameters to the 
 * CKM_AES_CBC_ENCRYPT_DATA mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 *  typedef struct CK_AES_CBC_ENCRYPT_DATA_PARAMS {
 *    CK_BYTE iv[16];
 *    CK_BYTE_PTR pData;
 *    CK_ULONG length;
 *  } CK_AES_CBC_ENCRYPT_DATA_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@svc.co.at>
 */
public class CK_AES_CBC_ENCRYPT_DATA_PARAMS {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_BYTE iv[16];
	 * </PRE>
	 * The 16-byte initialization vector.
	 */
	public byte[] iv;

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_BYTE_PTR pData;
	 *   CK_ULONG length;
	 * </PRE>
	 */
	public byte[] pData;

	/**
	 * Returns the string representation of CK_AES_CBC_ENCRYPT_DATA_PARAMS.
	 *
	 * @return the string representation of CK_AES_CBC_ENCRYPT_DATA_PARAMS
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("iv: ");
		buffer.append(Functions.toHexString(iv));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pData: ");
		buffer.append(Functions.toHexString(pData));
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
