import org.junit.Test;
import ro.uvt.dp.account.Account;
import ro.uvt.dp.account.AccountEUR;
import ro.uvt.dp.decorator.EconomyAccountDecorator;
import ro.uvt.dp.exceptions.AmountException;

import static org.junit.Assert.*;

public class DecoratorsTest {


    @Test
    public void EconomyDecorator() throws AmountException {
        // Get two instances of the logger
        Account myAccount = new AccountEUR("RO123456789", 1000);
        EconomyAccountDecorator myEconomyAccount = new EconomyAccountDecorator(myAccount, 30);

        assertEquals(202, myEconomyAccount.get_roi(), 0);
    }

    @Test
    public void EconomyDecorator_for_1_year() throws AmountException {
        // Get two instances of the logger
        Account myAccount = new AccountEUR("RO123456789", 1000);
        EconomyAccountDecorator myEconomyAccount = new EconomyAccountDecorator(myAccount, 12);

        assertEquals(90, myEconomyAccount.get_roi(), 0);
    }

    @Test
    public void EconomyDecorator_for_6_months() throws AmountException {
        // Get two instances of the logger
        Account myAccount = new AccountEUR("RO123456789", 1000);
        EconomyAccountDecorator myEconomyAccount = new EconomyAccountDecorator(myAccount, 6);

        assertEquals(20, myEconomyAccount.get_roi(), 0);
    }

    @Test
    public void EconomyDecorator_for_3_months() throws AmountException {
        // Get two instances of the logger
        Account myAccount = new AccountEUR("RO123456789", 1000);
        EconomyAccountDecorator myEconomyAccount = new EconomyAccountDecorator(myAccount, 3);

        assertEquals(10, myEconomyAccount.get_roi(), 0);
    }

    @Test
    public void EconomyDecorator_for_2_months() throws AmountException {
        // Get two instances of the logger
        Account myAccount = new AccountEUR("RO123456789", 1000);
        EconomyAccountDecorator myEconomyAccount = new EconomyAccountDecorator(myAccount, 2);

        assertEquals(4, myEconomyAccount.get_roi(), 0);
    }
}
