package ro.uvt.dp.decorator;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.exceptions.AmountException;

public class ChildrenAccountDecorator extends AccountDecorator {

    private double educationalBonus;

    public ChildrenAccountDecorator(Account account, double educationalBonus) throws AmountException {
        super(account);
        this.educationalBonus = educationalBonus;
        this.type = "Children";
    }

    @Override
    public double getTotalAmount() {
        // Assuming the educational bonus is added to the account
        double baseAmount = super.getTotalAmount();
        return baseAmount + educationalBonus;
    }

    public int getEducationalBonus() {
        return (int) educationalBonus;
    }

    public void setEducationalBonus(double educationalBonus) {
        this.educationalBonus = educationalBonus;
    }

    @Override
    public double getInterest() {
        return 0;
    }

    @Override
    public void transfer(Account c, double s) throws AmountException {

    }

    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + " + ChildrenAccountDecorator{" +
                "educationalBonus=" + educationalBonus +
                '}';
    }

    // Additional methods related to children accounts can be added here
}