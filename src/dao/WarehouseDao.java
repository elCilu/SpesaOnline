package dao;

import models.WarehouseModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class WarehouseDao extends BaseDao{

    private static final String GETQUANTITY_BY_ID = "select qty from warehouse where idProduct = ?";
    private static final String UPDATE_QTY_IN_STOCK = "update warehouse set qty = ? where idProduct = ?";
    private static final String INSERT_PRODUCT_QTY = "insert into warehouse values (?, ?, ?, ?)";

    private WarehouseDao() {
    }

    public static int insertWarehouse(WarehouseModel warehouse) {
        int result = 0;
        System.out.print("Inserting product into warehouse table... ");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_QTY);
            statement.setInt(1, warehouse.getIdProduct());
            statement.setInt(2, warehouse.getQty());
            statement.setInt(3, warehouse.getQtyMin());
            statement.setInt(4, warehouse.getQtyMax());
            result = statement.executeUpdate();

            if(result != 0)
                System.out.println("Product inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //ritorna qty presente in magazzino
    public static int getQuantity(int idProduct) {
        int qty = -1;   //se -1 errore nel db

        try{
            PreparedStatement statement = connection.prepareStatement(GETQUANTITY_BY_ID);
            statement.setInt(1, idProduct);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                qty = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return qty;
    }

    //aggiorna quantità di un prodotto
    /**
        @param newQty quantità già calcolata (qtyPrecedente + qtyDaAggiungere)
     **/
    public static int updateQuantity(int idProduct, int newQty) {
        int result = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QTY_IN_STOCK);
            statement.setInt(1, newQty);
            statement.setInt(2, idProduct);
            result = statement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
