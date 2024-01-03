package ro.uvt.dp;

import ro.uvt.dp.db.ControlBankTable;
import ro.uvt.dp.db.ControlClientTable;

import java.sql.*;

public class program {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dp_bank",
                "root",
                ""
        );

//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM client");
//
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("id"));
//            System.out.println(resultSet.getString("name"));
//            System.out.println(resultSet.getString("age"));
//            System.out.println(resultSet.getString("random"));
//        }
//        controlBankTable.addBank("ING Bank");

//        ControlClientTable controlClientTable = new ControlClientTable(connection);

//        controlClientTable.addClient("Ion", "Strada", "1999-01-01");

          ControlBankTable controlBankTable = new ControlBankTable(connection);
          controlBankTable.displayBanks();
    }


}
