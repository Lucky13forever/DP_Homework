import org.junit.Test;
import ro.uvt.dp.account.AccountFactory;
import ro.uvt.dp.account.EURAccountFactory;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.mediator.BankColleague;
import ro.uvt.dp.mediator.BankMediatorImpl;
import ro.uvt.dp.mediator.ClientColleague;
import ro.uvt.dp.mediator.Colleague;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MediatorTest {


    @Test
    public void whenClientSendsMessageToClient_thenRecipientReceivesMessage() throws AmountException {
        BankMediatorImpl mediator = new BankMediatorImpl();
        AccountFactory EURFactory = new EURAccountFactory();
        Client client1 = new Client.Builder("John Doe", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();
        Client client2 = new Client.Builder("John Doe", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();
        Colleague coleg_1 = new ClientColleague(mediator, client1);
        Colleague coleg_2 = new ClientColleague(mediator, client2);

        mediator.addColleague(client1);
        mediator.addColleague(client2);

        coleg_1.send("Hello World!", coleg_2);
    }
}