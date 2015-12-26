package iaik.pkcs.pkcs11.objects;

/**
 * This is a special version of a long attribute for the type
 * of a object class.
 * It provides a better <code>toString()</code> implementation.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class ObjectClassAttribute extends LongAttribute {

	/**
	 * Empty constructor.
	 * 
	 * @preconditions
	 * @postconditions
	 */
	public ObjectClassAttribute() {
		super(Attribute.CLASS);
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
			valueString = Object.getObjectClassName((Long) ckAttribute_.pValue);
		} else {
			valueString = "<NULL_PTR>";
		}

		return valueString;
	}

}
