package controllers;

import com.jfoenix.controls.JFXButton;
import dao.ProductDao;
import dao.ProductShoppingDao;
import dao.ShoppingDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.ProductModel;
import models.ShoppingModel;
import sample.Global;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ViewShopsController implements Initializable {
    @FXML
    VBox viewVBox;
    @FXML
    VBox viewButtonsVBox;
    @FXML
    VBox visualizeShoppingVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load2();


    }

    public void load2(){
        viewVBox.getChildren().clear();

        List<ShoppingModel> shoppings= ShoppingDao.getShoppingsByIdUser(Global.USER_ID);
        for (ShoppingModel s: shoppings) {
            Text orderDesc = new Text();
            orderDesc.setText("Ordine n: " + s.getId() + "\n\tStato: " + s.getStatus()+"\n");

            viewVBox.getChildren().add(orderDesc);

            //visualize button
            JFXButton viewButton = new JFXButton();
            viewButton.setText("VISUALIZZA");
            viewButton.setOnAction(actionEvent -> {
                viewShopping(s);
            });
            viewButton.setStyle("-fx-background-color:  #FFD700");
            viewButton.setButtonType(JFXButton.ButtonType.RAISED);
            viewButtonsVBox.getChildren().add(viewButton);
        }
    }

    public void viewShopping(ShoppingModel s){
        visualizeShoppingVBox.getChildren().clear();
        Map<Integer, Integer> products = ProductShoppingDao.getProductIdAndQtyByShoppingId(s.getId());
        for (Integer key: products.keySet()) {
            ProductModel product = ProductDao.getProductById(key);

            Text orderDesc = new Text();
            orderDesc.setText(product.getName() +"\n\tQuantità: " + products.get(key)+ "\n");
            visualizeShoppingVBox.getChildren().add(orderDesc);
        }
    }
}