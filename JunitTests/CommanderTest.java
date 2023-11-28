import org.junit.Test;
import ro.uvt.dp.account.AccountFactory;
import ro.uvt.dp.account.EURAccountFactory;
import ro.uvt.dp.commander.AddClientCommand;
import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.commander.RemoveClientCommand;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.exceptions.ClientNotFound;

import static org.junit.Assert.assertEquals;

public class CommanderTest {

    @Test
    public void addClient() throws AmountException {
        Bank bank = new Bank("B123");
        AccountFactory EURFactory = new EURAccountFactory();
        Client client = new Client.Builder("John Doe", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();

        AddClientCommand addClientCommand = new AddClientCommand(bank, client);
        addClientCommand.execute();

        assertEquals(1, bank.getClients().size());
    }

    @Test
    public void RemoveClient_no_clients() throws AmountException {
        Bank bank = new Bank("B123");
        AccountFactory EURFactory = new EURAccountFactory();
        Client client = new Client.Builder("John Doe", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();

        RemoveClientCommand removeClientCommand = new RemoveClientCommand(bank, client);
        try {
            removeClientCommand.execute();
        }
        catch (ClientNotFound e) {
            assertEquals("Client not found", e.getMessage());
        }
    }

    @Test
    public void RemoveClient() throws AmountException, ClientNotFound {
        Bank bank = new Bank("B123");
        AccountFactory EURFactory = new EURAccountFactory();
        Client client = new Client.Builder("John Doe", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();

        bank.addClient(client);

        RemoveClientCommand removeClientCommand = new RemoveClientCommand(bank, client);
        removeClientCommand.execute();

        assertEquals(0, bank.getClients().size());
    }
}
