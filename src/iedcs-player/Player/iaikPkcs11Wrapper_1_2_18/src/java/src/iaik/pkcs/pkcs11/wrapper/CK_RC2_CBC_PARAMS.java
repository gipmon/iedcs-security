package iaik.pkcs.pkcs11.wrapper;

/**
 * class CK_RC2_CBC_PARAMS provides the parameters to the CKM_RC2_CBC and
 * CKM_RC2_CBC_PAD mechanisms.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 *  typedef struct CK_RC2_CBC_PARAMS {
 *    CK_ULONG ulEffectiveBits;
 *    CK_BYTE iv[8];
 *  } CK_RC2_CBC_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_RC2_CBC_PARAMS {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_ULONG ulEffectiveBits;
	 * </PRE>
	 */
	public long ulEffectiveBits; /* effective bits (1-1024) */

	/**
	 * only the first 8 bytes will be used<p>
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_BYTE iv[8];
	 * </PRE>
	 */
	public byte[] iv; /* IV for CBC mode */

	/**
	 * Returns the string representation of CK_RC2_CBC_PARAMS.
	 *
	 * @return the string representation of CK_RC2_CBC_PARAMS
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("ulEffectiveBits: ");
		buffer.append(ulEffectiveBits);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("iv: ");
		buffer.append(Functions.toHexString(iv));
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
