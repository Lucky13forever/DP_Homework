package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public class EconomyAccountDecorator extends AccountDecorator {

    private double discountRate;

    public EconomyAccountDecorator(Account account, double discountRate) throws AmountException {
        super(account);
        this.discountRate = discountRate;
    }

    @Override
    public double getTotalAmount() {
        // Applying discount rate to the total amount
        double baseAmount = super.getTotalAmount();
        return baseAmount * (1 - discountRate);
    }

    @Override
    public double getInterest() {
        return 0;
    }

    @Override
    public void transfer(Account c, double s) throws AmountException {

    }

    // Additional methods related to economy accounts can be added here
}