package dao;

import models.ShoppingModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ShoppingDao extends BaseDao {

    private static final String INSERT_SHOPPING = "insert into shopping values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static int result = 0;

    private ShoppingDao() {}

    public static int insertShopping(ShoppingModel shopping) {
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