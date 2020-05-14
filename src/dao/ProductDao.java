package dao;

import models.ProductModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDao extends DatabaseConnection {
    private static ResultSet resultSet;

    public static List<ProductModel> getAlLProducts() {
        List<ProductDao> products = null;
        String query = "SELECT * FROM product";

        try {
            resultSet = statement.executeQuery(query);
            for ()
        } catch (SQLException e) {

        }

        return products;
    }
}
