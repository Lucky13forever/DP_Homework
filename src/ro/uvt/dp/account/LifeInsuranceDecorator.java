package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public class LifeInsuranceDecorator extends AccountDecorator {

    private double insuranceFee;

    public LifeInsuranceDecorator(Account account, double insuranceFee) throws AmountException {
        super(account);
        this.insuranceFee = insuranceFee;
    }

    @Override
    public double getTotalAmount() {
        // Assuming the insurance fee is deducted monthly from the account
        double baseAmount = super.getTotalAmount();
        return baseAmount - insuranceFee;
    }

    @Override
    public double getInterest() {
        return 0;
    }

    @Override
    public void transfer(Account c, double s) throws AmountException {

    }

    // Additional methods related to life insurance can be added here
}
