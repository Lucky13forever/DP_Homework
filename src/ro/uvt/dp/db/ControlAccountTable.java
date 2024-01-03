package ro.uvt.dp.db;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.account.EURAccountFactory;
import ro.uvt.dp.account.RONAccountFactory;
import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.decorator.ChildrenAccountDecorator;
import ro.uvt.dp.decorator.EconomyAccountDecorator;
import ro.uvt.dp.decorator.LifeInsuranceDecorator;
import ro.uvt.dp.decorator.NormalAccountDecorator;
import ro.uvt.dp.exceptions.AmountException;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControlAccountTable {
    Connection connection;
    ControlClientTable controlClientTable;
    ControlBankTable controlBankTable;

    public ControlAccountTable(Connection connection) {
        this.connection = connection;
        controlClientTable = new ControlClientTable(connection);
        controlBankTable = new ControlBankTable(connection);
    }

    public void addAccounts(Bank bank) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        int bank_id = controlBankTable.getBankId(bank.getName());
        for (Client client : bank.getClients()) {
            int client_id = controlClientTable.getClientId(client);
            for (Account account : client.getAccounts()) {
                String query = String.format("INSERT INTO account (id, bank_id, client_id, name, type, fiat, amount, blocked, children_bonus, economy_months, insurance_fee) VALUES (NULL, '%d', '%d', '%s', '%s', '%s', '%f', '%d', '%d', '%d', '%d', '%f')",
                        bank_id,
                        client_id,
                        account.getAccountNumber(),
                        account.getType(),
                        account.getFiat(),
                        account.getAmount(),
                        account.isBlocked() ? 1 : 0,
                        account.getType() == "Children" ? ((ChildrenAccountDecorator) account).getEducationalBonus() : 0,
                        account.getType() == "Economy" ? ((EconomyAccountDecorator) account).getMonths() : 0,
                        account.getType() == "Insurance" ? ((LifeInsuranceDecorator) account).getInsuranceFee() : 0
                );
                statement.executeUpdate(query);
            }
        }
    }

    public void addAccount(Account first) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO account (id, bank_id, client_id, name, type, fiat, amount, blocked, children_bonus, economy_months, insurance_fee) VALUES (NULL, '%d', '%d', '%s', '%s', '%s', '%f', '%d', '%d', '%d', '%f')",
                first.getBank_id(),
                first.getClient_id(),
                first.getAccountNumber(),
                first.getType(),
                first.getFiat(),
                first.getAmount(),
                first.isBlocked() ? 1 : 0,
                first.getType() == "Children" ? ((ChildrenAccountDecorator) first).getEducationalBonus() : 0,
                first.getType() == "Economy" ? ((EconomyAccountDecorator) first).getMonths() : 0,
                first.getType() == "Insurance" ? ((LifeInsuranceDecorator) first).getInsuranceFee() : 0
        );
        statement.executeUpdate(query);
    }

    public void updateAccount(Account first) throws SQLException {
        if (first.getId() == 0) {
            addAccount(first);
        } else {

            Statement statement = connection.createStatement();
            String query = String.format("UPDATE account SET amount = '%f', blocked = '%d' WHERE id = '%d'",
                    first.getAmount(),
                    first.isBlocked() ? 1 : 0,
                    first.getId()
            );
            statement.executeUpdate(query);

            if (first instanceof ChildrenAccountDecorator) {
                query = String.format("UPDATE account SET children_bonus = '%d' WHERE id = '%d'",
                        ((ChildrenAccountDecorator) first).getEducationalBonus(),
                        first.getId()
                );
                statement.executeUpdate(query);
            } else if (first instanceof EconomyAccountDecorator) {
                query = String.format("UPDATE account SET economy_months = '%d' WHERE id = '%d'",
                        ((EconomyAccountDecorator) first).getMonths(),
                        first.getId()
                );
                statement.executeUpdate(query);
            } else if (first instanceof LifeInsuranceDecorator) {
                query = String.format("UPDATE account SET insurance_fee = '%d' WHERE id = '%d'",
                        ((LifeInsuranceDecorator) first).getInsuranceFee(),
                        first.getId()
                );
                statement.executeUpdate(query);
            }

        }
    }

    public Account getAccount(int i) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM account WHERE id = '%d'", i);
        ResultSet resultSet = statement.executeQuery(query);
        Account detailed = null;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int bank_id = resultSet.getInt("bank_id");
            int client_id = resultSet.getInt("client_id");
            String name = resultSet.getString("name");
            String type = resultSet.getString("type");
            String fiat = resultSet.getString("fiat");
            int amount = resultSet.getInt("amount");
            boolean blocked = resultSet.getBoolean("blocked");
            int children_bonus = resultSet.getInt("children_bonus");
            int economony_months = resultSet.getInt("economy_months");
            int insurance_fee = resultSet.getInt("insurance_fee");


            Account factory = null;
            switch (fiat) {
                case "EUR":
                    factory = new EURAccountFactory().createAccount(id, bank_id, client_id, name, type, fiat, amount, blocked);
                    break;
                case "RON":
                    factory = new RONAccountFactory().createAccount(id, bank_id, client_id, name, type, fiat, amount, blocked);
                    break;
            }
            switch (type) {
                case "Normal":
                    detailed = new NormalAccountDecorator(factory);
                    break;
                case "Children":
                    detailed = new ChildrenAccountDecorator(factory, children_bonus);
                    break;
                case "Economy":
                    detailed = new EconomyAccountDecorator(factory, economony_months);
                    break;
                case "Insurance":
                    detailed = new LifeInsuranceDecorator(factory, insurance_fee);
                    break;
            }
        }
        return detailed;
    }

}
