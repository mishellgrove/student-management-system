package model;

/**
 * The Class Director.
 */
public class Director extends Employee {

	/** The school. */
	private VirtualSchool school;

	/**
	 * Instantiates a new director.
	 *
	 * @param code     the code
	 * @param name     the name
	 * @param lastName the last name
	 * @param salary   the salary
	 * @param school   the school
	 */
	public Director(String code, String name, String lastName, String password, double salary, VirtualSchool school) {
		super(code, name, lastName, password, salary);
		this.school = school;
	}

	/**
	 * Gets the school.
	 *
	 * @return the school
	 */
	public VirtualSchool getSchool() {
		return school;
	}

	/**
	 * Sets the school.
	 *
	 * @param school the new school
	 */
	public void setSchool(VirtualSchool school) {
		this.school = school;
	}

}
