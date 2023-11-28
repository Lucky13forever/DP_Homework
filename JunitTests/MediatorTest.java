import org.junit.Test;
import ro.uvt.dp.account.AccountFactory;
import ro.uvt.dp.account.EURAccountFactory;
import ro.uvt.dp.bank.Bank;
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

        coleg_1.send("Hello World!", coleg_2);

        assertEquals(1, coleg_2.getMessages().size());
        assertEquals("Hello World!", coleg_2.getMessages().get(0));
    }

    @Test
    public void whenBankSendsMessageToClient_thenRecipientReceivesMessage() throws AmountException {
        BankMediatorImpl mediator = new BankMediatorImpl();
        AccountFactory EURFactory = new EURAccountFactory();
        Bank bank = new Bank("B123");
        Client client = new Client.Builder("John Doe", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();
        Colleague coleg_1 = new BankColleague(mediator, bank);
        Colleague coleg_2 = new ClientColleague(mediator, client);

        coleg_1.send("You are valid for a credit card Sir", coleg_2);

        assertEquals(1, coleg_2.getMessages().size());
        assertEquals("You are valid for a credit card Sir", coleg_2.getMessages().get(0));
    }

    @Test
    public void whenBankSendsMessageToClients_thenRecipientReceivesMessage() throws AmountException {
        BankMediatorImpl mediator = new BankMediatorImpl();
        AccountFactory EURFactory = new EURAccountFactory();
        Bank bank = new Bank("B123");
        Client client_1 = new Client.Builder("Lugojan Emanuel", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();
        Client client_2 = new Client.Builder("Fofiu George", "123 Main St")
                .dateOfBirth("1990-01-01")
                .accountFactory(EURFactory)
                .account("EUR456", 500.0)
                .build();
        Colleague coleg_1 = new BankColleague(mediator, bank);
        Colleague coleg_2 = new ClientColleague(mediator, client_1);
        Colleague coleg_3 = new ClientColleague(mediator, client_2);

        coleg_1.send("You are valid for a credit card Sir", null);

        assertEquals(1, coleg_2.getMessages().size());
        assertEquals(1, coleg_3.getMessages().size());
        assertEquals("You are valid for a credit card Sir", coleg_2.getMessages().get(0));
        assertEquals("You are valid for a credit card Sir", coleg_3.getMessages().get(0));
    }
}