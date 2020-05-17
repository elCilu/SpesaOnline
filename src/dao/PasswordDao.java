package dao;

import models.PasswordModel;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordDao extends BaseDao {
    private static final String INSERT = "insert into password values (?, ?)";
    private static final String SELECT_BY_ID = "select * from password where id = ?";
    private static final String SELECT_ID_BY_SALT = "select * from password where salt = ?";

    public static void insertPassword(@NotNull String hash, @NotNull byte[] salt) {
        //TODO: input Object Password
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, hash);
            statement.setBytes(2, salt);
            System.out.println("Executin query: " + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PasswordModel selectById(int id) {
        PasswordModel password = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            System.out.println("Executin query: " + statement);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                password = new PasswordModel(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBytes(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static int getIdBySalt(@NotNull byte[] salt) {
        int id = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_SALT);
            statement.setBytes(1, salt);
            System.out.println("Executin query: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
