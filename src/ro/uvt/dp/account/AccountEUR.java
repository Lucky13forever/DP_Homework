package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.interfaces.Transfer;

public class AccountEUR extends Account implements Transfer {



	public AccountEUR(String accountNumber, double amount) throws AmountException {
		super(accountNumber, amount);
		this.fiat = "EUR";
	}

	protected AccountEUR(int id, int bank_id, int client_id, String accountNumber, String type, String fiat, double amount, boolean blocked) throws AmountException {
		super(id, bank_id, client_id, accountNumber, type, fiat, amount, blocked);
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
