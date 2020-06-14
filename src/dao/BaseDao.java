package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class BaseDao {
    //TODO: for every DAO class: print db status (executing query, returned x rows, etc.)
    static Connection connection = createConnection();
    private static final String connectionUrl =
            "jdbc:jtds:sqlserver://LAPTOP-IV2QJ2NV/Spesa_Online;instance=MSSQLSERVER;"
           // + "IntegratedSecurity=true;";
                    + "database=Spesa_Online;" //nome db su mssql
                    + "user=Sara;" //username di mssql
                    + "password=admin;" //vostra password al mssql
                    + "loginTimeout=30;";

    protected static Connection createConnection() {
        try {
            System.out.print("Connecting to database...");
            System.setProperty("java.net.preferIPv6Addresses", "true");
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected to database!");

        } catch (SQLException e) {
            System.err.println("Error while connecting to database.");
            e.printStackTrace();
        }
        return connection;
    }
}