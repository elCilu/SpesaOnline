package dao;

import enums.PaymentMethod;
import enums.Status;
import models.ShoppingModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ShoppingDao extends BaseDao {

    private static final String INSERT_SHOPPING = "insert into shopping values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_LAST = "select top 1 * from shopping order by id desc";
    private static final String GET_TODAY_DELIVERY = "select * from shopping where deliveryDate = ?";
    private static final String GET_ALL_SHOPPINGS = "select * from shopping";
    private static final String UPDATE_SHOPPING_STATUS = "update shopping set status = ? where id = ?";
    private static final String GET_SHOPPINGS_BY_USER_ID = "select * from shopping where idUser = ?";

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

    public static List<ShoppingModel> getTodayDelivery(){
        List<ShoppingModel> result = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(GET_TODAY_DELIVERY);
            statement.setDate(1, new java.sql.Date(new Date().getTime()));
            System.out.print("Selecting today's delivering...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new ShoppingModel(resultSet.getInt(1), resultSet.getDate(2), resultSet.getDate(3),
                        resultSet.getString(4), resultSet.getFloat(5), resultSet.getInt(6),
                        Status.values()[resultSet.getInt(7)], resultSet.getInt(8),
                        PaymentMethod.values()[resultSet.getInt(9)]));
            }

            System.out.println("All shopping selected!");
        } catch (SQLException e) {
            System.err.println("Error while selecting shopping.");
            e.printStackTrace();
        }

        return result;
    }

    public static List<ShoppingModel> getAllShoppings(){    
        List<ShoppingModel> shoppings = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement(GET_ALL_SHOPPINGS);
            System.out.print("Selecting shoppings...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                shoppings.add(new ShoppingModel(resultSet.getInt(1), resultSet.getDate(2),
                        resultSet.getDate(3), resultSet.getString(4), resultSet.getInt(5),
                        resultSet.getInt(6),Status.values()[resultSet.getInt(7)], resultSet.getInt(8),
                        PaymentMethod.values()[resultSet.getInt(9)]));
            }
            System.out.println("All shoppings are selected!");
        }catch (SQLException e){
            System.out.println("Errore while selecting all shoppings.");
            e.printStackTrace();
        }
        return shoppings;
    }

    public static int updateStatus(int idShopping, int status) {

        int result = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_SHOPPING_STATUS);
            statement.setInt(1, status);

            statement.setInt(2, idShopping);
            result = statement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static List<ShoppingModel> getShoppingsByIdUser(int idUser){
        List<ShoppingModel> shoppings = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement(GET_SHOPPINGS_BY_USER_ID);
            statement.setInt(1, idUser);
            System.out.print("Selecting shoppings...");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                shoppings.add(new ShoppingModel(resultSet.getInt(1), resultSet.getDate(2),
                        resultSet.getDate(3), resultSet.getString(4), resultSet.getInt(5),
                        resultSet.getInt(6),Status.values()[resultSet.getInt(7)], resultSet.getInt(8),
                        PaymentMethod.values()[resultSet.getInt(9)]));
            }
            System.out.println("All shoppings are selected!");
        }catch (SQLException e){
            System.out.println("Errore while selecting all shoppings.");
            e.printStackTrace();
        }
        return shoppings;
    }
}