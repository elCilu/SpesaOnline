package dao;

import enums.Tag;
import models.ProductModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProductDao extends BaseDao {

    private static final String INSERT_PRODUCT = "insert into products values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "select * from products";
    private static final String SELECT_BY_ID = "select * from products where id = ?";
    private static final String IS_EMPTY = "select id from products where id = 1";
    private static final String GET_QTY_IN_STOCK = "select qtyStock from products where id = ?";
    private static final String SELECT = "select * from products";

    private ProductDao() {}

    public static int insertProduct(ProductModel productModel) {
        int result = 0;
        System.out.print("Inserting product into Products table... ");
        try {
            int[] bits = setBits(productModel.getTag());
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT);
            statement.setString(1, productModel.getName());
            statement.setString(2, productModel.getBrand());
            statement.setInt(3, productModel.getQtyPack());
            statement.setString(4, productModel.getDep());
            statement.setInt(5, productModel.getQtyStock());
            statement.setFloat(6, productModel.getprice());
            statement.setInt(7, productModel.getTag().ordinal());
            statement.setInt(8, bits[0]);
            statement.setInt(9, bits[1]);
            statement.setInt(10, bits[2]);

            result = statement.executeUpdate();
            System.out.println("Product inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            System.out.print("Selecting all products... ");
            executeQuery(products, statement);
            System.out.println("All products selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting products.");
            e.printStackTrace();
        }
        return products;
    }

    public static List<ProductModel> select(String search, int indexSort, String dep, int[] bits) {
        List<ProductModel> products = new ArrayList<>();
        String toConcat = "";

        if (!search.isBlank())
            toConcat += " where (name like '%" + search + "%' or brand like '%" + search + "%')";
        if (!dep.equals("Tutto")) {
            if (toConcat.contains("where"))
                toConcat += " and dep = '" + dep + "'";
            else
                toConcat += " where dep = '" + dep + "'";
        }

        if(bits[0] == 1) {
            if (toConcat.contains("where"))
                toConcat += " and bio = 1";
            else
                toConcat += " where bio = 1";
        }
        if(bits[1] == 1) {
            if (toConcat.contains("where"))
                toConcat += " and glutenFree = 1";
            else
                toConcat += " where glutenFree = 1";
        }
        if(bits[2] == 1) {
            if (toConcat.contains("where"))
                toConcat += " and dairyFree = 1";
            else
                toConcat += " where dairyFree = 1";
        }

        if (indexSort == 1) {
            toConcat += " order by price asc";
        } else if (indexSort == 2) {
            toConcat += " order by price desc";
        } else if (indexSort == 3){
            toConcat += " order by name";
        }
        try {
            System.err.println(SELECT.concat(toConcat));
            PreparedStatement statement = connection.prepareStatement(SELECT.concat(toConcat));
            System.out.print("Selecting products with filters... ");
            executeQuery(products, statement);
            System.out.println("Selected products with filters!");
        } catch (SQLException e) {
            System.err.println("Error while selecting products.");
            e.printStackTrace();
        }
        return products;
    }

    public static ProductModel getProductById(int id) {
        ProductModel product = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            System.out.print("Selecting product with given id... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                product = new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), Tag.values()[resultSet.getInt(8)]);
            }
            System.out.println("Product selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting product.");
            e.printStackTrace();
        }
        return product;
    }

    private static void executeQuery(List<ProductModel> products, PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                    resultSet.getInt(6), resultSet.getFloat(7), Tag.values()[resultSet.getInt(8)]));
        }
    }

    public static int getQtyInStock(int id){
        int qtyInStock = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(GET_QTY_IN_STOCK);
            statement.setInt(1, id);
            System.out.println("Selecting qty in stock of the product with id " + id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                qtyInStock = resultSet.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("Error while selecting qtyStock");
            e.printStackTrace();
        }
        return qtyInStock;
    }

    static boolean isEmpty() {
        boolean result = true;
        try {
            PreparedStatement statement = connection.prepareStatement(IS_EMPTY);
            System.out.print("Checking if product table is empty... ");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = false;
                System.out.println("Products table is already populated!");
            } else {
                System.out.println("Products table is empty.");
            }
        } catch (SQLException e) {
            System.err.println("Error while checking products.");
            e.printStackTrace();
        }
        return result;
    }

    static int[] setBits(Tag tag){
        int[] bits = {0, 0, 0};
        switch (tag) {
            case BIO:
                bits[0] = 1;
                break;
            case GLUTEN_FREE:
                bits[1] = 1;
                break;
            case DAIRY_FREE:
                bits[2] = 1;
                break;
            case BIO_GLUTEN_FREE:
                bits[0] = 1;
                bits[1] = 1;
                break;
            case BIO_DAIRY_FREE:
                bits[0] = 1;
                bits[2] = 1;
                break;
            case GLUTEN_FREE_DAIRY_FREE:
                bits[1] = 1;
                bits[2] = 1;
                break;
            case BIO_GLUTEN_FREE_DAIRY_FREE:
                bits[0] = 1;
                bits[1] = 1;
                bits[2] = 1;
                break;
        }
        return bits;
    }
}
