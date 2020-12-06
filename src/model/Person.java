package model;

/**
 * The Class Person.
 */
public abstract class Person implements Comparable<Person> {

	/** The code. */
	private String code;
	private String password;
	/** The name. */
	private String name;

	/** The last name. */
	private String lastName;

	public Person() {
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param code     the code
	 * @param name     the name
	 * @param lastName the last name
	 */
	public Person(String code, String name, String lastName, String password) {
		this.code = code;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Person [code=" + code + ", name=" + name + ", lastName=" + lastName + "]";
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Person o) {
		int comparation = 0;
		int auxiliarCode1 = Integer.parseInt(this.code);
		int auxiliarCode2 = Integer.parseInt(o.getCode());

		if (auxiliarCode1 < auxiliarCode2) {
			comparation = -1;
		} else if (auxiliarCode1 > auxiliarCode2) {
			comparation = 1;
		}

		return comparation;
	}
}
