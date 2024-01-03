package ro.uvt.dp.db;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.account.EURAccountFactory;
import ro.uvt.dp.account.RONAccountFactory;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.decorator.ChildrenAccountDecorator;
import ro.uvt.dp.decorator.EconomyAccountDecorator;
import ro.uvt.dp.decorator.LifeInsuranceDecorator;
import ro.uvt.dp.decorator.NormalAccountDecorator;
import ro.uvt.dp.exceptions.AmountException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControlClientTable {
    Connection connection;

    public ControlClientTable(Connection connection) {
        this.connection = connection;
    }

    public void addClient(String name, String address, String date_of_birth) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO client (id, name, address, date_of_birth) VALUES (NULL, '" + name + "', '" + address + "', '" + date_of_birth +"') ");
    }

    public void addClient(Client client) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO client (id, name, address, date_of_birth) VALUES (NULL, '" + client.getName() + "', '" + client.getAddress() + "', '" + client.getDateOfBirth() +"') ");

        int client_id = getClientId(client);
        for (Account account : client.getAccounts())
        {
            account.setClient_id(client_id);
        }
    }

    public void removeClient(int id) throws SQLException {


        Statement statement = connection.createStatement();
        String query = "DELETE FROM client WHERE id = '" + id + "'";
        statement.executeUpdate(query);

        removeAccounts(id);
    }

    public void removeAccounts(int id) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM account WHERE client_id = '" + id + "'");
    }

    public void getAccounts(Client client, int user_id) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE client_id = '" + user_id + "'");
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
            Account detailed = null;
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
            client.addAccount(detailed);
        }
    }

    public Client getClient(int id) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE id = '" + id + "'");
        resultSet.next();
        Client nou = new Client.Builder()
                .name(resultSet.getString("name"))
                .address(resultSet.getString("address"))
                .dateOfBirth(resultSet.getString("date_of_birth"))
                .id(resultSet.getInt("id"))
                .build();

        getAccounts(nou, nou.getId());
        return nou;
    }

    public int getClientId(Client client) throws SQLException, AmountException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE name = '" + client.getName() + "'" +
                " AND address = '" + client.getAddress() + "'" +
                " AND date_of_birth = '" + client.getDateOfBirth() + "'");
        resultSet.next();
        return resultSet.getInt("id");
    }

    public void displayClients(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ". " + resultSet.getString("name") + ", " + resultSet.getString("address") + ", " + resultSet.getString("date_of_birth"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editClient(Client client) throws SQLException {
        int client_id = client.getId();
        Statement statement = connection.createStatement();
        String query =  "UPDATE client SET name = '" + client.getName() + "', address = '" + client.getAddress() + "', date_of_birth = '" + client.getDateOfBirth() + "' WHERE id = '" + client_id + "'";
        statement.executeUpdate(query);
    }

    public void updateClient(Client client) throws SQLException, AmountException {
        if (client.getId() == 0) {
            addClient(client);
        } else {
            editClient(client);
        }

        ControlAccountTable controlAccountTable = new ControlAccountTable(connection);
        for (Account account : client.getAccounts()) {
            controlAccountTable.updateAccount(account);
        }
    }
}
