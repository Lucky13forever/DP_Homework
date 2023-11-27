import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import ro.uvt.dp.account.Account;
import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.exceptions.ClientNotFound;

public class BankTest {

    private Bank bank;

    @Before
    public void setUp() {
        bank = new Bank("B123");
    }

    @Test
    public void testAddClient() throws AmountException {
        Client client = new Client("John", "123 Main St", Account.TYPE.EUR, "EUR123", 500.0);
        bank.addClient(client);
        assertEquals(1, bank.getClients().size());
        assertEquals("John", bank.getClients().get(0).getName());
    }

    @Test
    public void testGetClient() throws AmountException {
        Client client = new Client("Alice", "456 Elm St", Account.TYPE.RON, "RON456", 300.0);
        bank.addClient(client);
        Client foundClient = bank.getClient("Alice");
        assertEquals("Alice", foundClient.getName());
    }

    @Test
    public void testRemoveAccount() throws AmountException, ClientNotFound {
        Client client = new Client("Emanuel", "123 Main St", Account.TYPE.EUR, "EUR789", 500.0);
        bank.addClient(client);
        bank.removeClient("Emanuel");
        assertEquals(0, bank.getClients().size());
        try {
            bank.removeClient("Emanuel");
        }
        catch (ClientNotFound e) {
            assertEquals("Client not found", e.getMessage());
        }
    }
}
