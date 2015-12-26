package iaik.pkcs.pkcs11.wrapper;

/**
 * class CK_RC5_PARAMS provides the parameters to the CKM_RC5_ECB and
 * CKM_RC5_MAC mechanisms.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 *  typedef struct CK_RC5_PARAMS {&nbsp;&nbsp;
 *    CK_ULONG ulWordsize;&nbsp;&nbsp;
 *    CK_ULONG ulRounds;&nbsp;&nbsp;
 *  } CK_RC5_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_RC5_PARAMS {

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
	 * Returns the string representation of CK_RC5_PARAMS.
	 *
	 * @return the string representation of CK_RC5_PARAMS
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
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
