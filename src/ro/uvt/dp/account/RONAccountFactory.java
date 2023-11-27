package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public class RONAccountFactory implements AccountFactory {
    @Override
    public Account createAccount(String accountNumber, double amount) throws AmountException {
        return new AccountRON(accountNumber, amount);
    }
}
