package model;

import java.util.ArrayList;

import customExceptions.EmptySearchException;

/**
 * The Class Student.
 */
public class Student extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The registers. */
	private ArrayList<Register> registers;

	/** The account. */
	private StudentAccount account;

	/** The school. */
	private VirtualSchool school;

	/**
	 * Instantiates a new student.
	 *
	 * @param code     the code
	 * @param name     the name
	 * @param lastName the last name
	 * @param password the password
	 * @param account  the account
	 * @param school   the school
	 */
	public Student(String code, String name, String lastName, String password, StudentAccount account,
			VirtualSchool school) {
		super(code, name, lastName, password);
		this.registers = new ArrayList<Register>();
		this.school = school;
		this.account = account;
	}

	/**
	 * Adds the register.
	 */
	public void addRegister() {
		int id = registers.size();
		Register register = new Register(id + "" + getCode(), this);
		registers.add(register);
	}

	/**
	 * Removes the register.
	 *
	 * @param id the id
	 */
	public void removeRegister(String id) {
		int index = searchRegister(id);
		registers.remove(index);
	}

	/**
	 * Search register.
	 *
	 * @param id   the id
	 * @param low  the low
	 * @param high the high
	 * @return the int
	 */
	private int searchRegister(String id, int low, int high) {
		int mid = low + (high - low) / 2;
		if (high < low) {
			return -1;
		}
		int idRegisterAux = Integer.parseInt(id);
		int idAux = Integer.parseInt(registers.get(mid).getId());
		if (idAux == idRegisterAux) {
			return mid;
		} else if (idAux > idRegisterAux) {
			return searchRegister(id, low, mid - 1);
		}
		return searchRegister(id, mid + 1, high);
	}

	/**
	 * Search register.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int searchRegister(String id) {
		return searchRegister(id, 0, registers.size() - 1);
	}

	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Register register : registers) {
			courses.addAll(register.getCourses());
		}
		return courses;
	}

	public ArrayList<Course> getCoursesByName(String name) {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Register register : registers) {
			for (Course course : register.getCourses()) {
				if (course.getName().contains(name)) {
					courses.add(course);
				}
			}
		}
		return courses;
	}

	public ArrayList<Course> getCourseByTeacher(String nameTeacher) {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Register register : registers) {
			for (Course course : register.getCourses()) {
				if (course.getTeacher().getName().contains(nameTeacher)) {
					courses.add(course);
				}
			}
		}
		return courses;
	}

	public ArrayList<Course> getCourses(String criteria) throws EmptySearchException {
		ArrayList<Course> courses = getCourseByTeacher(criteria);
		if (courses.size() > 0) {
			return courses;
		}
		courses = getCoursesByName(criteria);
		if (courses.size() > 0) {
			return courses;
		}
		throw new EmptySearchException("The search did not find any match!");
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

	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	public StudentAccount getAccount() {
		return account;
	}

	/**
	 * Sets the account.
	 *
	 * @param account the new account
	 */
	public void setAccount(StudentAccount account) {
		this.account = account;
	}

	/**
	 * Gets the registers.
	 *
	 * @return the registers
	 */
	public ArrayList<Register> getRegisters() {
		return registers;
	}

	/**
	 * Sets the registers.
	 *
	 * @param registers the new registers
	 */
	public void setRegisters(ArrayList<Register> registers) {
		this.registers = registers;
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	/*
	 * @Override public int compareTo(Person o) { int comparation = 0; String
	 * nameOther = o.getName(); int auxiliarComparation =
	 * nameOther.compareTo(getName()); if (auxiliarComparation < 0) { comparation =
	 * 1; } else if (auxiliarComparation > 0) { comparation = -1; } return
	 * comparation; }
	 */
}
