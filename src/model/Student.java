package model;

import java.util.ArrayList;


/**
 * The Class Student.
 */
public class Student extends Person {

	/** The registers. */
	private ArrayList<Register> registers;

	/** The account. */
	private StudentAccount account;
	private VirtualSchool school;

	/**
	 * Instantiates a new student.
	 *
	 * @param code     the code
	 * @param name     the name
	 * @param lastName the last name
	 * @param account  the account
	 */
	public Student(String code, String name, String lastName, String password, StudentAccount account,
			VirtualSchool school) {
		super(code, name, lastName, password);
		this.registers = new ArrayList<Register>();
		this.school = school;
	}

	public void addRegister() {
		int id = registers.size();
		Register register = new Register(id + "", this);
		registers.add(register);
	}

	public void removeRegister(String id) {
		int index = searchRegister(id);
		registers.remove(index);
	}

	private int searchRegister(String id, int low, int high) {
		int mid = low +(high - low) / 2;
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

	public int searchRegister(String id) {
		return searchRegister(id, 0, registers.size()-1);
	}

	public VirtualSchool getSchool() {
		return school;
	}

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
	@Override
	public int compareTo(Person o) {
		int comparation = 0;
		String nameOther = o.getName();
		int auxiliarComparation = nameOther.compareTo(getName());
		if (auxiliarComparation < 0) {
			comparation = 1;
		} else if (auxiliarComparation > 0) {
			comparation = -1;
		}
		return comparation;
	}
}
