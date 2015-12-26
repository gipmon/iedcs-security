package iaik.pkcs.pkcs11.parameters;

//import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * This class encapsulates parameters for Mechanisms.CONCATENATE_BASE_AND_KEY.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public class ObjectHandleParameters implements Parameters {

	/**
	 * The PKCS#11 object.
	 */
	protected iaik.pkcs.pkcs11.objects.Object object_;

	/**
	 * Create a new ObjectHandleParameters object using the given object.
	 *
	 * @param object The PKCS#11 object which's handle to use.
	 * @preconditions
	 * @postconditions
	 */
	public ObjectHandleParameters(iaik.pkcs.pkcs11.objects.Object object) {
		object_ = object;
	}

	/**
	 * Create a (deep) clone of this object.
	 *
	 * @return A clone of this object.
	 * @preconditions
	 * @postconditions (result <> null)
	 *                 and (result instanceof ObjectHandleParameters)
	 *                 and (result.equals(this))
	 */
	public java.lang.Object clone() {
		ObjectHandleParameters clone;

		try {
			clone = (ObjectHandleParameters) super.clone();

			clone.object_ = (iaik.pkcs.pkcs11.objects.Object) this.object_.clone();
		} catch (CloneNotSupportedException ex) {
			// this must not happen, because this class is cloneable
			throw new TokenRuntimeException("An unexpected clone exception occurred.", ex);
		}

		return clone;
	}

	/**
	 * Get this parameters object as an Long object, which is the handle of the
	 * underlying object.
	 *
	 * @return This object as a Long object.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject() {
		return new Long(object_.getObjectHandle());
	}

	/**
	 * Get the PKCS#11 object.
	 *
	 * @return The PKCS#11 object.
	 * @preconditions
	 * @postconditions
	 */
	public iaik.pkcs.pkcs11.objects.Object getObject() {
		return object_;
	}

	/**
	 * Set the PKCS#11 object.
	 *
	 * @param object The PKCS#11 object.
	 * @preconditions
	 * @postconditions
	 */
	public void setObjectHandle(iaik.pkcs.pkcs11.objects.Object object) {
		object_ = object;
	}

	/**
	 * Returns the string representation of this object. Do not parse data from
	 * this string, it is for debugging only.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.INDENT);
		buffer.append("The Object: ");
		buffer.append(Constants.NEWLINE);
		buffer.append(object_);
		// buffer.append(Constants.NEWLINE);

		return buffer.toString();
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

		if (otherObject instanceof ObjectHandleParameters) {
			ObjectHandleParameters other = (ObjectHandleParameters) otherObject;
			equal = (this == other) || ((this != null) && this.object_.equals(other.object_));
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
		return (object_ != null) ? object_.hashCode() : 0;
	}

}
