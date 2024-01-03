package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.interfaces.Transfer;

public class AccountRON extends Account implements Transfer {

	public AccountRON(String accountNumber, double amount) throws AmountException {
		super(accountNumber, amount);
		this.fiat = "RON";
	}

	protected AccountRON(int id, int bank_id, int client_id, String accountNumber, String type, String fiat, double amount, boolean blocked) throws AmountException {
		super(id, bank_id, client_id, accountNumber, type, fiat, amount, blocked);
	}

	public double getInterest() {
		if (amount < 500)
			return 0.03;
		else
			return 0.08;

	}

	@Override
	public String toString() {
		return "Account RON [" + super.toString() + "]";
	}

	@Override
	public void transfer(Account c, double s) throws AmountException {
		c.retrieve(s);
		depose(s);
	}


}
