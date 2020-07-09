package dao;

import enums.PaymentMethod;
import enums.Status;
import enums.Tag;
import models.ProductModel;
import models.ShoppingModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class ShoppingDao extends BaseDao {

    private static final String INSERT_SHOPPING = "insert into shopping values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_LAST = "select top 1 * from shopping order by id desc";
    private static final String GET_TODAY_DELIVERY = "select * from shopping where deliveryDate = ?";
    private static final String GET_SHOPPING = "select * from shopping where status = ?";

    private ShoppingDao() {}

    public static int selectLast(){
        int idLast = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_LAST);
            System.out.print("Selecting product with given id... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                idLast = resultSet.getInt(1);
            }
            System.out.println("Product selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting product.");
            e.printStackTrace();
        }
        return idLast;
    }

    public static int insertShopping(ShoppingModel shopping) {
        int result = 0;
        System.out.print("Inserting shopping into shopping table...\n");
        java.sql.Date purchaseDate = new java.sql.Date(shopping.getPurchaseDate().getTime());
        java.sql.Date deliveryDate = new java.sql.Date(shopping.getDeliveryDate().getTime());

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_SHOPPING);
            statement.setDate(1, purchaseDate);
            statement.setDate(2, deliveryDate);
            statement.setString(3, shopping.getDeliveryH());
            statement.setFloat(4, shopping.getTotalCost());
            statement.setInt(5, shopping.getEarnedPoints());
            statement.setInt(6, shopping.getStatus().ordinal());
            statement.setInt(7, shopping.getIdClient());
            statement.setInt(8, shopping.getIdPaymentMethod().ordinal());
            result = statement.executeUpdate();

            if(result != 0)
                System.out.print("Shopping inserted \n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<ShoppingModel> getTodayDelivery(Date date){
        List<ShoppingModel> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_TODAY_DELIVERY);
            System.out.print("Selecting today's delivering...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if(date.getDate() == resultSet.getDate(3).getDate() && date.getMonth() == resultSet.getDate(3).getMonth()
                    && date.getYear() == resultSet.getDate(3).getYear()) {
                    result.add(new ShoppingModel(resultSet.getInt(1), resultSet.getDate(2), resultSet.getDate(3),
                            resultSet.getString(4), resultSet.getFloat(5), resultSet.getInt(6),
                            Status.values()[resultSet.getInt(7)], resultSet.getInt(8),
                            PaymentMethod.values()[resultSet.getInt(9)]));
                }
            }
            System.out.println("All shopping selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting shopping.");
            e.printStackTrace();
        }

        return result;
    }

    public static List<ShoppingModel> getShoppings(Status status){
        List<ShoppingModel> shoppings = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_SHOPPING);
            statement.setInt(1, status.ordinal());
            System.out.print("Selecting all shoppings with given status... ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                shoppings.add(new ShoppingModel(resultSet.getInt(1), resultSet.getDate(2),
                        resultSet.getDate(3), resultSet.getString(4), resultSet.getFloat(5),
                        resultSet.getInt(6), Status.values()[resultSet.getInt(7)], resultSet.getInt(8), PaymentMethod.values()[resultSet.getInt(9)]));
            }
            System.out.println("All shoppings selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting shoppings.");
            e.printStackTrace();
        }

        return shoppings;
    }
}