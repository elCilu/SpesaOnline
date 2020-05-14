package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=spesaonline;" //nome db su mssql
                        + "user=SA;" //username di mssql
                        + "password=_secret_1234;" //vostra password al mssql
                        + "loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("You did it!");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            System.out.println("You failed it :(");
            e.printStackTrace();
        }
    }
}
