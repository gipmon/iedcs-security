package iaik.pkcs.pkcs11.objects;

import iaik.pkcs.pkcs11.Util;
import iaik.pkcs.pkcs11.wrapper.CK_DATE;
import iaik.pkcs.pkcs11.wrapper.Functions;

import java.util.Date;

/**
 * Objects of this class represent a date attribute of an PKCS#11 object
 * as specified by PKCS#11.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class DateAttribute extends Attribute {

	/**
	 * Default constructor - only for internal use in AttributeArrayAttribute.getValueString().
	 */
	DateAttribute() {
		super();
	}

	/**
	 * Constructor taking the PKCS#11 type of the attribute.
	 *
	 * @param type The PKCS'11 type of this attribute; e.g.
	 *             PKCS11Constants.CKA_START_DATE.
	 * @preconditions (type <> null)
	 * @postconditions
	 */
	public DateAttribute(Long type) {
		super(type);
	}

	/**
	 * Set the date value of this attribute. Null, is also valid.
	 * A call to this method sets the present flag to true.
	 *
	 * @param value The date value to set. May be null.
	 * @preconditions
	 * @postconditions
	 */
	public void setDateValue(Date value) {
		ckAttribute_.pValue = Util.convertToCkDate(value);
		present_ = true;
	}

	/**
	 * Get the date value of this attribute. Null, is also possible.
	 *
	 * @return The date value of this attribute or null.
	 * @preconditions
	 * @postconditions
	 */
	public Date getDateValue() {
		return Util.convertToDate((CK_DATE) ckAttribute_.pValue);
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

		if (otherObject instanceof DateAttribute) {
			DateAttribute other = (DateAttribute) otherObject;
			equal = (this == other)
			    || (((this.present_ == false) && (other.present_ == false)) || (((this.present_ == true) && (other.present_ == true)) && ((this.sensitive_ == other.sensitive_)
			        && (this.ckAttribute_.type == other.ckAttribute_.type) && Functions.equals(
			        (CK_DATE) this.ckAttribute_.pValue, (CK_DATE) other.ckAttribute_.pValue))));
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
		return ((int) ckAttribute_.type)
		    ^ ((ckAttribute_.pValue != null) ? Functions
		        .hashCode((CK_DATE) ckAttribute_.pValue) : 0);
	}

}
