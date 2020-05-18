package dao;

import models.CredentialModel;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CredentialDao extends BaseDao {
    private static final String INSERT = "insert into credentials values (?, ?, ?)";
    private static final String SELECT_BY_ID = "select * from credentials where id = ?";
    private static final String SELECT_BY_EMAIL = "select * from credentials where email = ?";
    private static final String SELECT_ID_BY_SALT = "select * from credentials where salt = ?";

    private CredentialDao(){}

    public static int  insertCredentials(CredentialModel credential) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, credential.getEmail());
            statement.setString(2, credential.getHash());
            statement.setBytes(3, credential.getSalt());
            System.out.println("Inserting credentials into Credentials table...");
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static CredentialModel selectById(int id) {
        CredentialModel password = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            System.out.println("Selecting credentials by id...");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = new CredentialModel(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBytes(4));
                System.out.println("Selected credential with given id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static int getIdBySalt(byte[] salt) {
        int id = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_SALT);
            statement.setBytes(1, salt);
            System.out.println("Selecting");//aggiungi se si usa func
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static CredentialModel selectByEmail(String email) {
        CredentialModel credentials = null;
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            System.out.println("Selecting credentials with given email...");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                credentials = new CredentialModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getBytes(4));
                System.out.println("Credentials with this email have been selected.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return credentials;
    }
}