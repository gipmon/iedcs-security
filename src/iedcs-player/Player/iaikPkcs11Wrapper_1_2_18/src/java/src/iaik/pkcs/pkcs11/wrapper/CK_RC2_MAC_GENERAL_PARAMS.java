package iaik.pkcs.pkcs11.wrapper;

/**
 * class CK_RC2_MAC_GENERAL_PARAMS provides the parameters to the
 * CKM_RC2_MAC_GENERAL mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_RC2_MAC_GENERAL_PARAMS {
 *   CK_ULONG ulEffectiveBits;
 *   CK_ULONG ulMacLength;
 * } CK_RC2_MAC_GENERAL_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_RC2_MAC_GENERAL_PARAMS {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_ULONG ulEffectiveBits;
	 * </PRE>
	 */
	public long ulEffectiveBits; /* effective bits (1-1024) */

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_ULONG ulMacLength;
	 * </PRE>
	 */
	public long ulMacLength; /* Length of MAC in bytes */

	/**
	 * Returns the string representation of CK_RC2_MAC_GENERAL_PARAMS.
	 *
	 * @return the string representation of CK_RC2_MAC_GENERAL_PARAMS
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("ulEffectiveBits: ");
		buffer.append(ulEffectiveBits);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("ulEffectiveBits: ");
		buffer.append(ulEffectiveBits);
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
