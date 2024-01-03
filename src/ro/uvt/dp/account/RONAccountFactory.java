package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public class RONAccountFactory implements AccountFactory {
    @Override
    public Account createAccount(String accountNumber, double amount) throws AmountException {
        return new AccountRON(accountNumber, amount);
    }

    public Account createAccount(int id, int bank_id, int client_id, String accountNumber, String type, String fiat, double amount, boolean blocked) throws AmountException {
        return new AccountRON(id, bank_id, client_id, accountNumber, type, fiat, amount, blocked);
    }

    public String getFiat() {
        return "EUR";
    }
}
