package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public class ChildrenAccountDecorator extends AccountDecorator {

    private double educationalBonus;

    public ChildrenAccountDecorator(Account account, double educationalBonus) throws AmountException {
        super(account);
        this.educationalBonus = educationalBonus;
    }

    @Override
    public double getTotalAmount() {
        // Assuming the educational bonus is added to the account
        double baseAmount = super.getTotalAmount();
        return baseAmount + educationalBonus;
    }

    @Override
    public double getInterest() {
        return 0;
    }

    @Override
    public void transfer(Account c, double s) throws AmountException {

    }

    // Additional methods related to children accounts can be added here
}