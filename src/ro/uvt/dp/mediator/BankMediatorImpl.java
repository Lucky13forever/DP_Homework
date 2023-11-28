package ro.uvt.dp.mediator;

import ro.uvt.dp.client.Client;

import java.util.ArrayList;
import java.util.List;

public class BankMediatorImpl implements BankingMediator {
    private List<Colleague> colleagues;

    public BankMediatorImpl() {
        this.colleagues = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message, Colleague sender, Colleague receiver) {
        // Logic to send the message from sender to receiver
        if (receiver != null) {
            receiver.receive(message, sender);
            receiver.addMessage(message);
        } else {
            // Broadcast to all colleagues except the sender
            for (Colleague colleague : colleagues) {
                if (colleague != sender) {
                    colleague.receive(message, sender);
                    colleague.addMessage(message);
                }
            }
        }
    }

    @Override
    public void addColleague(Client client) {
        colleagues.add(new ClientColleague(this, client));
    }

    @Override
    public void addColleague(Colleague colleague) {
        colleagues.add(colleague);
    }
}