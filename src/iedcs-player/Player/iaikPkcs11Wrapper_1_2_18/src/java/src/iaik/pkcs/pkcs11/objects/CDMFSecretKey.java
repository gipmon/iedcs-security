package iaik.pkcs.pkcs11.objects;

import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * Objects of this class represent CDMF secret keys as specified by PKCS#11
 * v2.11.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (value_ <> null)
 */
public class CDMFSecretKey extends SecretKey {

	/**
	 * The value attribute of this secret key.
	 */
	protected ByteArrayAttribute value_;

	/**
	 * Deafult Constructor.
	 *
	 * @preconditions
	 * @postconditions
	 */
	public CDMFSecretKey() {
		super();
		keyType_.setLongValue(KeyType.CDMF);
	}

	/**
	 * Called by getInstance to create an instance of a PKCS#11 CDMF secret
	 * key.
	 *
	 * @param session The session to use for reading attributes.
	 *                This session must have the appropriate rights; i.e.
	 *                it must be a user-session, if it is a private object.
	 * @param objectHandle The object handle as given from the PKCS#111 module.
	 * @exception TokenException If getting the attributes failed.
	 * @preconditions (session <> null)
	 * @postconditions
	 */
	protected CDMFSecretKey(Session session, long objectHandle)
	    throws TokenException
	{
		super(session, objectHandle);
		keyType_.setLongValue(KeyType.CDMF);
	}

	/**
	 * The getInstance method of the SecretKey class uses this method to create
	 * an instance of a PKCS#11 CDMF secret key.
	 *
	 * @param session The session to use for reading attributes.
	 *                This session must have the appropriate rights; i.e.
	 *                it must be a user-session, if it is a private object.
	 * @param objectHandle The object handle as given from the PKCS#111 module.
	 * @return The object representing the PKCS#11 object.
	 *         The returned object can be casted to the
	 *         according sub-class.
	 * @exception TokenException If getting the attributes failed.
	 * @preconditions (session <> null)
	 * @postconditions (result <> null) 
	 */
	public static Object getInstance(Session session, long objectHandle)
	    throws TokenException
	{
		return new CDMFSecretKey(session, objectHandle);
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
	protected static void putAttributesInTable(CDMFSecretKey object) {
		if (object == null) {
			throw new NullPointerException("Argument \"object\" must not be null.");
		}

		object.attributeTable_.put(Attribute.VALUE, object.value_);
	}

	/**
	 * Allocates the attribute objects for this class and adds them to the
	 * attribute table.
	 *
	 * @preconditions
	 * @postconditions
	 */
	protected void allocateAttributes() {
		super.allocateAttributes();

		value_ = new ByteArrayAttribute(Attribute.VALUE);

		putAttributesInTable(this);
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof CDMFSecretKey)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		CDMFSecretKey clone = (CDMFSecretKey) super.clone();

		clone.value_ = (ByteArrayAttribute) this.value_.clone();

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
	 * @preconditions
	 * @postconditions
	 */
	public boolean equals(java.lang.Object otherObject) {
		boolean equal = false;

		if (otherObject instanceof CDMFSecretKey) {
			CDMFSecretKey other = (CDMFSecretKey) otherObject;
			equal = (this == other)
			    || (super.equals(other) && this.value_.equals(other.value_));
		}

		return equal;
	}

	/**
	 * Gets the value attribute of this CDMF key.
	 *
	 * @return The value attribute.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public ByteArrayAttribute getValue() {
		return value_;
	}

	/**
	 * Read the values of the attributes of this object from the token.
	 *
	 * @param session The session handle to use for reading attributes.
	 *                This session must have the appropriate rights; i.e.
	 *                it must be a user-session, if it is a private object.
	 * @exception TokenException If getting the attributes failed.
	 * @preconditions (session <> null)
	 * @postconditions
	 */
	public void readAttributes(Session session)
	    throws TokenException
	{
		super.readAttributes(session);

		Object.getAttributeValue(session, objectHandle_, value_);
	}

	/**
	 * This method returns a string representation of the current object. The
	 * output is only for debugging purposes and should not be used for other
	 * purposes.
	 *
	 * @return A string presentation of this object for debugging output.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer(1024);

		buffer.append(super.toString());

		buffer.append(Constants.NEWLINE);
		buffer.append(Constants.INDENT);
		buffer.append("Value (hex): ");
		buffer.append(value_.toString());

		return buffer.toString();
	}

}
