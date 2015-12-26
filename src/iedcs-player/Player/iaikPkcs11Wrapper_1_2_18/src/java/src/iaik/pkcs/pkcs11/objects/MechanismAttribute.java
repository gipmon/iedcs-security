package iaik.pkcs.pkcs11.objects;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.wrapper.Functions;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

/**
 * Objects of this class represent a mechanism attribute of an PKCS#11 object
 * as specified by PKCS#11.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants (ckAttribute_ <> null)
 */
public class MechanismAttribute extends LongAttribute {

	/**
	 * Default constructor - only for internal use in AttributeArrayAttribute.getValueString().
	 */
	MechanismAttribute() {
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
	public MechanismAttribute(Long type) {
		super(type);
	}

	/**
	 * Set the mechanism value of this attribute. 
	 * <code>null</code>, is also valid.
	 * A call to this method sets the present flag to true.
	 *
	 * @param mechanism The mechanism value to set. May be <code>null</code>.
	 * @preconditions
	 * @postconditions
	 */
	public void setMechanism(Mechanism mechanism) {
		ckAttribute_.pValue = (mechanism != null) ? new Long(mechanism.getMechanismCode())
		    : null;
		present_ = true;
	}

	/**
	 * Get the long value of this attribute. Null, is also possible.
	 *
	 * @return The long value of this attribute or null.
	 * @preconditions
	 * @postconditions
	 */
	public Mechanism getMechanism() {
		return ((ckAttribute_ != null) && (ckAttribute_.pValue != null)) ? new Mechanism(
		    ((Long) ckAttribute_.pValue).longValue()) : null;
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
			if (((Long) ckAttribute_.pValue).longValue() != PKCS11Constants.CK_UNAVAILABLE_INFORMATION) {
				valueString = Functions.mechanismCodeToString(((Long) ckAttribute_.pValue)
				    .longValue());
			} else {
				valueString = "<Information unavailable>";
			}
		} else {
			valueString = "<NULL_PTR>";
		}

		return valueString;
	}

}
