package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

abstract class DatabaseConnection {
    static Connection connection;
    static Statement statement;
    private static final String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
                    + "database=spesaonline;" //nome db su mssql
                    + "user=SA;" //username di mssql
                    + "password=_secret_1234;" //vostra password al mssql
                    + "loginTimeout=30;";

    DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}