package ro.uvt.dp.bank;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.exceptions.ClientNotFound;

import java.util.ArrayList;

public class Bank {

	private ArrayList<Client> clients = new ArrayList<>();
	private String bankCode = null;

	private int id;

	public Bank(String bankCode) {
		this.bankCode = bankCode;
	}

	public void addClient(Client c) {
		clients.add(c);
		for (Account account : c.getAccounts()) {
			account.setBank_id(id);
		}
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	
	public Client getClient(String name) {
		for (Client c : clients) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public void removeClient(String name) throws ClientNotFound {
		for (int i=0; i<clients.size(); i++){
			if (clients.get(i).getName().equals(name)){
				clients.remove(i);
				return;
			}
		}
		throw new ClientNotFound("Client not found");

	}

	@Override
	public String toString() {
		return "Bank [code=" + bankCode + ", clients=" + clients + "]";
	}

	public String getName() {
		return bankCode;
	}
}
