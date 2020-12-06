package customExceptions;

public class EntityRepeatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityRepeatedException(String errorMessage) {
		super(errorMessage);
	}

}
