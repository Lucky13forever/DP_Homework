package ro.uvt.dp.mediator;

import ro.uvt.dp.bank.Bank;

public class BankColleague extends Colleague {

    private Bank bank;

    public BankColleague(BankingMediator mediator, Bank bank) {
        super(mediator);
        mediator.addColleague(this);
        this.bank = bank;
    }

    @Override
    public void send(String message, Colleague receiver) {
        mediator.sendMessage(message, this, receiver);
    }

    @Override
    public void receive(String message, Colleague sender) {
        // Bank receives a message
        System.out.println("Bank " + bank.getName() + " received a message: " + message + " from " + sender.getName());
    }

    public String getName(){
        return bank.getName();
    }
}