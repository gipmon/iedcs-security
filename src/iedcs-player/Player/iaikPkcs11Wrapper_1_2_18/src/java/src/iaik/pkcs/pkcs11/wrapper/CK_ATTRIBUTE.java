package iaik.pkcs.pkcs11.wrapper;

/**
 * class CK_ATTRIBUTE includes the type, value and length of an attribute.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_ATTRIBUTE {&nbsp;&nbsp;
 *   CK_ATTRIBUTE_TYPE type;&nbsp;&nbsp;
 *   CK_VOID_PTR pValue;&nbsp;&nbsp;
 *   CK_ULONG ulValueLen;
 * } CK_ATTRIBUTE;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schläffer <schlaeff@sbox.tugraz.at>
 */
public class CK_ATTRIBUTE implements Cloneable {

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_ATTRIBUTE_TYPE type;
	 * </PRE>
	 */
	public long type;

	/**
	 * <B>PKCS#11:</B>
	 * <PRE>
	 *   CK_VOID_PTR pValue;
	 *   CK_ULONG ulValueLen;
	 * </PRE>
	 */
	public Object pValue;

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 */
	public Object clone() {
		CK_ATTRIBUTE clone;

		try {
			clone = (CK_ATTRIBUTE) super.clone();

			// if possible, make a deep clone
			if (clone.pValue instanceof byte[]) {
				clone.pValue = ((byte[]) this.pValue).clone();
			} else if (clone.pValue instanceof char[]) {
				clone.pValue = ((char[]) this.pValue).clone();
			} else if (clone.pValue instanceof CK_DATE) {
				clone.pValue = ((CK_DATE) this.pValue).clone();
			} else if (clone.pValue instanceof boolean[]) {
				clone.pValue = ((boolean[]) this.pValue).clone();
			} else if (clone.pValue instanceof int[]) {
				clone.pValue = ((int[]) this.pValue).clone();
			} else if (clone.pValue instanceof long[]) {
				clone.pValue = ((long[]) this.pValue).clone();
			} else if (clone.pValue instanceof Object[]) {
				clone.pValue = ((Object[]) this.pValue).clone();
			} else {
				// the other supported objecty types: Boolean, Long, Byte, ... are immutable, no clone needed
				clone.pValue = this.pValue;
			}
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new PKCS11RuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Returns the string representation of CK_ATTRIBUTE.
	 *
	 * @return the string representation of CK_ATTRIBUTE
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("type: ");
		buffer.append(type);
		buffer.append(Constants.NEWLINE);

		buffer.append(Constants.INDENT);
		buffer.append("pValue: ");
		buffer.append(pValue.toString());
		//buffer.append(Constants.NEWLINE);

		return buffer.toString();
	}

}
