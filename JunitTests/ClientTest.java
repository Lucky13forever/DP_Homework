import org.junit.Before;
import org.junit.Test;
import ro.uvt.dp.account.*;
import ro.uvt.dp.account.Account.TYPE;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.exceptions.AccountNotFound;
import ro.uvt.dp.exceptions.AmountException;

import static org.junit.Assert.*;

public class ClientTest {

    private Client client;

    @Before
    public void setUp() throws AmountException {
        client = new Client("Bob", "789 Oak St", TYPE.RON, "RON789", 1000.0);
    }

    @Test
    public void testAddAccount() throws AmountException {
        client.addAccount(TYPE.EUR, "EUR123", 500.0);
        assertEquals(2, client.getAccounts().size());
    }

    @Test
    public void testGetAccount() {
        Account account = client.getAccount("RON789");
        assertNotNull(account);
    }

    @Test
    public void testCloseAccount() throws AccountNotFound {
        client.closeAccount("RON789");
        assertEquals(0, client.getAccounts().size());
        try {
            client.closeAccount("RON789");
        } catch (AccountNotFound e) {
            assertEquals("Account not found", e.getMessage());
        }
    }

    @Test
    public void testClientBuilder() {
        try {
            // Create an account factory (replace this with your actual implementation)
            AccountFactory RONFactory = new RONAccountFactory();
            AccountFactory EURFactory = new EURAccountFactory();

            // Use the builder to create a client
            Client client = new Client.Builder("John Doe", "123 Main St")
                    .dateOfBirth("1990-01-01")
                    .accountFactory(RONFactory)
                    .account("RON123", 1000.0)
                    .accountFactory(EURFactory)
                    .account("EUR456", 500.0)
                    .build();

            // Check if the client is created correctly
            assertEquals("John Doe", client.getName());
            assertEquals("123 Main St", client.getAddress());
            assertEquals("1990-01-01", client.getDateOfBirth());

            // Check if accounts are added correctly
            assertEquals(2, client.getAccounts().size());

            // Check if the accounts have the correct amounts
            for (Account account : client.getAccounts()) {
                if (account.getAccountNumber().equals("EUR123")) {
                    assertEquals(1000.0, account.getAmount(), 0.001);
                } else if (account.getAccountNumber().equals("RON456")) {
                    assertEquals(500.0, account.getAmount(), 0.001);
                }
            }

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
