package dao;

import enums.Tag;
import models.CredentialModel;
import models.ProductModel;
import models.WarehouseModel;
import utils.CredentialUtil;
import utils.ScriptRunner;

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class DatabaseHandler extends BaseDao {
    private static final String path = new File("").getAbsolutePath().concat("/src/dao/sources/");

    private DatabaseHandler() {}

    public static boolean createAndPopulateTables() {
        boolean result = false;

        if (createTables()) {
            if (ProductDao.isEmpty()) {
                result = populateTables();
            } else {
                result = true;
            }
        }
        return result;
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
        return populateProducts() && populateAdmins();
    }

    private static boolean populateProducts() {
        boolean result = false;
        try {
            FileReader fileReader = new FileReader(path.concat("ProductsTable.csv"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String[] data;
            AtomicInteger resultQuery = new AtomicInteger();

            System.out.println("Populating products table...");
            connection.setAutoCommit(false);
            while ((line = bufferedReader.readLine()) != null) {
                data = line.split(",");
                resultQuery.set(ProductDao.insertProduct(new ProductModel(0, data[0], data[1],
                        Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), Float.parseFloat(data[5]),
                        Tag.values()[Integer.parseInt(data[6])])));
                if (resultQuery.get() == 0) {
                    connection.rollback();
                    break;
                }
            }
            bufferedReader.close();
            fileReader.close();

            if (resultQuery.get() != 0) {
                connection.commit();
                System.out.println("Product table has been populated!");

                System.out.println("Populating warehouse table...");
                Map<Integer, Integer> idQty = ProductDao.getAllIDandQtyStock();
                for (Map.Entry<Integer, Integer> entry : idQty.entrySet()) {
                    resultQuery.set(WarehouseDao.insertWarehouse(new WarehouseModel(entry.getKey(), entry.getValue(), 20, 100)));
                    if (resultQuery.get() == 0) {
                        connection.rollback();
                        System.err.println("Something went wrong while populating warehouse table.");
                        break;
                    }
                }
                if (resultQuery.get() != 0){
                    connection.commit();
                    System.out.println("Warehouse table has been populated!");
                    result = true;
                }
            } else {
                System.err.println("Something went wrong while populating product table.");
            }

            connection.setAutoCommit(true);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean populateAdmins() {
        boolean result = false;

        try {
            connection.setAutoCommit(false);
            List<String> deps = Arrays.asList("fruttaverdura", "carne", "pesce", "scatolame", "uovalatticini",
                    "salumiformaggi", "panepasticceria", "confezionati", "surgelatigelati", "merendadolci",
                    "acquabevandealcolici", "igiene", "casa");

            result = populateManagers(deps) && populateSuppliers(deps) && populateStockMan() && populateExpress();

            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Something went wrong while populating admins.");
        }

        return result;
    }

    private static boolean populateManagers(List<String> deps) {
        boolean result = true;
        int resultQuery;
        byte [] salt;
        System.out.println("Populating manager table... ");

        try {
            for (int i = 0; i < 13; i++) {
                resultQuery = ManagerDao.insertManager("Mario","Rossi", i + 1, deps.get(i).concat("@spesaonline.it"));
                if(resultQuery != 0) {
                    salt = CredentialUtil.createSalt();
                    resultQuery = CredentialDao.insertCredentials(new CredentialModel(
                            0,
                            deps.get(i).concat("@spesaonline.it"),
                            CredentialUtil.generateHash("responsabile", salt),
                            salt,
                            1));
                    if (resultQuery == 0) {
                        result = false;
                        System.err.println("Something went wrong while creating manager's credentials.");
                        connection.rollback();
                        break;
                    }
                } else {
                    result = false;
                    System.err.println("Something went wrong while inserting manager.");
                    connection.rollback();
                    break;
                }
            }

            if (result) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean populateSuppliers(List<String> deps) {
        boolean result = true;
        int resultQuery;
        byte[] salt;
        try {
            for (int i = 0; i < 13; i++) {
                resultQuery = SupplierDao.insertSupplier(
                        deps.get(i).concat("@spesaonline.it"),
                        123456789,
                        "Fornitore".concat(String.valueOf(i)),
                        i);
                if (resultQuery != 0) {
                    salt = CredentialUtil.createSalt();
                    resultQuery = CredentialDao.insertCredentials(new CredentialModel(
                            0,
                            deps.get(i).concat("@fornitore.it"),
                            CredentialUtil.generateHash("fornitore", salt),
                            salt,
                            3));
                    if (resultQuery == 0) {
                        result = false;
                        System.err.println("Something went wrong while creating supplier's credentials.");
                        connection.rollback();
                        break;
                    }
                } else {
                    result = false;
                    System.err.println("Something went wrong while inserting supplier.");
                    connection.rollback();
                    break;
                }
            }
            if(result) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean populateStockMan() {
        int resultQuery = 0;
        byte[] salt;

        try {
            resultQuery = StockManDao.insertStockMan("Paolo", "Bianchi", "magazziniere@spesaonline.it");
            if (resultQuery != 0) {
                salt = CredentialUtil.createSalt();
                resultQuery = CredentialDao.insertCredentials(new CredentialModel(
                        0,
                        "magazziniere@spesaonline.it",
                        CredentialUtil.generateHash("magazziniere", salt),
                        salt,
                        2));
                if (resultQuery == 0) {
                    System.err.println("Something went wrong while creating stock man's credentials.");
                    connection.rollback();
                }
            } else {
                System.err.println("Something went wrong while inserting stock man.");
                connection.rollback();
            }
            if (resultQuery != 0) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultQuery != 0;
    }

    private static boolean populateExpress() {
        int resultQuery = 0;
        byte[] salt;

        try {
            resultQuery = ExpressDao.insertExpress("corriere@spesaonline.it", 123456789, "DHL");
            if (resultQuery != 0) {
                salt = CredentialUtil.createSalt();
                resultQuery = CredentialDao.insertCredentials(new CredentialModel(
                        0,
                        "corriere@spesaonline.it",
                        CredentialUtil.generateHash("corriere", salt),
                        salt,
                        4));
                if (resultQuery == 0) {
                    System.err.println("Something went wrong while creating express' credentials.");
                    connection.rollback();
                }
            } else {
                System.err.println("Something went wrong while inserting express.");
                connection.rollback();
            }
            if (resultQuery != 0) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultQuery != 0;
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
}
