package controllers;

import dao.ProductDao;
import dao.ProductShoppingDao;
import dao.WarehouseDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.ProductModel;
import models.ProductShoppingModel;
import models.ShoppingModel;
import sample.GlobalVars;

public class ConfirmedController {
    @FXML
    private GridPane ConfirmedPage;

    @FXML
    protected void goToLogIn() {
        try {
            Stage stage = (Stage) ConfirmedPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProducts(ShoppingModel shopping) {
        //aggiungo i prodotti del carrello alla tabella dei prodotti comprati
        for (ProductModel product : GlobalVars.cart.keySet()) {
            addProductShopping(product.getId(), shopping.getId(), GlobalVars.cart.get(product));
            //update warehouse
            updateWarehouse(product.getId(), (WarehouseDao.getQuantity(product.getId()) - GlobalVars.cart.get(product)));
        }
    }
    public void updateWarehouse(int id, int qty){
        try {
            int resultQuery1 = ProductDao.updateQuantity(id, qty);
            if (resultQuery1 == 0)
                throw new Exception("Errore nell'aggiornamento del qtyStock di products nel db");
            int resultQuery = WarehouseDao.updateQuantity(id, qty);
            if (resultQuery == 0)
                throw new Exception("Errore nell'aggiornamento del magazzino nel db");

            } catch(Exception e){
                e.printStackTrace();
            }
    }

    public void addProductShopping(int idProduct, int idShopping, int qty) {
        try {
            ProductShoppingModel productShopping = new ProductShoppingModel(0, idProduct, idShopping, qty);
            int resultQuery = ProductShoppingDao.insertProductShopping(productShopping);

            if (resultQuery == 0) {
                throw new Exception("Errore nell'inserimento del prodotto nel db");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}