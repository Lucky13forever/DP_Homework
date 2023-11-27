package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public class EURAccountFactory implements AccountFactory {
    @Override
    public Account createAccount(String accountNumber, double amount) throws AmountException {
        return new AccountEUR(accountNumber, amount);
    }
}
