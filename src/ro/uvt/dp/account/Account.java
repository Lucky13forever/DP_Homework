package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.interfaces.Operations;

public abstract class Account implements Operations {

	protected int bank_id = 0;
	protected int client_id = 0;

	protected int id = 0;

	protected String type;

	protected String accountCode = null;

	public String fiat = null;

	protected boolean blocked = false;
	protected double amount = 0;

	public double getAmount() {
		return amount;
	}

	public int getBank_id() {

		return bank_id;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setBank_id(int id) {
		this.bank_id = id;
	}

	public static enum TYPE {
		EUR, RON
	};

	protected Account(String accountNumber, double amount) throws AmountException {
		this.accountCode = accountNumber;
		depose(amount);
	}

	protected Account(int id, int bank_id, int client_id, String accountNumber, String type, String fiat, double amount, boolean blocked) throws AmountException {
		this.id = id;
		this.bank_id = bank_id;
		this.client_id = client_id;
		this.accountCode = accountNumber;
		this.type = type;
		this.fiat = fiat;
		this.amount = amount;
		this.blocked = blocked;
	}

	protected Account(Account account){
		this.id = account.id;
		this.bank_id = account.bank_id;
		this.client_id = account.client_id;
		this.accountCode = account.accountCode;
		this.type = account.type;
		this.fiat = account.fiat;
		this.amount = account.amount;
		this.blocked = account.blocked;
	}

	public int getId() {
		return id;
	}

	public void setBankId(int bank_id) {
		this.bank_id = bank_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	@Override
	public double getTotalAmount() {

		return amount + amount * getInterest();
	}

	public String getType(){
		return type;
	}

	public String getFiat(){
		return fiat;
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
		return String.format("id: %d, bank_id: %d, client_id: %d, name: %s, type: %s, fiat: %s, amount: %f, blocked: %b", id, bank_id, client_id, accountCode, type, fiat, amount, blocked);
	}

	public String getAccountNumber() {
		return accountCode;
	}

}
