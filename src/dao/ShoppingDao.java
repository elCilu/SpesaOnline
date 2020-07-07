package dao;

import models.ShoppingModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ShoppingDao extends BaseDao {

    private static final String INSERT_SHOPPING = "insert into shopping values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_LAST = "select top 1 * from shopping order by id desc";

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
            statement.setInt(6, shopping.getStatus());
            statement.setInt(7, shopping.getIdClient());
            statement.setInt(8, shopping.getIdPaymentMethod());
            result = statement.executeUpdate();

            if(result != 0)
                System.out.print("Shopping inserted \n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}