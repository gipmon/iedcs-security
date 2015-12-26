package iaik.pkcs.pkcs11.parameters;

/**
 * Every parameters class implements this interface through which the module.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
public interface Parameters extends Cloneable {

	/**
	 * Get this parameters object as an object of the corresponding *_PARAMS class
	 * of the iaik.pkcs.pkcs11.wrapper package.
	 *
	 * @return The object of the corresponding *_PARAMS class.
	 * @preconditions
	 * @postconditions (result <> null)
	 */
	public Object getPKCS11ParamsObject();

}
