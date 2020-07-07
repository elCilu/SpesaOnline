package controllers;

import dao.ProductShoppingDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.CartModel;
import models.ProductModel;
import models.ProductShoppingModel;
import models.ShoppingModel;

public class ConfirmedController {
    @FXML
    private GridPane ConfirmedPage;
    @FXML
    protected void goToLogIn() {
        try {
            Stage stage = (Stage) ConfirmedPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            stage.setScene(new Scene(root, 400, 350));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProducts(CartModel cart, ShoppingModel shopping){
        //aggiungo i prodotti del carrello alla tabella dei prodotti comprati
        for(ProductModel product : cart.getProducts()){
            addProductShopping(product.getId(), shopping.getId(), cart.getProductQty(product));
        }
    }

    public void addProductShopping(int idProduct, int idShopping, int qty) {
        try{
            ProductShoppingModel productShopping = new ProductShoppingModel(0, idProduct, idShopping, qty);
            int resultQuery = ProductShoppingDao.insertProductShopping(productShopping);

            if (resultQuery == 0){
                throw new Exception("Errore nell'inserimento del prodotto nel db");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
