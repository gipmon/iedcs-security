package iaik.pkcs.pkcs11.objects;

/**
 * Objects of this class represent a boolean attribute of a PKCS#11 object
 * as specified by PKCS#11.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class BooleanAttribute extends Attribute {

	/**
	 * Default constructor - only for internal use in AttributeArrayAttribute.getValueString().
	 */
	BooleanAttribute() {
		super();
	}

	/**
	 * Constructor taking the PKCS#11 type of the attribute.
	 *
	 * @param type The PKCS'11 type of this attribute; e.g.
	 *             PKCS11Constants.CKA_PRIVATE.
	 * @preconditions (type <> null)
	 * @postconditions
	 */
	public BooleanAttribute(Long type) {
		super(type);
	}

	/**
	 * Set the boolean value of this attribute. Null, is also valid.
	 * A call to this method sets the present flag to true.
	 *
	 * @param value The boolean value to set. May be null.
	 * @preconditions
	 * @postconditions
	 */
	public void setBooleanValue(Boolean value) {
		ckAttribute_.pValue = value;
		present_ = true;
	}

	/**
	 * Get the boolean value of this attribute. Null, is also possible.
	 *
	 * @return The boolean value of this attribute or null.
	 * @preconditions
	 * @postconditions
	 */
	public Boolean getBooleanValue() {
		return (Boolean) ckAttribute_.pValue;
	}

}
