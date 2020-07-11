package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class ManagerDao extends BaseDao{

    private static final String GET_DEP = "select role from products where id = ?";

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
