package customExceptions;

public class NotEnoughMoneyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NotEnoughMoneyException(String erroMessage) {
		super(erroMessage);
	}

}
