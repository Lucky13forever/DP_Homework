package ro.uvt.dp.client;

import java.util.ArrayList;
import java.util.Collection;

import ro.uvt.dp.account.*;
import ro.uvt.dp.account.Account.TYPE;
import ro.uvt.dp.exceptions.AccountNotFound;
import ro.uvt.dp.exceptions.AmountException;

public class Client {
	private String name;
	private String address;

	private String dateOfBirth;
	private ArrayList<Account> accounts = new ArrayList<>();

	public Client(String name, String adress, TYPE type, String accountNumber, double amount) throws AmountException {
		this.name = name;
		this.address = adress;
		addAccount(type, accountNumber, amount);
	}

	public String getAddress() {
		return address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public static class Builder {
		private String name;
		private String address;

		private AccountFactory accountFactory;
		private String dateOfBirth;  // Additional client information
		private ArrayList<Account> accounts = new ArrayList<>();

		public Builder(String name, String address) {
			this.name = name;
			this.address = address;
		}

		public Builder dateOfBirth(String dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder account(String accountNumber, double amount) throws AmountException {
			Account account = accountFactory.createAccount(accountNumber, amount);
			accounts.add(account);
			return this;
		}

		public Builder accountFactory(AccountFactory accountFactory){
			this.accountFactory = accountFactory;
			return this;
		}

		public Client build() throws AmountException {
			return new Client(this);
		}
	}

	private Client(Builder builder) {
		this.name = builder.name;
		this.address = builder.address;
		this.dateOfBirth = builder.dateOfBirth;
		this.accounts = builder.accounts;
	}

	public void addAccount(TYPE type, String accountNumber, double amount) throws AmountException {
		Account c = null;
		if (type == Account.TYPE.EUR)
			c = new AccountEUR(accountNumber, amount);
		else if (type == Account.TYPE.RON)
			c = new AccountRON(accountNumber, amount);
		accounts.add(c);
	}

	public Account getAccount(String accountCode) {
		for (Account a : accounts) {
			if (a.getAccountNumber().equals(accountCode)) {
				return a;
			}
		}
		return null;
	}

	public void closeAccount(String accountCode) throws AccountNotFound {
		for (int i=0; i<accounts.size(); i++){
			if (accounts.get(i).getAccountNumber().equals(accountCode)){
				accounts.remove(i);
				return;
			}
		}
		throw new AccountNotFound("Account not found");
	}

	public ArrayList<Account> getAccounts(){
		return accounts;
	}

	@Override
	public String toString() {
		return "\n\tClient [name=" + name + ", address=" + address + ", acounts=" + accounts + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String nume) {
		this.name = nume;
	}
}
