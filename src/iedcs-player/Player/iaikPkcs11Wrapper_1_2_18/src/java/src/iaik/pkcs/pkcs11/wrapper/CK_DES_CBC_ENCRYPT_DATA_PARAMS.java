package iaik.pkcs.pkcs11.wrapper;

/**
 * The class CK_DES_CBC_ENCRYPT_DATA_PARAMS provides the parameters to the 
 * CKM_DES_CBC_ENCRYPT_DATA and CKM_DES3_CBC_ENCRYPT_DATA mechanisms.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 *  typedef struct CK_DES_CBC_ENCRYPT_DATA_PARAMS {
 *    CK_BYTE iv[8];
 *    CK_BYTE_PTR pData;
 *    CK_ULONG length;
 *  } CK_DES_CBC_ENCRYPT_DATA_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@svc.co.at>
 */
public class CK_DES_CBC_ENCRYPT_DATA_PARAMS {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_BYTE iv[8];
	 * </PRE>
	 * The 8-byte initialization vector.
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
	 * Returns the string representation of CK_DES_CBC_ENCRYPT_DATA_PARAMS.
	 *
	 * @return the string representation of CK_DES_CBC_ENCRYPT_DATA_PARAMS
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
