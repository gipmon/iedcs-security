package iaik.pkcs.pkcs11.objects;

/**
 * Objects of this class represent a long attribute of an PKCS#11 object
 * as specified by PKCS#11.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class LongAttribute extends Attribute {

	/**
	 * Default constructor - only for internal use in AttributeArrayAttribute.getValueString().
	 */
	LongAttribute() {
		super();
	}

	/**
	 * Constructor taking the PKCS#11 type of the attribute.
	 *
	 * @param type The PKCS'11 type of this attribute; e.g.
	 *             PKCS11Constants.CKA_VALUE_LEN.
	 * @preconditions (type <> null)
	 * @postconditions
	 */
	public LongAttribute(Long type) {
		super(type);
	}

	/**
	 * Set the long value of this attribute. Null, is also valid.
	 * A call to this method sets the present flag to true.
	 *
	 * @param value The long value to set. May be null.
	 * @preconditions
	 * @postconditions
	 */
	public void setLongValue(Long value) {
		ckAttribute_.pValue = value;
		present_ = true;
	}

	/**
	 * Get the long value of this attribute. Null, is also possible.
	 *
	 * @return The long value of this attribute or null.
	 * @preconditions
	 * @postconditions
	 */
	public Long getLongValue() {
		return (Long) ckAttribute_.pValue;
	}

	/**
	 * Get a string representation of the value of this attribute. The radix
	 * for the presentation can be specified; e.g. 16 for hex, 10 for decimal.
	 *
	 * @param radix The radix for the representation of the value.
	 * @return A string representation of the value of this attribute.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	protected String getValueString(int radix) {
		String valueString;

		if ((ckAttribute_ != null) && (ckAttribute_.pValue != null)) {
			valueString = Long.toString(((Long) ckAttribute_.pValue).longValue(), radix);
		} else {
			valueString = "<NULL_PTR>";
		}

		return valueString;
	}

	/**
	 * Get a string representation of this attribute. The radix for the
	 * presentation of the value can be specified; e.g. 16 for hex, 10 for
	 * decimal.
	 *
	 * @param radix The radix for the representation of the value.
	 * @return A string representation of the value of this attribute.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public String toString(int radix) {
		StringBuffer buffer = new StringBuffer(32);

		if (present_) {
			if (sensitive_) {
				buffer.append("<Value is sensitive>");
			} else {
				buffer.append(getValueString(radix));
			}
		} else {
			buffer.append("<Attribute not present>");
		}

		return buffer.toString();
	}

}
