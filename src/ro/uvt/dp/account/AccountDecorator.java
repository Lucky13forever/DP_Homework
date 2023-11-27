package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public abstract class AccountDecorator extends Account {
    protected Account decoratedAccount;

    public AccountDecorator(Account account) throws AmountException {
        super(account.getAccountNumber(), account.getAmount());
        this.decoratedAccount = account;
    }

    @Override
    public double getTotalAmount() {
        return decoratedAccount.getTotalAmount();
    }
}
