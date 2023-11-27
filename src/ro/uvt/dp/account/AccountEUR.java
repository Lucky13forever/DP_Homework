package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.interfaces.Transfer;

public class AccountEUR extends Account implements Transfer {

	public AccountEUR(String accountNumber, double amount) throws AmountException {
		super(accountNumber, amount);
	}

	public double getInterest() {
		return 0.01;

	}

	@Override
	public String toString() {
		return "Account EUR [" + super.toString() + "]";
	}

	@Override
	public void transfer(Account c, double s) throws AmountException {
		c.retrieve(s);
		depose(s);
	}
}
