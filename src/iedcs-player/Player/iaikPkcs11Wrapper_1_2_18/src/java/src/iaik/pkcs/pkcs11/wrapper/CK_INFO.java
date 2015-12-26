package iaik.pkcs.pkcs11.wrapper;

/**
 * class  CK_INFO provides general information about Cryptoki.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 *  typedef struct CK_INFO {&nbsp;&nbsp;
 *    CK_VERSION cryptokiVersion;&nbsp;&nbsp;
 *    CK_UTF8CHAR manufacturerID[32];&nbsp;&nbsp;
 *    CK_FLAGS flags;&nbsp;&nbsp;
 *    CK_UTF8CHAR libraryDescription[32];&nbsp;&nbsp;
 *    CK_VERSION libraryVersion;&nbsp;&nbsp;
 *  } CK_INFO;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_INFO {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_VERSION cryptokiVersion;
	 * </PRE>
	 */
	public CK_VERSION cryptokiVersion; /* Cryptoki interface ver */

	/**
	 * must be blank padded - only the first 32 chars will be used<p>
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_UTF8CHAR manufacturerID[32];
	 * </PRE>
	 */
	public char[] manufacturerID; /* blank padded - only first 32 */
	/* chars will be used */

	/**
	 * must be zero
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_FLAGS flags;
	 * </PRE>
	 */
	public long flags; /* must be zero */

	/* libraryDescription and libraryVersion are new for v2.0 */

	/**
	 * must be blank padded - only the first 32 chars will be used<p>
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *
	 * </PRE>
	 */
	public char[] libraryDescription; /* blank padded - only first 32 */
	/* chars will be used */

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_VERSION libraryVersion;
	 * </PRE>
	 */
	public CK_VERSION libraryVersion; /* version of library */

	/**
	 * Returns the string representation of CK_INFO.
	 *
	 * @return the string representation of CK_INFO
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("cryptokiVersion: ");
		buffer.append(cryptokiVersion.toString());
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("manufacturerID: ");
		buffer.append(new String(manufacturerID));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("flags: ");
		buffer.append(Functions.toBinaryString(flags));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("libraryDescription: ");
		buffer.append(new String(libraryDescription));
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("libraryVersion: ");
		buffer.append(libraryVersion.toString());
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
