package dao;

import models.SupplierModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDao extends BaseDao {
    private static final String GET_SUPPLIER_BY_DEP = "select * from suppliers where dep = ?";

    private SupplierDao(){
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
