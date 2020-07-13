package controllers;

import com.jfoenix.controls.JFXButton;
import dao.*;
import enums.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.*;
import javafx.scene.text.Text;
import sample.GlobalVars;
import utils.OSystem;

//import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

//aggiungere la colonna status
//aggiungere la tabella SupplierOrderDao
//fare tableview e aggiungere una colonna solo per lo stato dei prodotti
public class SupplierController implements Initializable {
    @FXML
    VBox viewButtonsVBox;
    @FXML
    VBox viewVBox;
    @FXML
    VBox visualizeShoppingVBox;
    @FXML
    VBox shoppingVBox;
    @FXML
    VBox qtyVBox;
    @FXML
    Button commonButton;
    @FXML
    Button newOrderButton;
    @FXML
    Button visualizeOrdersButton;
    @FXML
    ImageView logo;

    Map<ProductModel, Integer> productsToRequest = new HashMap<>();
    List<ProductModel> products = new ArrayList<>();
    ListView<String> depProducts = new ListView<>();
    private static final int QTYMIN = 20;
    private static final int QTYMAX = 100;
    private Text message = new Text();
    private Text status = new Text();
    private static final File prodImg = new File("");
    private String path = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //build the path
        if (OSystem.isWindows())
            path = "C:\\" + prodImg.getAbsolutePath() + "\\images\\";
        if (OSystem.isUnix())
            path = "file://" + prodImg.getAbsolutePath() + "/images/";
        if (OSystem.isMac())
            path = "";

        // logo.setImage(new Image(path + "warehouse.png"));
        visualizeOrders();
    }

    void refresh(){
        //refresh of containers
        visualizeShoppingVBox.getChildren().clear();
        shoppingVBox.getChildren().clear();
        qtyVBox.getChildren().clear();
        commonButton.setVisible(false);
    }

    /*@FXML
    public void visualizeOrders() {
        //refresh();
        //newOrderButton.setDisable(false);
        viewVBox.getChildren().clear();
        viewButtonsVBox.getChildren().clear();
        List<OrderModel> orders = OrderDao.getAllOrders();
        if (orders.isEmpty()) {
            Text noOrders = new Text();
            noOrders.setText("NON CI SONO ORDINI!");

            viewVBox.getChildren().add(noOrders);
        } else {
            for (OrderModel o : orders) {
                //short description of a shopping
                Text orderDesc = new Text();
                orderDesc.setText("Ordine n: " + o.getId() + "\nId Fornitore: " + o.getpIvaSupplier());

                viewVBox.getChildren().add(orderDesc);

                //visualize button
                JFXButton viewButton = new JFXButton();
                viewButton.setText("VISUALIZZA");
                viewButton.setOnAction(actionEvent -> {
                    viewOrder(o);
                });
                viewButton.setStyle("-fx-background-color:  #FFD700");
                viewButton.setButtonType(JFXButton.ButtonType.RAISED);
                viewButtonsVBox.getChildren().add(viewButton);
            }
        }
        commonButton.setVisible(false);
        viewButtonsVBox.setSpacing(5);
    }

    private void viewOrder(OrderModel order) {
        //refresh();
        commonButton.setVisible(true);
        viewButtonsVBox.setVisible(false);
        Map<Integer, Integer> shop = ProductOrderDao.getProductIdAndQtyByOrderId(order.getId());
        for (Integer productId : shop.keySet()) {
            ProductModel p = ProductDao.getProductById(productId);

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand() + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            visualizeShoppingVBox.getChildren().add(prodNameCode);

            //product quantity
            Text qty = new Text();
            qty.setText(String.valueOf(shop.get(productId)));
            shoppingVBox.getChildren().add(qty);
        }
    }*/
    @FXML
    public void visualizeOrders(){
        //refresh();
        //newOrderButton.setDisable(false);
        viewVBox.getChildren().clear();
        viewButtonsVBox.getChildren().clear();
        List<OrderModel> orders = OrderDao.getAllOrders();
        if(orders.isEmpty()){
            Text noOrders = new Text();
            noOrders.setText("NON CI SONO ORDINI!");

            viewVBox.getChildren().add(noOrders);
        }
        else {
            for (OrderModel o : orders) {
                //short description of a shopping
                Text orderDesc = new Text();
                orderDesc.setText("Ordine n: " + o.getId() + "\nId Fornitore: " + o.getpIvaSupplier());

                viewVBox.getChildren().add(orderDesc);

                //visualize button
                JFXButton viewButton = new JFXButton();
                viewButton.setText("VISUALIZZA");
                viewButton.setOnAction(actionEvent -> {
                    viewOrder(o);
                });
                viewButton.setStyle("-fx-background-color:  #FFD700");
                viewButton.setButtonType(JFXButton.ButtonType.RAISED);
                viewButtonsVBox.getChildren().add(viewButton);
            }
        }
        commonButton.setVisible(false);
        viewButtonsVBox.setSpacing(5);
    }

    private void viewOrder(OrderModel order) {
        refresh();
        commonButton.setVisible(true);

        Map<Integer, Integer> shopp = ProductOrderDao.getProductIdAndQtyByOrderId(order.getId());
        for (Integer productId : shopp.keySet()) {
            ProductModel p = ProductDao.getProductById(productId);

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand() + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            visualizeShoppingVBox.getChildren().add(prodNameCode);

            //product quantity
            Text qty = new Text();
            qty.setText(String.valueOf(shopp.get(productId)));
            shoppingVBox.getChildren().add(qty);
        }
    }

    @FXML
    public void confirmedOrder() {
        OrderModel order = new OrderModel(0, 1/*getSupplier().getpIva()*/, 1/*GlobalVars.STOCK_MAN_ID*/, 0);
        Map<Integer, Integer> shopping = ProductOrderDao.getProductIdAndQtyByOrderId(order.getId());
        //CAMBIO QUANTITA'
        for (Integer productId : shopping.keySet()) {
            ProductModel p = ProductDao.getProductById(productId);
            try {

                int resultQuery = ProductDao.updateQty(p.getId(), 100);

                if (resultQuery == 0) {
                    throw new Exception("Errore nell'inserimento della quantità");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //CAMBIO STATO "CONFIRMED"
        OrderModel orderConfirmed = new OrderModel(0, 1/*getSupplier().getpIva()*/, 1/*GlobalVars.STOCK_MAN_ID*/, 0);
        try {

            int resultQuery = OrderDao.updateStatusConfirmed(orderConfirmed.getId(), 1);

            if (resultQuery == 0) {
                throw new Exception("Errore nel cambio di stato confermato");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //svuoto pagina + lascio messaggio "ordine confermato!"
        visualizeShoppingVBox.getChildren().clear();
        shoppingVBox.getChildren().clear();
        qtyVBox.getChildren().clear();
        commonButton.setVisible(false);
        message.setText("ORDINE CONFERMATO!");
        message.autosize();
        visualizeShoppingVBox.getChildren().add(message);
    }


    public void VisualizeConfirmedOrders() {
        visualizeShoppingVBox.getChildren().clear();
        shoppingVBox.getChildren().clear();
        qtyVBox.getChildren().clear();
        commonButton.setVisible(false);
        viewButtonsVBox.getChildren().clear();



        List<OrderModel> ordersConf = OrderDao.getAllOrdersConfirmed();
        if (ordersConf.isEmpty()) {
            Text noOrders = new Text();
            noOrders.setText("NON CI SONO ORDINI CONFERMATI!");

            viewVBox.getChildren().add(noOrders);
        } else {
            for (OrderModel o : ordersConf) {
                //short description of a shopping
                Text orderDesc = new Text();
                orderDesc.setText("Ordine n: " + o.getId() + "\nId Fornitore: " + o.getpIvaSupplier());

                viewVBox.getChildren().add(orderDesc);

            }
        }
        commonButton.setVisible(false);
        viewButtonsVBox.setSpacing(5);
    }

}

