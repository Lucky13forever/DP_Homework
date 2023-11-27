package ro.uvt.dp.bank;

import ro.uvt.dp.client.Client;

public class AddClientCommand implements Command{
    private Bank bank;
    private Client client;

    public AddClientCommand(Bank bank, Client client) {
        this.bank = bank;
        this.client = client;
    }

    @Override
    public void execute() {
        bank.addClient(client);
    }
}
