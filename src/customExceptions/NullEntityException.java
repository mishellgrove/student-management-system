package customExceptions;

public class NullEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullEntityException(String errorMessage) {
		super(errorMessage);
	}

}
