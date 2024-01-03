package ro.uvt.dp;

import ro.uvt.dp.account.*;
import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.db.ControlAccountTable;
import ro.uvt.dp.db.ControlBankTable;
import ro.uvt.dp.db.ControlClientTable;
import ro.uvt.dp.decorator.EconomyAccountDecorator;
import ro.uvt.dp.decorator.LifeInsuranceDecorator;
import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.exceptions.BankNotFound;

import java.sql.*;
import java.text.MessageFormat;

public class program {
    public static void main(String[] args) throws SQLException, AmountException, BankNotFound {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dp_bank",
                "root",
                ""
        );

        ControlBankTable controlBankTable = new ControlBankTable(connection);
        ControlAccountTable controlAccountTable = new ControlAccountTable(connection);
        ControlClientTable controlClientTable = new ControlClientTable(connection);

//         --------------------------------- ADD BANK ---------------------------------
//        Bank bank = new Bank("ING");
//        controlBankTable.addBank(bank);


//        --------------------------------- EDIT BANK ---------------------------------
//        Bank bank = controlBankTable.getBank("TBI");
//        controlBankTable.renameBank(bank, "TBI Bank");


//       --------------------------------- REMOVE BANK ---------------------------------
//        controlBankTable.removeBank("ING BANK");

//        --------------------------------- SEE ALL BANKS ---------------------------------
//        controlBankTable.displayBanks();

//        --------------------------------- GET CLIENTS FROM BANK ---------------------------------

//        Bank bank = controlBankTable.getBank("BT");
//        for (Client client : bank.getClients()) {
//            System.out.println(client.toString());
//        }




//        --------------------------------- ADD CLIENT ---------------------------------

//        Client client = new Client.Builder()
//                .name("Mihai")
//                .address("Str. Mihai Viteazu, nr. 1")
//                .dateOfBirth("1990-01-01")
//                .build();
//
//        controlClientTable.addClient(client);


//        --------------------------------- SEE ALL CLIENTS ---------------------------------
//        controlClientTable.displayClients();


//        --------------------------------- EDIT CLIENT ---------------------------------
//        Client client = controlClientTable.getClient(9);
//        client.setName("Mihai Alexandru");
//        client.setAddress("Str. Mihai Viteazu, nr. 2");
//        client.setBirth("1990-01-02");
//        controlClientTable.editClient(client);


//        --------------------------------- REMOVE CLIENT ---------------------------------
//        controlClientTable.removeClient(7);

//        Client client = controlClientTable.getClient(6);
//        EconomyAccountDecorator first = (EconomyAccountDecorator) client.getAccounts().get(0);

//        first.depose(100);

//        first.setMonths(32);

//        --------------------------------- UPDATE ACCOUNT ---------------------------------

//        EconomyAccountDecorator first = (EconomyAccountDecorator) controlAccountTable.getAccount(7);
//
//        System.out.println(first.toString());
//
//        first.setMonths(34);
//        controlAccountTable.updateAccount(first);

//   --------------------------------- ADD BANK CLIENT AND ACCOUNT --------------------------------


        Bank bank = new Bank("DNG");

        Client client = new Client.Builder()
                .name("Ionut")
                .address("Demetriade Nr. 19")
                .dateOfBirth("1998-07-01")
                .build();

        Account simple = new LifeInsuranceDecorator(new EURAccountFactory().createAccount("12g3b1nasdh", 1000), 10.1);


        System.out.println(simple.toString());
        client.addAccount(simple);

        bank.addClient(client);

        controlBankTable.addBank(bank);

    }
}
