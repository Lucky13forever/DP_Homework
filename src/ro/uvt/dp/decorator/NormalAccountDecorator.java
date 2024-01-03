package ro.uvt.dp.decorator;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.exceptions.AmountException;

public class NormalAccountDecorator extends AccountDecorator{

    @Override
    public double getInterest() {
        return 0;
    }

    public String getType(){
        return type;
    }

    public NormalAccountDecorator(Account account) throws AmountException {
        super(account);
        this.type = "Normal";
    }

    @Override
    public void transfer(Account c, double s) throws AmountException {

    }
}
