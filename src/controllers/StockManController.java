package controllers;

import dao.*;
import enums.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.ProductModel;
import models.ShoppingModel;
import javafx.scene.text.Text;

import javax.net.ssl.SNIHostName;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

//fare ordineModel e ordineDao
//il fornitore rifornisce il warehouse
//fare tableview e aggiungere una colonna solo per lo stato dei prodotti
public class StockManController implements Initializable {
    @FXML
    VBox viewButtonsVBox;
    @FXML
    VBox viewVBox;
    @FXML
    VBox visualizeShoppingVBox;
    @FXML
    VBox shoppingVBox;
    @FXML
    GridPane grid;
    @FXML
    Button newOrderButton;

    Map<ProductModel, Integer> productsToRequest = new HashMap<>();
    private static final int QTYMIN = 20;
    private static final int QTYMAX = 100;
    private Text message = new Text();
    private Button sendButton = new Button();
    private Text status = new Text();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage();
    }

    void loadPage(){
        for(ShoppingModel shopping : ShoppingDao.getAllShoppings()/*ShoppingDao.getTodayDelivery((new Date()))*/) {
            //short description of a shopping
            Text shDescription = new Text();
            shDescription.setText("Shopping n: " + shopping.getId() +
                                " \nDelivery: " + shopping.getDeliveryDate() + " " + shopping.getDeliveryH()
                                + "\nStatus: " + shopping.getStatus());

            viewVBox.getChildren().add(shDescription);

            //visualize button
            Button viewButton = new Button();
            viewButton.setText("VISUALIZZA");
            viewButton.setOnAction(actionEvent -> {
                viewShopping(shopping);
            });
            viewButtonsVBox.getChildren().add(viewButton);
        }
    }

    private void viewShopping(ShoppingModel shopping) {
        refresh();
        Map<Integer, Integer> shoppings = ProductShoppingDao.getProductIdAndQtyByShoppingId(shopping.getId());
        for (Integer productId : shoppings.keySet()){
            ProductModel p = ProductDao.getProductById(productId);

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand()  + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            visualizeShoppingVBox.getChildren().add(prodNameCode);


            //product quantity
            Text qty = new Text();
            qty.setText(String.valueOf(shoppings.get(productId)));
            shoppingVBox.getChildren().add(qty);
        }
        status.setText(shopping.getStatus().toString());
        visualizeShoppingVBox.getChildren().add(status);
        Button statusButton = new Button();
        statusButton.setText("CONFERMA SPESA");
        statusButton.setOnAction(actionEvent -> {
            modifyStatus(shopping);
        });
        shoppingVBox.getChildren().add(statusButton);
    }


    //modifica stato della spesa (da confermato a in consegna)
    public void modifyStatus(ShoppingModel shopping) {
        Date currentDate = new Date();

        //magazziniere controlla che la spesa deve essere spedita quel giorno
        if(currentDate.compareTo(shopping.getDeliveryDate()) == 0)  //controllare se è giusto
            shopping.setStatus(Status.DELIVERING);
        status.setText(shopping.getStatus().toString());
        //stampa onAction la data non è quella di oggi
    }

    //invia ordine a fornitore
    public void sendOrder(){
        //fai model ordine int id, int piva fornitore, int matricola magazz.
        //1 fornitore per reparto
        //salva ordine nel db

    }

    public void checkProducts(){
        for(ProductModel p : ProductDao.getAllProducts()){
            //check if under the limit
            if(ProductDao.getQtyInStock(p.getId()) < QTYMIN){
                //order the remaining qty
                productsToRequest.put(p, (QTYMAX - p.getQtyStock()));
            }
        }
    }

    @FXML
    public void visualizeOrder(){
        //check product to order
        checkProducts();
        //refresh of containers
        refresh();

        for(ProductModel p: productsToRequest.keySet()){
            //set the name of the product
            Text product = new Text();
            product.setText(p.getName());
            visualizeShoppingVBox.getChildren().add(product);

            //set the quantity
            Text qty = new Text();
            qty.setText(String.valueOf(productsToRequest.get(p)));
            shoppingVBox.getChildren().add(qty);
        }

        //confirmation button
        sendButton.setText("INVIA");
        sendButton.setOnAction(actionEvent -> {
            //send the order
            sendOrder();
            refresh();
            message.setText("ORDINE INVIATO!");
            message.autosize();
            visualizeShoppingVBox.getChildren().add(message);
            newOrderButton.setDisable(true);
        });
        shoppingVBox.getChildren().add(sendButton);
        //grid.add(sendButton, 1, 1);

    }

    void refresh(){
        //refresh of containers
        visualizeShoppingVBox.getChildren().clear();
        shoppingVBox.getChildren().clear();
        grid.getChildren().remove(sendButton);//rimuovere il button
    }

}
