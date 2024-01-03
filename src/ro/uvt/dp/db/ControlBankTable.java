package ro.uvt.dp.db;

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

    public void removeBank(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM bank WHERE code = '" + name + "'");
    }

    public int returnId(String name) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM bank WHERE code = '" + name + "'");
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("id");
        }
        return result;
    }

    public void displayBanks() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM bank");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id"));
            System.out.print(". ");
            System.out.print(resultSet.getString("code"));
        }
    }
}
