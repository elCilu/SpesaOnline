package controllers;

import com.jfoenix.controls.JFXButton;
import dao.ProductDao;
import dao.ShoppingDao;
import dao.WarehouseDao;
import enums.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.ProductModel;
import models.ShoppingModel;
import models.WarehouseModel;

import java.util.List;
import java.util.Map;


public class SupplierController {
    @FXML
    private VBox id;
    @FXML
    private VBox deliveryDate;
    @FXML
    private VBox deliveryH;
    @FXML
    private VBox status;
    @FXML
    private TextField nome;
    @FXML
    private TextField marca;
    @FXML
    private TextField qtyP;
    @FXML
    private TextField reparto;
    @FXML
    private TextField qtyS;
    @FXML
    private TextField prezzo;
    @FXML
    private VBox tag;
    @FXML
    private Text actionTarget;

    @FXML
    public void visualizeSendedOrders(){
        refresh();
        newOrderButton.setDisable(false);
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
        Map<Integer, Integer> shoppings = ProductOrderDao.getProductIdAndQtyByOrderId(order.getId());
        for (Integer productId : shoppings.keySet()) {
            ProductModel p = ProductDao.getProductById(productId);

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand() + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            visualizeShoppingVBox.getChildren().add(prodNameCode);

            //product quantity
            Text qty = new Text();
            qty.setText(String.valueOf(shoppings.get(productId)));
            shoppingVBox.getChildren().add(qty);
        }
    }
