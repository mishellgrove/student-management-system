package model;

/**
 * The Class TeacherAccount.
 */
public class TeacherAccount extends Account {

	/**
	 * Instantiates a new teacher account.
	 *
	 * @param person the person
	 * @param id     the id
	 * @param amount the amount
	 */
	public TeacherAccount(Person person, String id, double amount) {
		super(person, id, amount);
	}

	public TeacherAccount(String id, double ammount) {
		super(id, ammount);
	}

}
