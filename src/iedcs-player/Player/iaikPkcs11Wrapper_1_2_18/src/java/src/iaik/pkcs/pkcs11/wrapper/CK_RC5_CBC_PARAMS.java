package iaik.pkcs.pkcs11.wrapper;

/**
 * class CK_RC5_CBC_PARAMS is a structure that provides the parameters to the
 * CKM_RC5_CBC and CKM_RC5_CBC_PAD mechanisms.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 *  typedef struct CK_RC5_CBC_PARAMS {
 *    CK_ULONG ulWordsize;
 *    CK_ULONG ulRounds;
 *    CK_BYTE_PTR pIv;
 *    CK_ULONG ulIvLen;
 *  } CK_RC5_CBC_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_RC5_CBC_PARAMS {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_ULONG ulWordsize;
	 * </PRE>
	 */
	public long ulWordsize; /* wordsize in bits */

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_ULONG ulRounds;
	 * </PRE>
	 */
	public long ulRounds; /* number of rounds */

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_BYTE_PTR pIv;
	 *   CK_ULONG ulIvLen;
	 * </PRE>
	 */
	public byte[] pIv; /* pointer to IV *///FIXME: PTR

	/**
	 * Returns the string representation of CK_RC5_CBC_PARAMS.
	 *
	 * @return the string representation of CK_RC5_CBC_PARAMS
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("ulWordsize: ");
		buffer.append(ulWordsize);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("ulRounds: ");
		buffer.append(ulRounds);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pIv: ");
		buffer.append(Functions.toHexString(pIv));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("ulIv: ");
		buffer.append(pIv.length);
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
