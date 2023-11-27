package ro.uvt.dp.mediator;

import ro.uvt.dp.client.Client;

public abstract class Colleague {
    protected BankingMediator mediator;
    protected Client client;

    public Colleague(BankingMediator mediator, Client client) {
        this.mediator = mediator;
        this.client = client;
    }



    public abstract void send(String message, Colleague receiver);
    public abstract void receive(String message, Colleague sender);

    protected String getName() {
        return client.getName();
    }
}
