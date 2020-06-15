package dao;

import models.ProductShoppingModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ProductShoppingDao extends BaseDao {

    private static final String INSERT_PRODUCT_SHOPPING = "insert into ProductShopping values (?, ?, ?)";

    private ProductShoppingDao() {}

    public static int insertProductShopping(ProductShoppingModel product_shopping) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_SHOPPING);
            statement.setInt(1, product_shopping.getIdProduct());
            statement.setInt(2, product_shopping.getIdShopping());
            statement.setInt(3, product_shopping.getQty());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}