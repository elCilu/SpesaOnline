package controllers;

import com.jfoenix.controls.JFXButton;
import dao.*;
import enums.Status;
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


//aggiungere la colonna status
//aggiungere la tabella SupplierOrderDao
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
        if(OSystem.isWindows())
            path = "C:\\" + prodImg.getAbsolutePath() + "\\images\\";
        if(OSystem.isUnix())
            path = "file://" + prodImg.getAbsolutePath() + "/images/";
        if(OSystem.isMac())
            path = "";

        //logo.setImage(new Image(path + "warehouse.png"));
        loadPage();
    }

    public void loadPage(){
        refresh();
        viewVBox.getChildren().clear();
        viewButtonsVBox.getChildren().clear();
        newOrderButton.setDisable(false);
        for(ShoppingModel shopping : ShoppingDao.getAllShoppings()) {
            //short description of a shopping
            Text shDescription = new Text();
            shDescription.setText("Spesa n: " + shopping.getId() +
                                " \nConsegna: " + shopping.getDeliveryDate() + " " + shopping.getDeliveryH()
                                + "\nStato: " + shopping.getStatus());

            viewVBox.getChildren().add(shDescription);

            //visualize button
            JFXButton viewButton = new JFXButton();
            viewButton.setText("VISUALIZZA");
            viewButton.setOnAction(actionEvent -> {
                viewShopping(shopping);
            });
            viewButton.setStyle("-fx-background-color:  #FFD700");
            viewButton.setButtonType(JFXButton.ButtonType.RAISED);
            viewButtonsVBox.getChildren().add(viewButton);
        }
        commonButton.setStyle("-fx-background-color:  #FFD700");
        commonButton.setVisible(false);
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
        commonButton.setText("CONFERMA");
        //Button statusButton = new Button();
        commonButton.setOnAction(actionEvent -> {
            modifyStatus(shopping);
            commonButton.setDisable(true);
        });
        commonButton.setVisible(true);
        //shoppingVBox.getChildren().add(statusButton);
    }


    //modifica stato della spesa (da confermato a in consegna)
    public void modifyStatus(ShoppingModel shopping) {
        //magazziniere controlla che la spesa deve essere spedita quel giorno
        if(shopping.getStatus().equals(Status.CONFIRMED)) {
            shopping.setStatus(Status.DELIVERING);
            status.setText("PRONTO");
            updateShoppingStatus(shopping.getId(), shopping.getStatus().ordinal());
        }
    }

    public void updateShoppingStatus(int id, int status){
        try {
            int resultQuery = ShoppingDao.updateStatus(id, status);
            if (resultQuery == 0)
                throw new Exception("Errore nell'iaggiornamento dello stato dello shopping nel db");

        } catch(Exception e){
            e.printStackTrace();
        }
    }



    public void checkProducts(){
        productsToRequest.clear();
        for(ProductModel p : products){
            //check if under the limit
            if(ProductDao.getQtyInStock(p.getId()) < QTYMIN){
                //order the remaining qty
                productsToRequest.put(p, (QTYMAX - p.getQtyStock()));
            }
        }
    }

    @FXML
    public void visualizeOrder(){
        viewVBox.getChildren().clear();
        visualizeProductsPerDep();
        //refresh of containers
        refresh();
        viewButtonsVBox.getChildren().clear();

        for(ProductModel p: productsToRequest.keySet()){
            //set the name of the product
            Text product = new Text();
            product.setText(p.getName());
            viewButtonsVBox.getChildren().add(product);

            //set the quantity
            Text qty = new Text();
            qty.setText(String.valueOf(productsToRequest.get(p)));
            qtyVBox.getChildren().add(qty);
        }
        viewButtonsVBox.setSpacing(0);

        //confirmation button
        commonButton.setText("INVIA");
        commonButton.setOnAction(actionEvent -> {
            //send the order
            sendOrder();
            refresh();
            viewVBox.getChildren().clear();
            viewButtonsVBox.getChildren().clear();
            loadPage();
            message.setText("ORDINE INVIATO!");
            message.autosize();
            visualizeShoppingVBox.getChildren().add(message);
            newOrderButton.setDisable(true);
        });
        if(depProducts.getSelectionModel().getSelectedItem() == null || depProducts.getSelectionModel().getSelectedItem().compareTo("Tutto") == 0)
            commonButton.setDisable(true);
        else {
            commonButton.setDisable(false);
        }
        commonButton.setVisible(true);
        newOrderButton.setDisable(true);
    }

    //invia ordine a fornitore
    public void sendOrder(){
        OrderModel order = new OrderModel(0, 1/*getSupplier().getpIva()*/, 1/*GlobalVars.STOCK_MAN_ID*/);
        //manda ordine per reparto....
        try {
            int resultQuery = OrderDao.insertOrder(order);

            if (resultQuery == 0) {
                throw new Exception("Errore nell'inserimento dell'ordine in Orders");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        order.setId(OrderDao.selectLast());

        for(ProductModel p : productsToRequest.keySet()){
            ProductOrderModel productOrder = new ProductOrderModel(0, p.getId(), order.getId(), productsToRequest.get(p));
            try {
                int resultQuery = ProductOrderDao.insertProductOrder(productOrder);

                if (resultQuery == 0) {
                    throw new Exception("Errore nell'inserimento del prodotto in productsOrder");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void visualizeProductsPerDep() {
        viewVBox.getChildren().clear();
        viewButtonsVBox.getChildren().clear();
        depProducts.getItems().clear();

        depProducts.getItems().add("Tutto");
        depProducts.getItems().add("Acqua bevande e alcolici");
        depProducts.getItems().add("Carne");
        depProducts.getItems().add("Casa");
        depProducts.getItems().add("Confezionati");
        depProducts.getItems().add("Frutta e verdura");
        depProducts.getItems().add("Igiene");
        depProducts.getItems().add("Merenda e dolci");
        depProducts.getItems().add("Pane e pasticceria");
        depProducts.getItems().add("Pesce");
        depProducts.getItems().add("Salumi e formaggi");
        depProducts.getItems().add("Scatolame");
        depProducts.getItems().add("Surgelati e gelati");
        depProducts.getItems().add("Uova e latticini");
        viewVBox.getChildren().add(depProducts);
        final SupplierModel[] supplier = {null};
        depProducts.setOnMouseClicked(mouseEvent -> {
            int[] bits = {0, 0, 0};
            products = ProductDao.select(
                     "", 0, depProducts.getSelectionModel().getSelectedItem() == null ?
                            "Tutto" : depProducts.getSelectionModel().getSelectedItem(), bits);
            getSupplier();
            refresh();
            checkProducts();
            updateVisualizeOrders();
            Text suppName= new Text("Fornitore: " + supplier[0].getCompanyName());
            Text suppPIva = new Text("Id Fornitore: " + supplier[0].getpIva());
            visualizeShoppingVBox.getChildren().addAll(suppName, suppPIva);

            if(depProducts.getSelectionModel().getSelectedItem().compareTo("Tutto") == 0)
                commonButton.setDisable(true);
            else
                commonButton.setDisable(false);
        });
    }

    private SupplierModel getSupplier(){
        SupplierModel supplier = null;
        String dep = depProducts.getSelectionModel().getSelectedItem();

        if(dep == "Frutta e verdura")
            supplier = SupplierDao.getSupplier(1);
        else if(dep == "Carne")
            supplier = SupplierDao.getSupplier(2);
        else if(dep == "Pesce")
            supplier = SupplierDao.getSupplier(3);
        else if(dep == "Scatolame")
            supplier = SupplierDao.getSupplier(4);
        else if(dep == "Uova e latticini")
            supplier = SupplierDao.getSupplier(5);
        else if(dep == "Salumi e formaggi")
            supplier = SupplierDao.getSupplier(6);
        else if(dep == "Pane e pasticceria")
            supplier = SupplierDao.getSupplier(7);
        else if(dep == "Confezionati")
            supplier = SupplierDao.getSupplier(8);
        else if(dep == "Surgelati e gelati")
            supplier = SupplierDao.getSupplier(9);
        else if(dep == "Merenda e dolci")
            supplier = SupplierDao.getSupplier(10);
        else if(dep == "Acqua bevande e alcolici")
            supplier = SupplierDao.getSupplier(12);
        else if(dep == "Igiene")
            supplier = SupplierDao.getSupplier(13);
        else if(dep == "Casa")
            supplier = SupplierDao.getSupplier(14);

        return supplier;
    }

    public void updateVisualizeOrders() {
        //refresh of containers
        refresh();
        viewButtonsVBox.getChildren().clear();
        if (productsToRequest.isEmpty()){
            Text noProducts = new Text();
            noProducts.setText("NON CI SONO PRODOTTI DA ORDINARE!");
            viewButtonsVBox.getChildren().add(noProducts);
        }
        else{
            for (ProductModel p : productsToRequest.keySet()) {
                //set the name of the product
                Text product = new Text();
                product.setText(p.getName());
                viewButtonsVBox.getChildren().add(product);

                //set the quantity
                Text qty = new Text();
                qty.setText(String.valueOf(productsToRequest.get(p)));
                qtyVBox.getChildren().add(qty);
            }
            viewButtonsVBox.setSpacing(0);

            //confirmation button
            commonButton.setText("INVIA");
            commonButton.setOnAction(actionEvent -> {
                //send the order
                sendOrder();
                refresh();
                viewVBox.getChildren().clear();
                viewButtonsVBox.getChildren().clear();
                loadPage();
                message.setText("ORDINE INVIATO!");
                message.autosize();
                visualizeShoppingVBox.getChildren().add(message);
                newOrderButton.setDisable(true);
            });

            if (depProducts.getSelectionModel().getSelectedItem() == null || depProducts.getSelectionModel().getSelectedItem().compareTo("Tutto") == 0)
                commonButton.setDisable(true);
            else {
                commonButton.setDisable(false);
            }
            commonButton.setVisible(true);
        }

        newOrderButton.setDisable(true);
    }

    void refresh(){
        //refresh of containers
        visualizeShoppingVBox.getChildren().clear();
        shoppingVBox.getChildren().clear();
        qtyVBox.getChildren().clear();
        commonButton.setVisible(false);
    }

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

}

