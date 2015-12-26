package iaik.pkcs.pkcs11.objects;

import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * Objects of this class represent a byte-array attribute of a PKCS#11 object
 * as specified by PKCS#11.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class ByteArrayAttribute extends Attribute {

	/**
	 * Default constructor - only for internal use in AttributeArrayAttribute.getValueString().
	 */
	ByteArrayAttribute() {
		super();
	}

	/**
	 * Constructor taking the PKCS#11 type of the attribute.
	 *
	 * @param type The PKCS'11 type of this attribute; e.g.
	 *             PKCS11Constants.CKA_VALUE.
	 * @preconditions (type <> null)
	 * @postconditions
	 */
	public ByteArrayAttribute(Long type) {
		super(type);
	}

	/**
	 * Set the byte-array value of this attribute. Null, is also valid.
	 * A call to this method sets the present flag to true.
	 *
	 * @param value The byte-array value to set. May be null.
	 * @preconditions
	 * @postconditions
	 */
	public void setByteArrayValue(byte[] value) {
		ckAttribute_.pValue = value;
		present_ = true;
	}

	/**
	 * Get the byte-array value of this attribute. Null, is also possible.
	 *
	 * @return The byte-array value of this attribute or null.
	 * @preconditions
	 * @postconditions
	 */
	public byte[] getByteArrayValue() {
		return (byte[]) ckAttribute_.pValue;
	}

	/**
	 * Get a string representation of the value of this attribute.
	 *
	 * @return A string representation of the value of this attribute.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	protected String getValueString() {
		String valueString;

		if ((ckAttribute_ != null) && (ckAttribute_.pValue != null)) {
			valueString = Functions.toHexString((byte[]) ckAttribute_.pValue);
		} else {
			valueString = "<NULL_PTR>";
		}

		return valueString;
	}

	/**
	 * Compares all member variables of this object with the other object.
	 * Returns only true, if all are equal in both objects.
	 *
	 * @param otherObject The other object to compare to.
	 * @return True, if other is an instance of this class and all member
	 *         variables of both objects are equal. False, otherwise.
	 * @preconditions
	 * @postconditions
	 */
	public boolean equals(java.lang.Object otherObject) {
		boolean equal = false;

		if (otherObject instanceof ByteArrayAttribute) {
			ByteArrayAttribute other = (ByteArrayAttribute) otherObject;
			equal = (this == other)
			    || (((this.present_ == false) && (other.present_ == false)) || (((this.present_ == true) && (other.present_ == true)) && ((this.sensitive_ == other.sensitive_) && Functions
			        .equals((byte[]) this.ckAttribute_.pValue,
			            (byte[]) other.ckAttribute_.pValue))));
		}

		return equal;
	}

	/**
	 * The overriding of this method should ensure that the objects of this class
	 * work correctly in a hashtable.
	 *
	 * @return The hash code of this object.
	 * @preconditions
	 * @postconditions
	 */
	public int hashCode() {
		return (ckAttribute_.pValue != null) ? Functions
		    .hashCode((byte[]) ckAttribute_.pValue) : 0;
	}

}
