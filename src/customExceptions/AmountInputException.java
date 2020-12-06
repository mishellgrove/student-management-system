package customExceptions;

public class AmountInputException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AmountInputException(String errorMessage) {
		super(errorMessage);
	}

}
