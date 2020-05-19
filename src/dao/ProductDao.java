package dao;

import models.ProductModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProductDao extends BaseDao {

    private static final String INSERT_PRODUCT = "insert into products values (?, ?, ?, ? , ?, ?, ?)";
    private static final String SELECT_ALL = "select * from products";
    private static final String SELECT_BY_ID = "select * from products where id = ?";
    private static final String SELECT_BY_DEP = "select * from products where dep = ?";
    private static final String SELECT_BY_TAG = "select * from products where tag = ?";
    private static final String IS_EMPTY = "select id from products where id = 1";

    private ProductDao() {
    }

    public static int insertProduct(ProductModel productModel) {
        int result = 0;
        System.out.print("Inserting product into Products table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT);
            statement.setString(1, productModel.getName());
            statement.setString(2, productModel.getBrand());
            statement.setInt(3, productModel.getQtyPack());
            statement.setString(4, productModel.getDep());
            statement.setInt(5, productModel.getQtyStock());
            statement.setFloat(6, productModel.getprice());
            statement.setInt(7, productModel.getTag());
            result = statement.executeUpdate();
            System.out.println("Product inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            System.out.print("Selecting all products... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8)));
            }
            System.out.println("All products selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting products.");
            e.printStackTrace();
        }
        return products;
    }

    static ProductModel getProductById(int id) {
        ProductModel product = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            System.out.print("Selecting product with given id... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                product = new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8));
            }
            System.out.println("Product selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting product.");
            e.printStackTrace();
        }
        return product;
    }

    static List<ProductModel> getProductsByDep(String dep) {
        List<ProductModel> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_DEP);
            statement.setString(1, dep);
            System.out.print("Selecting products with given department... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8)));
            }
            System.out.println("Products selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting products.");
            e.printStackTrace();
        }
        return products;
    }

    static List<ProductModel> getProductsByTag(int tag) {
        List<ProductModel> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_TAG);
            statement.setInt(1, tag);
            System.out.print("Selecting products with given tag... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8)));
            }
            System.out.println("Products selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting products.");
            e.printStackTrace();
        }
        return products;
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
}
