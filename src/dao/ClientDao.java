package dao;

import models.ClientModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDao extends BaseDao {

    private static final String INSERT_USER = "insert into client values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_EMAIL = "select idPassword from client where email = ?";

    public static void insertUser(ClientModel client) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getZip());
            statement.setString(5, client.getPhoneNumber());
            statement.setString(6, client.getEmail());
            statement.setInt(7, client.getIdPaymentMethod());
            statement.setInt(8, client.getPasswordId());
            System.out.println("Executin query: " + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int selectUserIdPasswordByUsername(String email) {
        int id = 0;
        ResultSet resultSet;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            System.out.println("Executin query: " + statement);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
