package iaik.pkcs.pkcs11.objects;

import java.util.Enumeration;

import iaik.pkcs.pkcs11.wrapper.CK_ATTRIBUTE;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * Objects of this class represent a attribute array of a PKCS#11 object
 * as specified by PKCS#11. This attribute is available since
 * cryptoki version 2.20.
 * 
 *
 * @author <a href="mailto:Birgit.Haas@iaik.tugraz.at"> Birgit Haas </a>
 * @version 1.0
 * @invariants
 */
public class AttributeArray extends Attribute {

	/**
	 * The attributes of this attribute array in their object class representation.
	 * Needed for printing and comparing this attribute array.
	 */
	protected GenericTemplate template_;

	/**
	 * Default constructor - only for internal use.
	 */
	AttributeArray() {
		super();
	}

	/**
	 * Constructor taking the PKCS#11 type of the attribute.
	 *
	 * @param type The PKCS#11 type of this attribute; e.g.
	 *             PKCS11Constants.CKA_VALUE.
	 * @preconditions (type <> null)
	 * @postconditions
	 */
	public AttributeArray(Long type) {
		super(type);
	}

	/**
	 * Set the attributes of this attribute array by specifying a GenericTemplate.
	 * Null, is also valid.
	 * A call to this method sets the present flag to true.
	 *
	 * @param value The AttributeArray value to set. May be null.
	 * @preconditions
	 * @postconditions
	 */
	public void setAttributeArrayValue(GenericTemplate value) {

		template_ = value;

		int length = template_.attributeTable_.size();
		CK_ATTRIBUTE[] attributes = null;
		if (length > 0) {
			attributes = new CK_ATTRIBUTE[length];
			Enumeration attributeEnumeration = template_.attributeTable_.elements();
			int counter = 0;
			while (attributeEnumeration.hasMoreElements()) {
				Attribute attribute = (Attribute) attributeEnumeration.nextElement();
				attributes[counter] = attribute.getCkAttribute();
				counter++;
			}
		}
		ckAttribute_.pValue = attributes;
		present_ = true;
	}

	/**
	 * Get the attribute array value of this attribute. Null, is also possible.
	 *
	 * @return The attribute array value of this attribute or null.
	 * @preconditions
	 * @postconditions
	 */
	public GenericTemplate getAttributeArrayValue() {
		if (template_ == null) {
			if (ckAttribute_.pValue != null
			    && ((CK_ATTRIBUTE[]) ckAttribute_.pValue).length > 0) {
				CK_ATTRIBUTE[] attributesArray = (CK_ATTRIBUTE[]) ckAttribute_.pValue;
				GenericTemplate template = new GenericTemplate();
				for (int i = 0; i < attributesArray.length; i++) {
					Long type = new Long(attributesArray[i].type);
					Class implementation = (Class) Attribute.getAttributeClass(type);
					Attribute attribute;
					if (implementation == null) {
						attribute = new Attribute(type);
						attribute.setCkAttribute(attributesArray[i]);
					} else {
						try {
							attribute = (Attribute) implementation.newInstance();
							attribute.setCkAttribute(attributesArray[i]);
							attribute.setPresent(true);
							template.addAttribute(attribute);
						} catch (Exception ex) {
							System.err.println("Error when trying to create a " + implementation
							    + " instance for " + type + ": " + ex.getMessage());
							System.err.flush();
							System.exit(1);
						}
					}
				}
				return template;
			} else {
				return null;
			}
		} else {
			return template_;
		}
	}

	/**
	 * Get a string representation of the value of this attribute.
	 *
	 * @return A string representation of the value of this attribute.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	protected String getValueString() {
		String valueString = "";
		if (template_ == null) template_ = getAttributeArrayValue();
		if (template_ == null) {
			valueString = "<NULL_PTR>";
		} else {
			String indent = Constants.INDENT + Constants.INDENT + Constants.INDENT;
			valueString += template_.toString(true, true, indent);
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

		if (otherObject instanceof AttributeArray) {
			AttributeArray other = (AttributeArray) otherObject;
			if (this.template_ == null) this.template_ = this.getAttributeArrayValue();
			if (other.template_ == null) other.template_ = other.getAttributeArrayValue();
			equal = (this == other)
			    || (((this.present_ == false) && (other.present_ == false)) || (((this.present_ == true) && (other.present_ == true)) && ((this.sensitive_ == other.sensitive_) && (this.template_
			        .equals(other.template_)))));
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
		if (template_ == null) template_ = getAttributeArrayValue();
		return template_.hashCode();
	}

	/**
	 * Create a (deep) clone of this object.
	 * The attributes in the CK_ATTRIBUTE[] need not be cloned, as they can't be set
	 * separately.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof AttributeArray)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		AttributeArray clone;

		clone = (AttributeArray) super.clone();
		if (template_ == null) template_ = getAttributeArrayValue();
		if (template_ != null) clone.template_ = (GenericTemplate) this.template_.clone();
		return clone;
	}

}
