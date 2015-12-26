package iaik.pkcs.pkcs11.wrapper;

/**
 * class .<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_DATE {&nbsp;&nbsp;
 *   CK_CHAR year[4];&nbsp;&nbsp;
 *   CK_CHAR month[2];&nbsp;&nbsp;
 *   CK_CHAR day[2];&nbsp;&nbsp;
 * } CK_DATE;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_DATE implements Cloneable {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_CHAR year[4];   - the year ("1900" - "9999")
	 * </PRE>
	 */
	public char[] year; /* the year ("1900" - "9999") */

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_CHAR month[2];  - the month ("01" - "12")
	 * </PRE>
	 */
	public char[] month; /* the month ("01" - "12") */

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_CHAR day[2];    - the day ("01" - "31")
	 * </PRE>
	 */
	public char[] day; /* the day ("01" - "31") */

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 */
	public Object clone() {
		CK_DATE clone;

		clone = new CK_DATE();
		clone.year = (char[]) this.year.clone();
		clone.month = (char[]) this.month.clone();
		clone.day = (char[]) this.day.clone();

		return clone;
	}

	/**
	 * Returns the string representation of CK_DATE.
	 *
	 * @return the string representation of CK_DATE
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(new String(day));
		buffer.append('.');
		buffer.append(new String(month));
		buffer.append('.');
		buffer.append(new String(year));
		buffer.append(" (DD.MM.YYYY)");

		return buffer.toString();
	}

}
