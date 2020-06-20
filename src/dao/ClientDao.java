package dao;

import enums.PaymentMethod;
import models.ClientModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClientDao extends BaseDao {

    private static final String INSERT_USER = "insert into clients values (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "select * from clients where id = ?";
    private static final String SELECT_ID_BY_EMAIL = "select id from clients where email = ?";
    private static final String UPDATE_BY_ID = "update clients set " +
            "name = ?, surname = ?, address = ?, zip = ?, phoneNumber = ?, email = ?, paymentMethod = ? " +
            "where id = ?";

    private ClientDao() {
    }

    public static int insertClient(ClientModel client) {
        int result = 0;
        System.out.print("Inserting client into clients table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            setStatement(client, statement);
            result = statement.executeUpdate();
            System.out.println("Client inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ClientModel selectById(int id) {
        ClientModel result = null;
        System.out.print("Selecting client with given id... ");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new ClientModel(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        PaymentMethod.values()[resultSet.getInt(8)]);
            }
            System.out.println("Client with given id selected.");
        } catch (SQLException e) {
            System.err.println("Error while selecting client!");
        }
        return result;
    }

    public static int selectIdByEmail(String email) {
        int result = 0;
        System.out.print("Selecting client with given email... ");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            System.out.println("Client with given email selected.");
        } catch (SQLException e) {
            System.err.println("Error while selecting client!");
        }
        return result;
    }

    public static int updateById(ClientModel client) {
        int result = 0;
        System.out.print("Updating client with given id... ");
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID);
            setStatement(client, statement);
            statement.setInt(8, client.getId());
            result = statement.executeUpdate();
            System.out.println("Client with given id updated. ");
        } catch (SQLException e) {
            System.err.println("Error while updating client!");
            e.printStackTrace();
        }

        return result;
    }

    private static void setStatement(ClientModel client, PreparedStatement statement) {
        try {
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getZip());
            statement.setString(5, client.getPhoneNumber());
            statement.setString(6, client.getEmail());
            statement.setInt(7, client.getIdPaymentMethod().ordinal());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
