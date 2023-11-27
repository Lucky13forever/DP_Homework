package ro.uvt.dp.account;

import ro.uvt.dp.exceptions.AmountException;

public class EconomyAccountDecorator extends AccountDecorator {

    private int months;
    private double interestRate;
    public EconomyAccountDecorator(Account account, int months) throws AmountException {
        super(account);
        this.months = months;
        // 1 month 0.2% , 3 months - 1%, 6 months - 3%, 12 months - 7%
        int for_12 = months / 12;
        int left = months - for_12 * 12;
        int for_6 = (months - left) / 6;
        left = left - for_6 * 6;
        int for_3 = left / 3;
        left = left - for_3 * 3;
        int for_1 = left;
        interestRate = for_12 * 7 + for_6 * 3 + for_3 * 1 + for_1 * 0.2;
    }

    @Override
    public double getTotalAmount() {
        // Applying discount rate to the total amount
        return super.getTotalAmount();
    }

    @Override
    public double getInterest() {
        return 0;
    }

    @Override
    public void transfer(Account c, double s) throws AmountException {

    }

    public int get_roi() {
        // Calculate the return on investment
        return (int) (getTotalAmount() * (interestRate / 100));
    }

}