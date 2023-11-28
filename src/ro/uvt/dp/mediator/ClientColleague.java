package ro.uvt.dp.mediator;

import ro.uvt.dp.client.Client;

import java.util.Collection;

public class ClientColleague extends Colleague {

    private Client client;

    public ClientColleague(BankingMediator mediator, Client client) {
        super(mediator);
        mediator.addColleague(this);
        this.client = client;
    }

    @Override
    public void send(String message, Colleague receiver) {
        mediator.sendMessage(message, this, receiver);
    }

    @Override
    public void receive(String message, Colleague sender) {
        // Client receives a message
        System.out.println("Client " + client.getName() + " received a message: " + message + " from " + sender.getName());
    }

    public String getName() {
        return client.getName();
    }

}