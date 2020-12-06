package model;

/**
 * The Class StudentAccount.
 */
public class StudentAccount extends Account {

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

	public StudentAccount(String id, double ammount) {
		super(id, ammount);
	}
	
}
