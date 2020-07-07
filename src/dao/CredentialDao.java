package dao;

import models.CredentialModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CredentialDao extends BaseDao {
    private static final String INSERT = "insert into credentials values (?, ?, ?)";
    //private static final String SELECT_BY_ID = "select * from credentials where id = ?";
    private static final String SELECT_BY_EMAIL = "select * from credentials where email = ?";

    private CredentialDao(){}

    public static int  insertCredentials(CredentialModel credential) {
        int result = 0;
        System.out.print("Inserting credentials into Credentials table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, credential.getEmail());
            statement.setString(2, credential.getHash());
            statement.setBytes(3, credential.getSalt());
            result = statement.executeUpdate();
            System.out.println("Credentials inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*public static CredentialModel selectById(int id) {
        CredentialModel password = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            System.out.print("Selecting credentials by id... ");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = new CredentialModel(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBytes(4));
                System.out.println("Credentials inserted.");
            }
        } catch (SQLException e) {
            System.err.println("Error while selecting product.");
            e.printStackTrace();
        }
        return password;
    }*/

    public static CredentialModel selectByEmail(String email) {
        CredentialModel credentials = null;
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            System.out.print("Selecting credentials with given email... ");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                credentials = new CredentialModel(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getBytes(4));
                System.out.println("Credentials selected!");
            }
        }catch (SQLException e){
            System.err.println("Error while selecting credentials.");
            e.printStackTrace();
        }
        return credentials;
    }
}
