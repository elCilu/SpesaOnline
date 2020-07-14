package dao;


import models.ProductOrderModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProductOrderDao extends BaseDao {

    private static final String INSERT_PRODUCT_ORDER = "insert into productsOrder values (?, ?, ?)";
    private static final String GET_PRODUCT_ID_AND_QTY_BY_ORDER_ID = "select idProduct, qty from productsOrder where idOrders = ?";

    private ProductOrderDao() {}

    public static int insertProductOrder(ProductOrderModel product_order) {
        int result = 0;
        System.out.print("Inserting product into productsOrder table... \n");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_ORDER);
            statement.setInt(1, product_order.getIdProduct());
            statement.setInt(2, product_order.getIdOrder());
            statement.setInt(3, product_order.getQty());
            result = statement.executeUpdate();

            if (result != 0)
                System.out.print("Product inserted \n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<Integer, Integer> getProductIdAndQtyByOrderId(int id){
        Map<Integer, Integer> result = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_ID_AND_QTY_BY_ORDER_ID);
            statement.setInt(1, id);
            System.out.print("Selecting productID from productsOrder...");
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