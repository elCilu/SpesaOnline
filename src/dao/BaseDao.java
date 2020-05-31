package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class BaseDao {
    //TODO: for every DAO class: print db status (executing query, returned x rows, etc.)
    static Connection connection = createConnection();
    private static final String connectionUrl =
            "jdbc:jtds:sqlserver://LAPTOP-BNV4BFEE/ProgettoSpesa;instance=MSSQLSERVER";

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