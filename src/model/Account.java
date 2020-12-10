package model;

import java.io.Serializable;

import customExceptions.AmountInputException;
import customExceptions.NotEnoughMoneyException;

/**
 * The Class Account.
 */
public abstract class Account implements IAccount, Comparable<Account>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The person. */
	private Person person;

	/** The id. */
	private String id;

	/** The amount. */
	private double amount;

	/**
	 * Instantiates a new account.
	 *
	 * @param person the person
	 * @param id     the id
	 * @param amount the amount
	 */
	public Account(Person person, String id, double amount) {
		this.person = person;
		this.id = id;
		this.amount = amount;
	}

	public Account(String id, double ammount) {
		this.id = id;
		this.amount = ammount;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the person.
	 *
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets the person.
	 *
	 * @param person the new person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Account o) {
		int comparation = 0;
		String idOther = o.getId();
		int auxComparation = id.compareTo(idOther);
		if (auxComparation < 0) {
			comparation = -1;
		} else if (auxComparation > 0) {
			comparation = 1;
		}
		return comparation;
	}

	/**
	 * Withdraw money.
	 *
	 * @param amount the amount
	 * @throws NotEnoughMoneyException the not enough money exception
	 */
	@Override
	public void withdrawMoney(double amount) throws NotEnoughMoneyException {
		double res = this.amount - amount;
		if (res < 0) {
			throw new NotEnoughMoneyException("The amount in your account is less than: " + amount + "$");
		}
		setAmount(res);
	}

	/**
	 * Deposit money.
	 *
	 * @param amount the amount
	 * @throws AmountInputException the amount input exception
	 */
	@Override
	public void depositMoney(double amount) throws AmountInputException {
		if (amount <= 0) {
			throw new AmountInputException("The money input is wrong");
		}
		setAmount(getAmount() + amount);
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getExportFormat() {
		return getId() + "," + getAmount();
	}
}
