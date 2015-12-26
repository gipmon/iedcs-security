package iaik.pkcs.pkcs11;

/**
 * The base class for all runtiem exceptions in this package. It is able to wrap
 * a other exception from a lower layer.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 1.0
 * @invariants
 */
public class TokenRuntimeException extends RuntimeException {

	/**
	 * An encapsulated (inner) exception. Possibly, an exception from a lower
	 * layer that ca be propagated to a higher layer only in wrapped form.
	 */
	protected Exception encapsulatedException_;

	/**
	 * The default constructor.
	 *
	 * @preconditions
	 * @postconditions
	 */
	public TokenRuntimeException() {
		super();
	}

	/**
	 * Constructor taking an exception message.
	 *
	 * @param message The message giving details about the exception to ease
	 *                debugging.
	 * @preconditions
	 * @postconditions
	 */
	public TokenRuntimeException(String message) {
		super(message);
	}

	/**
	 * Constructor taking an other exception to wrap.
	 *
	 * @param encapsulatedException The other exception the wrap into this.
	 * @preconditions
	 * @postconditions
	 */
	public TokenRuntimeException(Exception encapsulatedException) {
		super();
		encapsulatedException_ = encapsulatedException;
	}

	/**
	 * Constructor taking a message for this exception and an other exception to
	 * wrap.
	 *
	 * @param message The message giving details about the exception to ease
	 *                debugging.
	 * @param encapsulatedException The other exception the wrap into this.
	 * @preconditions
	 * @postconditions
	 */
	public TokenRuntimeException(String message, Exception encapsulatedException) {
		super(message);
		encapsulatedException_ = encapsulatedException;
	}

	/**
	 * Get the encapsulated (wrapped) exceptin. May be null.
	 *
	 * @return The encasulated (wrapped) exception, or null if there is no inner
	 *         exception.
	 * @preconditions
	 * @postconditions
	 */
	public Exception getEncapsulatedException() {
		return encapsulatedException_;
	}

	/**
	 * Returns the string representation of this exception, including the string
	 * representation of the wrapped (encapsulated) exception.
	 *
	 * @return The string representation of exception.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());

		if (encapsulatedException_ != null) {
			buffer.append(", Encasulated Exception: ");
			buffer.append(encapsulatedException_.toString());
		}

		return buffer.toString();
	}

}
