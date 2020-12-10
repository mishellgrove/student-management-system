package model;

/**
 * The Class StudentAccount.
 */
public class StudentAccount extends Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new student account.
	 *
	 * @param person the person
	 * @param id     the id
	 * @param amount the amount
	 */
	public StudentAccount(Person person, String id, double amount) {
		super(person, id, amount);
	}

	/**
	 * Instantiates a new student account.
	 *
	 * @param id      the id
	 * @param ammount the ammount
	 */
	public StudentAccount(String id, double ammount) {
		super(id, ammount);
	}

}
