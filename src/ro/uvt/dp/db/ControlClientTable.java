package ro.uvt.dp.db;

import javax.swing.plaf.nimbus.State;
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

    public void removeClient(int id) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM bank WHERE id = '" + id + "'");
    }

    public void displayClients() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM client");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id"));
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getString("address"));
            System.out.println(resultSet.getString("date_of_birth"));
        }
    }
}
