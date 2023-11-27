package ro.uvt.dp.mediator;

import ro.uvt.dp.client.Client;

public class BankColleague extends Colleague {

    public BankColleague(BankingMediator mediator, Client client) {
        super(mediator, client);
    }

    @Override
    public void send(String message, Colleague receiver) {
        mediator.sendMessage(message, this, receiver);
    }

    @Override
    public void receive(String message, Colleague sender) {
        // Bank receives a message
        System.out.println("Bank " + client.getName() + " received a message: " + message + " from " + sender.getName());
    }
}