package customExceptions;

/**
 * The Class EmptySearchException.
 */
public class EmptySearchException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new empty search exception.
	 *
	 * @param errorMessage the error message
	 */
	public EmptySearchException(String errorMessage) {
		super(errorMessage);
	}

}
