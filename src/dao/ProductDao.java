package dao;

import models.ProductModel;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProductDao extends BaseDao{
    //TODO: arrange whole class

    private static final String SELECT_ALL = "select * from product";
    private static final String SELECT_BY_ID = "select * from product where id = %d";
    private static final String SELECT_BY_DEP = "select * from product where dep = %s";
    private static final String SELECT_BY_TAG = "select * from product where tag = %d";

    private ProductDao() {
    }

    static List<ProductModel> getAllProducts() {
        return getProductModels(SELECT_ALL);
    }

    static ProductModel getProductById(Integer id) {
        ProductModel product = null;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(SELECT_BY_ID, id));
            while (resultSet.next()) {
                product = createProductModel(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    static List<ProductModel> getProductsByDep(String dep) {
        return getProductModels(String.format(SELECT_BY_DEP, dep));
    }

    static List<ProductModel> getProductsByTag(int tag) {
        return getProductModels(String.format(SELECT_BY_TAG, tag));
    }

    @NotNull
    private static List<ProductModel> getProductModels(String query) {
        List<ProductModel> products = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                products.add(createProductModel(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static ProductModel createProductModel(@NotNull ResultSet resultSet) {
        //TODO: replace it with Product constructor
        ProductModel product = new ProductModel();
        try {
            product.setId(resultSet.getInt(1));
            product.setName(resultSet.getString(2));
            product.setBrand(resultSet.getString(3));
            product.setQtyPack(resultSet.getInt(4));
            product.setDep(resultSet.getString(5));
            product.setQtyStock(resultSet.getInt(6));
            product.setPrize(resultSet.getFloat(7));
            product.setTag(resultSet.getInt(8));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
