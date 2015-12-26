package iaik.pkcs.pkcs11.objects;

/**
 * This is a special version of a long attribute for the type
 * of a certificate type.
 * It provides a better <code>toString()</code> implementation.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class CertificateTypeAttribute extends LongAttribute {

	/**
	 * Empty constructor.
	 * 
	 * @preconditions
	 * @postconditions
	 */
	public CertificateTypeAttribute() {
		super(Attribute.CERTIFICATE_TYPE);
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
			valueString = Certificate.getCertificateTypeName((Long) ckAttribute_.pValue);
		} else {
			valueString = "<NULL_PTR>";
		}

		return valueString;
	}

}
