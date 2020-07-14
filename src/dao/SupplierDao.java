package dao;

import models.SupplierModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDao extends BaseDao{
    private static final String INSERT_SUPPLIER = "insert into suppliers values (?, ?, ?, ?)";
    private static final String SELECT_ID_BY_EMAIL = "select id from suppliers where email = ?";
    private static final String GET_SUPPLIER_BY_DEP = "select * from suppliers where dep = ?";

    public static int insertSupplier(String email, int pIva, String companyName, int dep) {
        int result = 0;
        System.out.print("Inserting supplier into suppliers table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_SUPPLIER);
            statement.setString(1, email);
            statement.setInt(2, pIva);
            statement.setString(3, companyName);
            statement.setInt(4, dep);
            result = statement.executeUpdate();
            System.out.println("Supplier inserted.");
        } catch (SQLException e) {
            System.err.println("Error while inserting supplier!");
        }
        return result;
    }

    public static int selectIdByEmail(String pIva) {
        int result = 0;
        System.out.print("Selecting supplier with given email... ");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_EMAIL);
            statement.setString(1, pIva);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            System.out.println("Supplier with given email selected.");
        } catch (SQLException e) {
            System.err.println("Error while selecting Supplier!");
        }
        return result;
    }

    public static SupplierModel getSupplier(int dep) {
        SupplierModel supplier = null;

        try{
            PreparedStatement statement = connection.prepareStatement(GET_SUPPLIER_BY_DEP);
            statement.setInt(1, dep);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                supplier = new SupplierModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return supplier;
    }
}
