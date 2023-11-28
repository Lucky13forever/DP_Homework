package ro.uvt.dp.mediator;

import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;

import java.util.ArrayList;
import java.util.List;

public abstract class Colleague {

    private List<String> messages = new ArrayList<>();
    protected BankingMediator mediator;

    public Colleague(BankingMediator mediator) {
        this.mediator = mediator;
    }

    public abstract void send(String message, Colleague receiver);
    public abstract void receive(String message, Colleague sender);

    protected void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getName() {
    	return null;
    }
}
