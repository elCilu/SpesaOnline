package dao;

import enums.Tag;
import models.ProductModel;
import utils.ScriptRunner;

import java.io.*;
import java.sql.SQLException;

public final class DatabaseHandler extends BaseDao {
    private static final String path = new File("").getAbsolutePath().concat("/src/dao/sources/");

    private DatabaseHandler() {
    }

    public static boolean createAndPopulateTables() {
        final boolean[] result = {false};

        if (createTables()) {
            if (ProductDao.isEmpty()) {
                result[0] = populateTables();
            } else {
                result[0] = true;
            }
        }
        return result[0];
    }

    public static void closeConnection() {
        try {
            if (!connection.isClosed()) {
                System.out.print("Closing connection to database... ");
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean createTables() {
        boolean result = false;
        try {
            Reader reader = new BufferedReader(new FileReader(path.concat("CreateTables.sql")));
            System.out.print("Creating tables... ");
            ScriptRunner scriptRunner = new ScriptRunner(connection, false, true);
            scriptRunner.runScript(reader);
            System.out.println("Tables have been created.");
            reader.close();
            result = true;
        } catch (SQLException | IOException e) {
            System.err.println("Error while creating tables.");
            e.printStackTrace();
        }
        return result;
    }

    private static boolean populateTables() {
        boolean result = false;
        try {
            FileReader fileReader = new FileReader(path.concat("ProductsTable.csv"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String[] data;
            int resultQuery = 0;

            System.out.print("Populating tables... ");
            connection.setAutoCommit(false);
            while ((line = bufferedReader.readLine()) != null) {
                data = line.split(",");
                resultQuery = ProductDao.insertProduct(new ProductModel(0, data[0], data[1],
                        Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), Float.parseFloat(data[5]), Tag.BIO));
                if (resultQuery == 0) {
                    connection.rollback();
                    break;
                }
            }
            if (resultQuery != 0) {
                connection.commit();
                System.out.println("Tables have been populated!");
                result = true;
            } else {
                System.err.println("Something went wrong while populating tables.");
            }
            connection.setAutoCommit(true);
            bufferedReader.close();
            fileReader.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
