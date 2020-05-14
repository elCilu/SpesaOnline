package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDao extends BaseDao {

    static void getAllProducts() {
        String query = "SELECT * FROM product";
        try {
            ResultSet resultSet = createConnection().createStatement().executeQuery(query);
            if (resultSet != null) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(2));
                }
            } else {
                System.out.println("FAIL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
