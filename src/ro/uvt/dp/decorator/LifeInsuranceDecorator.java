package ro.uvt.dp.decorator;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.exceptions.AmountException;

public class LifeInsuranceDecorator extends AccountDecorator {

    private double insuranceFee;

    public LifeInsuranceDecorator(Account account, double insuranceFee) throws AmountException {
        super(account);
        this.insuranceFee = insuranceFee;
        this.type = "Insurance";
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

    public void setInsuranceFee(double insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public double getInsuranceFee() {
        return insuranceFee;
    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + " + LifeInsuranceDecorator{" +
                "insuranceFee=" + insuranceFee +
                '}';
    }

    // Additional methods related to life insurance can be added here
}
