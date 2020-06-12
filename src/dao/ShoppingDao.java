package dao;

import models.ShoppingModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ShoppingDao extends BaseDao {

    private static final String INSERT_SHOPPING = "insert into Shopping values (?, ?, ?, ?, ?, ?, ?)";

    private ShoppingDao() {}

    public static int insertShopping(ShoppingModel shopping) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_SHOPPING);
            statement.setDate(1, (Date) shopping.getPurchaseDate());
            statement.setDate(2, (Date) shopping.getDeliveryDate());
            statement.setInt(3, shopping.getTotalCost());
            statement.setInt(4, shopping.getEarnedPoints());
            statement.setInt(5, shopping.getStatus());
            statement.setInt(6, shopping.getIdClient());
            statement.setInt(7, shopping.getIdPaymentMethod());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}