package dao;

import models.ProductShoppingModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProductShoppingDao extends BaseDao {

    private static final String INSERT_PRODUCT_SHOPPING = "insert into productsShopping values (?, ?, ?)";
    private static final String GET_PRODUCT_ID_AND_QTY_BY_SHOPPING_ID = "select idProduct, qty from productsShopping where idShopping = ?";

    private ProductShoppingDao() {}

    public static int insertProductShopping(ProductShoppingModel product_shopping) {
        int result = 0;
        System.out.print("Inserting product into productsShopping table... \n");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_SHOPPING);
            statement.setInt(1, product_shopping.getIdProduct());
            statement.setInt(2, product_shopping.getIdShopping());
            statement.setInt(3, product_shopping.getQty());
            result = statement.executeUpdate();

            if (result != 0)
                System.out.print("Product inserted \n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<Integer, Integer> getProductIdAndQtyByShoppingId(int id){
        Map<Integer, Integer> result = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_ID_AND_QTY_BY_SHOPPING_ID);
            statement.setInt(1, id);
            System.out.print("Selecting productID from productsShopping...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.put(resultSet.getInt(1), resultSet.getInt(2));
            }
            System.out.println("All productId and qty are selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting productId and qty.");
            e.printStackTrace();
        }
        return result;
    }
}