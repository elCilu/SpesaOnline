package dao;

import models.ProductModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDao extends BaseDao {
    private static final Connection connection = createConnection();

    static void getProductById() {
        String query = "select * from product where id = 1";
        ProductModel result = new ProductModel();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            if (resultSet != null) {
                while (resultSet.next()) {
                    result.setId(resultSet.getInt(1));
                    result.setName(resultSet.getString(2));
                    result.setBrand(resultSet.getString(3));
                    result.setQtyPack(resultSet.getInt(4));
                    result.setDep(resultSet.getString(5));
                    result.setQtyStock(resultSet.getInt(6));
                    result.setPrize(resultSet.getFloat(7));
                    result.setTag(resultSet.getInt(8));
                }
                System.out.println(result.toString());
            } else {
                System.out.println("FAIL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
