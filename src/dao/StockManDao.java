package dao;

import dao.BaseDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockManDao extends BaseDao {
    private static final String INSERT_STOCK_MAN = "insert into stockMans values (?, ?, ?)";
    private static final String SELECT_ID_BY_EMAIL = "select matriculation from stockmans where email = ?";

    public static int insertStockMan(String name, String surname, String mail) {
        int result = 0;
        System.out.print("Inserting stock man into stockMans table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_STOCK_MAN);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, mail);
            result = statement.executeUpdate();
            System.out.println("Stock man inserted.");
        } catch (SQLException e) {
            System.err.println("Error while inserting stock man!");
        }
        return result;
    }

    public static int selectIdByEmail(String email) {
        int result = 0;
        System.out.print("Selecting stock man with given email... ");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            System.out.println("Manager with given stock man selected.");
        } catch (SQLException e) {
            System.err.println("Error while selecting stock man!");
        }
        return result;
    }
}
