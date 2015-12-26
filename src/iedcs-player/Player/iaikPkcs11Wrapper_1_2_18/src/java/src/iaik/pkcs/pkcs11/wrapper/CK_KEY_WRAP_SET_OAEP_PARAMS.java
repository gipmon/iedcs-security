package iaik.pkcs.pkcs11.wrapper;

/**
 * class CK_KEY_WRAP_SET_OAEP_PARAMS  provides the parameters to the
 * CKM_KEY_WRAP_SET_OAEP mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_KEY_WRAP_SET_OAEP_PARAMS {
 *   CK_BYTE bBC;
 *   CK_BYTE_PTR pX;
 *   CK_ULONG ulXLen;
 * } CK_KEY_WRAP_SET_OAEP_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_KEY_WRAP_SET_OAEP_PARAMS {

	/**
	 * block contents byte
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_BYTE bBC;
	 * </PRE>
	 */
	public byte bBC; /* block contents byte */

	/**
	 * extra data
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_BYTE_PTR pX;
	 *   CK_ULONG ulXLen;
	 * </PRE>
	 */
	public byte[] pX; /* extra data */

	/**
	 * Returns the string representation of CK_KEY_WRAP_SET_OAEP_PARAMS.
	 *
	 * @return the string representation of CK_KEY_WRAP_SET_OAEP_PARAMS
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("bBC: ");
		buffer.append(bBC);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pX: ");
		buffer.append(Functions.toBinaryString(pX));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("ulXLen: ");
		buffer.append(pX.length);
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
