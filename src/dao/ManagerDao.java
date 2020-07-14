package dao;

import models.ClientModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ManagerDao extends BaseDao{

    private static final String INSERT_MANAGER = "insert into manager values (?, ?, ?, ?)";
    private static final String SELECT_ID_BY_EMAIL = "select matriculation from manager where mail = ?";
    private static final String GET_DEP = "select dep from manager where matriculation = ?";

    public static int insertManager(String name, String surname, int dep, String mail) {
        int result = 0;
        System.out.print("Inserting manager into managers table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_MANAGER);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setInt(3, dep);
            statement.setString(4, mail);
            result = statement.executeUpdate();
            System.out.println("Manager inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int selectIdByEmail(String email) {
        int result = 0;
        System.out.print("Selecting manager with given email... ");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            System.out.println("Manager with given email selected.");
        } catch (SQLException e) {
            System.err.println("Error while selecting manager!");
        }
        return result;
    }

    public static int getDep(int id){
        int role = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(GET_DEP);
            statement.setInt(1, id);
            System.out.println("Selecting role of manager with id " + id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("Error while selecting role of manager");
            e.printStackTrace();
        }
        return role;
    }
}
