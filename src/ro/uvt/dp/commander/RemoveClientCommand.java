package ro.uvt.dp.commander;

import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.exceptions.ClientNotFound;

public class RemoveClientCommand implements Command {

    private Bank bank;
    private Client client;

    public RemoveClientCommand(Bank bank, Client client) {
        this.bank = bank;
        this.client = client;
    }

    @Override
    public void execute() throws ClientNotFound {
        bank.removeClient(client.getName());
    }
}
