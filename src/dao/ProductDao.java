package dao;

import models.ProductModel;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProductDao extends BaseDao{

    private static final String SELECT_ALL = "select * from product";
    private static final String SELECT_BY_ID = "select * from product where id = ?";
    private static final String SELECT_BY_DEP = "select * from product where dep = ?";
    private static final String SELECT_BY_TAG = "select * from product where tag = ?";

    private ProductDao() {
    }

    static List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            System.out.println("Selecting all products...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8)));
            }
            System.out.println("All products selected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    static ProductModel getProductById(int id) {
        ProductModel product = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            System.out.println("Selecting product with given id...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                product = new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8));
            }
            System.out.println("Product with this id has been selected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    static List<ProductModel> getProductsByDep(String dep) {
        List<ProductModel> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_DEP);
            statement.setString(1, dep);
            System.out.println("Selecting products with given department...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8)));
            }
            System.out.println("Products belonging to given department have been selected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    static List<ProductModel> getProductsByTag(int tag) {
        List<ProductModel> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_TAG);
            statement.setInt(1, tag);
            System.out.println("Selecting products with given tag...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8)));
            }
            System.out.println("Products with this tag have been selected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    private static List<ProductModel> getProductModels(String query) {
        List<ProductModel> products = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                products.add(new ProductModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getFloat(7), resultSet.getInt(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


}
