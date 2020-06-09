package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

//da db mi serve ricavare solo le qty in stock
public class CartDao extends BaseDao{

    private static final String GET_QTY_IN_STOCK = "select qtyStock from products where id = ?";

    public static int getQtyInStock(int id){
        int qtyInStock = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(GET_QTY_IN_STOCK);
            statement.setInt(1, id);
            System.out.println("Selecting qty in stock of the product with id " + id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                qtyInStock = resultSet.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("Error while selecting qtyStock");
            e.printStackTrace();
        }
        return qtyInStock;
    }
}
