package iaik.pkcs.pkcs11.objects;

import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * Objects of this class represent Mechanism Objects as introduced in 
 * PKCS#11 2.20.
 * 
 * @author Florian Reimair
 */
public class Mechanism extends Object {

	/**
	 * The mechanism Type of this Mechanism object.
	 */
	protected LongAttribute mechanismType_;

	/**
	 * The default constructor. An application use this constructor to instantiate
	 * an object that serves as a template. It may also be useful for working with
	 * vendor-defined objects.
	 */
	public Mechanism() {
		super();
	}

	/**
	 * Constructor taking the reference to the PKCS#11 module for accessing the
	 * object's attributes, the session handle to use for reading the attribute
	 * values and the object handle.
	 *
	 * @param session The session to use for reading attributes.
	 *                This session must have the appropriate rights; i.e.
	 *                it must be a user-session, if it is a private object.
	 * @param objectHandle The object handle as given from the PKCS#111 module.
	 * @exception TokenException If getting the attributes failed.
	 */
	protected Mechanism(Session session, long objectHandle)
	    throws TokenException
	{
		super(session, objectHandle);
	}

	/**
	 * Put all attributes of the given object into the attributes table of this
	 * object. This method is only static to be able to access invoke the
	 * implementation of this method for each class separately (see use in
	 * clone()).
	 *
	 * @param object The object to handle.
	 * @preconditions (object <> null)
	 * @postconditions
	 */
	protected static void putAttributesInTable(Mechanism object) {
		if (object == null) {
			throw new NullPointerException("Argument \"object\" must not be null.");
		}

		object.attributeTable_.put(Attribute.MECHANISM_TYPE, object.mechanismType_);
	}

	/**
	 * Allocates the attribute objects for this class and adds them to the
	 * attribute table.
	 */
	protected void allocateAttributes() {
		super.allocateAttributes();

		mechanismType_ = new LongAttribute(Attribute.MECHANISM_TYPE);

		putAttributesInTable(this);
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 */
	public java.lang.Object clone() {
		Mechanism clone = (Mechanism) super.clone();

		clone.mechanismType_ = (LongAttribute) this.mechanismType_.clone();

		putAttributesInTable(clone); // put all cloned attributes into the new table

		return clone;
	}

	/**
	 * Compares all member variables of this object with the other object.
	 * Returns only true, if all are equal in both objects.
	 *
	 * @param otherObject The other object to compare to.
	 * @return True, if other is an instance of this class and all member
	 *         variables of both objects are equal. False, otherwise.
	 */
	public boolean equals(java.lang.Object otherObject) {
		if (otherObject instanceof Mechanism) {
			Mechanism other = (Mechanism) otherObject;
			return (this == other)
			    || (super.equals(other) && this.mechanismType_.equals(other.mechanismType_));
		}

		return false;
	}

	/**
	 * Read the values of the attributes of this object from the token.
	 *
	 * @param session The session handle to use for reading attributes.
	 *                This session must have the appropriate rights; i.e.
	 *                it must be a user-session, if it is a private object.
	 * @exception TokenException If getting the attributes failed.
	 */
	public void readAttributes(Session session)
	    throws TokenException
	{
		super.readAttributes(session);

		/*
		 * read multiple attributes at once might cause a performance gain
		 * but we only have one attribute here.
		 */
		Object.getAttributeValue(session, objectHandle_, mechanismType_);
		//    Object.getAttributeValues(session, objectHandle_, new Attribute[] {
		//        mechanismType_});
	}

	/**
	 * @return returns the mechanism type of this mechanism object.
	 */
	public LongAttribute getMechanismType() {
		return mechanismType_;
	}

	/**
	 * This method returns a string representation of the current object. The
	 * output is only for debugging purposes and should not be used for other
	 * purposes.
	 *
	 * @return A string presentation of this object for debugging output.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer(32);

		buffer.append(super.toString());

		buffer.append(Constants.NEWLINE);
		buffer.append(Constants.INDENT);
		buffer.append("Mechanism Type: ");
		buffer.append(mechanismType_.toString());

		return buffer.toString();
	}

	/**
	 * The overriding of this method should ensure that the objects of this class
	 * work correctly in a hashtable.
	 *
	 * @return The hash code of this object.
	 */
	public int hashCode() {
		return mechanismType_.hashCode();
	}
}
