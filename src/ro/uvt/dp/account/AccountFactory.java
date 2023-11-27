package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public interface AccountFactory {
    Account createAccount(String accountNumber, double amount) throws AmountException;
}