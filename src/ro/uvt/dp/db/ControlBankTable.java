package ro.uvt.dp.db;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.exceptions.AmountException;
import ro.uvt.dp.exceptions.BankNotFound;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControlBankTable {
    Connection connection;

    public ControlBankTable(Connection connection) {
        this.connection = connection;
    }

    public void addBank(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO bank (id, code) VALUES (NULL, '" + name + "')");
    }

    public void addBank(Bank bank) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO bank (id, code) VALUES (NULL, '" + bank.getName() + "')");

        int bank_id = returnId(bank.getName());
        bank.setID(bank_id);
        updateBank(bank);
    }

    public void removeAccounts(int id) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM account WHERE bank_id = '" + id + "'");
    }

    public void removeBank(String name) throws SQLException {
        int bank_id = returnId(name);

        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM bank WHERE code = '" + name + "'");

        removeAccounts(bank_id);
    }

    public int returnId(String name) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT id FROM bank WHERE code = '" + name + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt("id");
    }

    public void displayBanks() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM bank");
        while (resultSet.next()) {
            System.out.println();
            System.out.print(resultSet.getString("id"));
            System.out.print(". ");
            System.out.print(resultSet.getString("code"));
        }
    }

    public Bank getBank(String name) throws SQLException, BankNotFound, AmountException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM bank WHERE code = '" + name + "'");
        Bank bank = null;
        while (resultSet.next()) {
            bank = new Bank(resultSet.getString("code"));
            bank.setID(resultSet.getInt("id"));
        }
        if (bank == null){
            throw new BankNotFound("Bank not found");
        }

        addClients(bank);

        return bank;
    }

    public void addClients(Bank bank) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT client_id FROM account WHERE bank_id = '" + bank.getID() + "'");
        while (resultSet.next()) {
            bank.addClient(new ControlClientTable(connection).getClient(resultSet.getInt("client_id")));
        }
    }

    public Bank getBank(int id) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM bank WHERE id = '" + id + "'");
        Bank bank = null;
        while (resultSet.next()) {
            bank = new Bank(resultSet.getString("code"));
        }
        addClients(bank);
        return bank;
    }

    public int getBankId(String name) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM bank WHERE code = '" + name + "'");
        resultSet.next();
        return resultSet.getInt("id");
    }

    public void renameBank(Bank bank, String new_name) throws SQLException {
        Statement statement = connection.createStatement();
        int id = bank.getID();
        statement.executeUpdate("UPDATE bank SET code = '" + new_name + "' WHERE id = '" + id + "'");
    }

    public void updateBank(Bank bank) throws SQLException, AmountException {

        for (Client client : bank.getClients()) {
            for (Account account : client.getAccounts()) {
                account.setBank_id(bank.getID());
            }
            new ControlClientTable(connection).updateClient(client);
        }
    }
}
