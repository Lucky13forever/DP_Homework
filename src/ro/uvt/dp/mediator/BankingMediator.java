package ro.uvt.dp.mediator;

import ro.uvt.dp.client.Client;

public interface BankingMediator {
    void sendMessage(String message, Colleague sender, Colleague receiver);
    void addColleague(Client client);

    void addColleague(Colleague colleague);
}