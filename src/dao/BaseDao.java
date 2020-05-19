package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class BaseDao {
    //TODO: for every DAO class: print db status (executing query, returned x rows, etc.)
    static Connection connection = createConnection();
    private static final String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
                    + "database=spesaonline;" //nome db su mssql
                    + "user=SA;" //username di mssql
                    + "password=_secret_1234;" //vostra password al mssql
                    + "loginTimeout=30;";

    protected static Connection createConnection() {
        try {
            System.out.print("Connecting to database...");
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            System.err.println("Error while connecting to database.");
            e.printStackTrace();
        }
        return connection;
    }
}