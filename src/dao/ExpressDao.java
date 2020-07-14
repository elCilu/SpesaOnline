package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpressDao extends BaseDao{
    private static final String INSERT_EXPRESS = "insert into express values (?, ?, ?)";
    private static final String SELECT_ID_BY_EMAIL = "select id from express where email = ?";

    public static int insertExpress(String email, int pIva, String companyName) {
        int result = 0;
        System.out.print("Inserting express into express table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_EXPRESS);
            statement.setString(1, email);
            statement.setInt(2, pIva);
            statement.setString(3, companyName);
            result = statement.executeUpdate();
            System.out.println("Express inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int selectIdByEmail(String pIva) {
        int result = 0;
        System.out.print("Selecting express with given email... ");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_EMAIL);
            statement.setString(1, pIva);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            System.out.println("Express with given email selected.");
        } catch (SQLException e) {
            System.err.println("Error while selecting express!");
        }
        return result;
    }
}
