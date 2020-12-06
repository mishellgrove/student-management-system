package model;

import customExceptions.AmountInputException;
import customExceptions.NotEnoughMoneyException;

/**
 * The Interface IAccount.
 */
public interface IAccount {

	/**
	 * Withdraw money.
	 *
	 * @param amount the amount
	 * @throws NotEnoughMoneyException the not enough money exception
	 */
	abstract void withdrawMoney(double amount) throws NotEnoughMoneyException;

	/**
	 * Deposit money.
	 *
	 * @param amount the amount
	 * @throws AmountInputException the amount input exception
	 */
	abstract void depositMoney(double amount)throws AmountInputException;

}
