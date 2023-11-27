import org.junit.Test;
import ro.uvt.dp.account.Account;
import ro.uvt.dp.account.AccountEUR;
import ro.uvt.dp.account.EconomyAccountDecorator;
import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.logger.Logger;

import static org.junit.Assert.*;

public class DecoratorsTest {


    @Test
    public void EconomyDecorator() throws AmountException {
        // Get two instances of the logger
        Account myAccount = new AccountEUR("RO123456789", 1000);
        EconomyAccountDecorator myEconomyAccount = new EconomyAccountDecorator(myAccount, 30);

        assertEquals(202, myEconomyAccount.get_roi(), 0);
    }
}
