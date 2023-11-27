package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.interfaces.Operations;

public abstract class Account implements Operations {

	protected String accountCode = null;

	protected boolean blocked = false;
	protected double amount = 0;

	public double getAmount() {
		return amount;
	}

	public static enum TYPE {
		EUR, RON
	};

	protected Account(String accountNumber, double amount) throws AmountException {
		this.accountCode = accountNumber;
		depose(amount);
	}

	@Override
	public double getTotalAmount() {

		return amount + amount * getInterest();
	}

	@Override
	public void depose(double amount) throws AmountException {
		if (isBlocked())
			throw new AmountException("Account is blocked");

		if (amount < 0)
			throw new AmountException("Invalid amount to depose");
		this.amount += amount;
	}

	@Override
	public void retrieve(double amount) throws AmountException {
		if (isBlocked())
			throw new AmountException("Account is blocked");

		if (amount > this.amount)
			throw new AmountException("Insufficient funds");
		this.amount -= amount;
	}

	public void blockAccount(){
		blocked = true;
	}

	public void unblockAccount(){
		blocked = false;
	}

	public boolean isBlocked(){
		return blocked;
	}

	public String toString() {
		return "Account: code=" + accountCode + ", amount=" + amount;
	}

	public String getAccountNumber() {
		return accountCode;
	}

}
