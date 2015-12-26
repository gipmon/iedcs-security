package iaik.pkcs.pkcs11.objects;

/**
 * This is a special version of a long attribute for the type
 * of a hardware feature.
 * It provides a better <code>toString()</code> implementation.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class HardwareFeatureTypeAttribute extends LongAttribute {

	/**
	 * Empty constructor.
	 * 
	 * @preconditions
	 * @postconditions
	 */
	public HardwareFeatureTypeAttribute() {
		super(Attribute.HW_FEATURE_TYPE);
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
			valueString = HardwareFeature
			    .getHardwareFeatureTypeName((Long) ckAttribute_.pValue);
		} else {
			valueString = "<NULL_PTR>";
		}

		return valueString;
	}

}
