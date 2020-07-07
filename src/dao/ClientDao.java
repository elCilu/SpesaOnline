package dao;

import models.ClientModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ClientDao extends BaseDao {

    private static final String INSERT_USER = "insert into clients values (?, ?, ?, ?, ?, ?, ?)";

    private ClientDao() {}

    public static int insertClient(ClientModel client) {
        int result = 0;
        System.out.print("Inserting client into clients table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getZip());
            statement.setString(5, client.getPhoneNumber());
            statement.setString(6, client.getEmail());
            statement.setInt(7, client.getIdPaymentMethod());
            result = statement.executeUpdate();
            System.out.println("Client inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
