package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class BaseDao {
    //TODO: for every DAO class: print db status (executing query, returned x rows, etc.)
    static Connection connection = createConnection();
    private static final String connectionUrl =
            "jdbc:jtds:sqlserver://LAPTOP-BNV4BFEE/ProgettoSpesa;instance=MSSQLSERVER";

    static Connection createConnection() {
        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}