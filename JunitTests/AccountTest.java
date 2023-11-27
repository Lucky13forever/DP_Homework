import org.junit.Before;
import org.junit.Test;
import ro.uvt.dp.account.*;
import ro.uvt.dp.exceptions.AmountException;

import static org.junit.Assert.*;

public class AccountTest {

    private Account accountRON_1, accountRON_2;
    private Account accountEUR_1, accountEUR_2 ;

    @Before
    public void setUp() throws AmountException {
        accountRON_1 = new AccountRON("RON1", 1000);
        accountRON_2 = new AccountRON("RON2", 200);
        accountEUR_1 = new AccountEUR("EUR1", 1000);
        accountEUR_2 = new AccountEUR("EUR2", 200);
    }



    @Test
    public void testAccountRONInterest() {
        assertEquals("Interest for RON acccount with higher amount then 500 should be 0.08", 0.08, accountRON_1.getInterest(), 0.01);
        assertEquals("Interest for RON acccount with lower amount then 500 should be 0.03", 0.03, accountRON_2.getInterest(), 0.01);
    }

    @Test
    public void testAccountEURInterest() {
        assertEquals("Interest for EUR acccount should be 0.01", 0.01, accountEUR_1.getInterest(), 0.01);
    }

    @Test
    public void testDepose(){
        try {
            accountRON_1.depose(100);
        } catch (AmountException e) {
            assertTrue("Depose should not throw exception", false);
        }
        assertEquals("Depose should add the amount to the account", CustomAmount(1100, accountRON_1.getInterest()), accountRON_1.getTotalAmount(), 0.01);

        try {
            accountRON_1.depose(-100);
            assertTrue("Depose should throw exception", false);
        } catch (AmountException e) {
            assertTrue("Depose should throw exception", true);
        }
    }

    @Test
    public void testRetrieve(){
        try {
            accountEUR_1.retrieve(100);
        } catch (AmountException e) {
            assertTrue("Retrieve should not throw exception", false);
        }
        assertEquals("Retrieve should remove the amount from the account", CustomAmount(900, accountEUR_1.getInterest()), accountEUR_1.getTotalAmount(), 0.01);

        try {
            accountEUR_1.retrieve(10000);
            assertTrue("Retrieve should throw exception", false);
        } catch (AmountException e) {
            assertTrue("Retrieve should throw exception", true);
        }
    }

    public double CustomAmount(int x, double interest)
    {
        return x * (1 + interest);
    }

    @Test
    public void testRONTransfer(){
        try {
            accountRON_1.transfer(accountRON_2, 100);
        } catch (AmountException e) {
            assertTrue("Transfer should not throw exception", false);
        }
        assertEquals("Transfer should remove the amount from the source account", CustomAmount(100, accountRON_2.getInterest()), accountRON_2.getTotalAmount(), 0.01);
        assertEquals("Transfer should add the amount to the destination account", CustomAmount(1100, accountRON_1.getInterest()), accountRON_1.getTotalAmount(), 0.01);

        try {
            accountRON_1.transfer(accountRON_2, 10000);
            assertTrue("Transfer should throw exception", false);
        } catch (AmountException e) {
            assertTrue("Transfer should throw exception", true);
        }
    }

    @Test
    public void testEURTransfer(){
        try {
            accountEUR_1.transfer(accountEUR_2, 100);
        } catch (AmountException e) {
            assertTrue("Transfer should not throw exception", false);
        }
        assertEquals("Transfer should remove the amount from the source account", CustomAmount(100, accountEUR_2.getInterest()), accountEUR_2.getTotalAmount(), 0.01);
        assertEquals("Transfer should add the amount to the destination account", CustomAmount(1100, accountEUR_1.getInterest()), accountEUR_1.getTotalAmount(), 0.01);

        try {
            accountEUR_1.transfer(accountEUR_2, 10000);
            assertTrue("Transfer should throw exception", false);
        } catch (AmountException e) {
            assertTrue("Transfer should throw exception", true);
        }
    }

    @Test
    public void testAccountBlock(){
        accountRON_1.blockAccount();
        assertTrue("Account should be blocked", accountRON_1.isBlocked());
        try {
            accountRON_1.depose(100);
            assertTrue("Depose should throw exception", false);
        } catch (AmountException e) {
            assertTrue("Depose should throw exception", true);
        }
        try {
            accountRON_1.retrieve(100);
            assertTrue("Retrieve should throw exception", false);
        } catch (AmountException e) {
            assertTrue("Retrieve should throw exception", true);
        }
    }

    @Test
    public void testAccountUnblock(){
        accountRON_1.blockAccount();
        assertTrue("Account should be blocked", accountRON_1.isBlocked());
        accountRON_1.unblockAccount();
        assertTrue("Account should be unblocked", !accountRON_1.isBlocked());
        try {
            accountRON_1.depose(100);
        } catch (AmountException e) {
            assertTrue("Depose should not throw exception", false);
        }
        try {
            accountRON_1.retrieve(100);
        } catch (AmountException e) {
            assertTrue("Retrieve should not throw exception", false);
        }
    }

    @Test
    public void testAccountFactoryEUR() {
        try {
            // Create an account factory
            AccountFactory accountFactory = new EURAccountFactory();

            // Create an EUR account using the factory
            Account account = accountFactory.createAccount("EUR123", 1000.0);

            // Check if the account is of the correct type
            assertTrue(account instanceof AccountEUR);

            // Check if the account number is set correctly
            assertEquals("EUR123", account.getAccountNumber());

            // Check if the initial amount is set correctly
            assertEquals(1000.0, account.getAmount(), 0.001);

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testAccountFactoryRON() {
        try {
            // Create an account factory
            AccountFactory accountFactory = new RONAccountFactory();

            // Create a RON account using the factory
            Account account = accountFactory.createAccount("RON456", 500.0);

            // Check if the account is of the correct type
            assertTrue(account instanceof AccountRON);

            // Check if the account number is set correctly
            assertEquals("RON456", account.getAccountNumber());

            // Check if the initial amount is set correctly
            assertEquals(500.0, account.getAmount(), 0.001);

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }


}
