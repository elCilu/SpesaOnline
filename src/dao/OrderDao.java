package dao;

import models.OrderModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends BaseDao{
    private static final String INSERT_ORDER = "insert into orders values (?, ?, ?)";
    private static final String SELECT_ALL = "select * from orders";
    private static final String SELECT_BY_ID = "select * from orders where id = ?";
    private static final String IS_EMPTY = "select id from orders where id = 1";
    private static final String SELECT_LAST = "select top 1 * from orders order by id desc";
    private static final String UPDATE_STATUS_CONFIRMED = "update orders set status = ? where id = ?";
    private static final String SELECT_BY_CONFIRMED = "select * from orders where status = 1";


    private OrderDao() {}

    public static int selectLast(){
        int idLast = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_LAST);
            System.out.print("Selecting orders with given id... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                idLast = resultSet.getInt(1);
            }
            System.out.println("Order selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting order.");
            e.printStackTrace();
        }
        return idLast;
    }

    public static int insertOrder(OrderModel orderModel) {
        int result = 0;
        System.out.print("Inserting order into Orders table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER);
            statement.setInt(1, orderModel.getpIvaSupplier());
            statement.setInt(2, orderModel.getMatrStockMan());
            statement.setInt(3, orderModel.getStatus());
            result = statement.executeUpdate();
            System.out.println("Order inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            System.out.print("Selecting all orders... ");
            executeQuery(orders, statement);
            System.out.println("All orders selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting orders.");
            e.printStackTrace();
        }
        return orders;
    }

    public static OrderModel getOrderById(int id) {
        OrderModel order = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            System.out.print("Selecting order with given id... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = new OrderModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), 0);
            }
            System.out.println("Order selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting order.");
            e.printStackTrace();
        }
        return order;
    }

    private static void executeQuery(List<OrderModel> orders, PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            orders.add(new OrderModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), 0));
        }
    }

    static boolean isEmpty() {
        boolean result = true;
        try {
            PreparedStatement statement = connection.prepareStatement(IS_EMPTY);
            System.out.print("Checking if orders table is empty... ");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = false;
                System.out.println("Orders table is already populated!");
            } else {
                System.out.println("Orders table is empty.");
            }
        } catch (SQLException e) {
            System.err.println("Error while checking orders.");
            e.printStackTrace();
        }
        return result;
    }

    public static int updateStatusConfirmed(int id, int confirmed) {
        int result = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_CONFIRMED);
            statement.setInt(1, confirmed);
            statement.setInt(2, id);
            result = statement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static List<OrderModel> getAllOrdersConfirmed() {
        List<OrderModel> orders = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CONFIRMED);
            System.out.print("Selecting all orders CONFIRMED... ");
            executeQuery(orders, statement);
            System.out.println("All orders CONFIRMED selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting orders CONFIRMED.");
            e.printStackTrace();
        }
        return orders;
    }
}
