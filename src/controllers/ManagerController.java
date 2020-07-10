package controllers;

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


public class ManagerController {
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

    private static List<ShoppingModel> shoppings = ShoppingDao.getShopping();

    ProductModel product;
    WarehouseModel warehouse;
    ChoiceBox<Tag> tags = new ChoiceBox<>();


    //visualizza lo stato delle spese
    protected void showStatus(){
        //setto a 1 le textfield che devono ricevere numeri
        qtyP.setText("1");
        qtyS.setText("1");
        prezzo.setText("1");
        //setta la choicebox dei tag
        for (Tag t : Tag.values())
            tags.getItems().add(t);
        tag.getChildren().add(tags);

        for (ShoppingModel s : shoppings) {
            //shopping id
            Text shoppingId = new Text();
            shoppingId.setText(String.format("%d", s.getId()));
            id.getChildren().add(shoppingId);

            //delivery date
            Text shoppingDelivery = new Text();
            shoppingDelivery.setText(String.format("%s", s.getDeliveryDate()));
            deliveryDate.getChildren().add(shoppingDelivery);

            //delivery time
            Text shoppingDeliveryTime = new Text();
            shoppingDeliveryTime.setText(String.format("%s", s.getDeliveryH()));
            deliveryH.getChildren().add(shoppingDeliveryTime);

            //shopping status
            Text shoppingStatus = new Text();
            shoppingStatus.setText(String.format("%s", s.getStatus()));
            status.getChildren().add(shoppingStatus);
        }

    }

    //inserimento nuovo prodotto nel db
    @FXML
    protected void addNewProduct(){
        String name;
        String brand;
        int qtyPack;
        String dep;
        int qtyStock;
        float price;
        Tag shoppingTag;

        try {
            name = nome.getText();
            brand = marca.getText();
            dep = reparto.getText();
            qtyPack = Integer.parseInt(qtyP.getText());
            qtyStock = Integer.parseInt(qtyS.getText());
            price = Float.parseFloat(prezzo.getText());
            shoppingTag = tags.getValue();

            if (name.isEmpty() || brand.isEmpty() || dep.isEmpty() ||  qtyPack <= 0 || qtyStock < 0 || price <= 0) {
                actionTarget.setText("Tutti i campi sono obbligatori");
                throw new Exception("Campi del sign up vuoti");
            }

            // inserisco prodotto nel db
            product = new ProductModel(0, name, brand, qtyPack, dep, qtyStock, price, shoppingTag);
            int resultQuery = ProductDao.insertProduct(product);

            if (resultQuery == 0) {
                throw new Exception("Errore nell'inserimento del prodotto nel db");
            }

            // inserisco qty nel
            warehouse = new WarehouseModel(0, qtyStock, 20, 100);
            int resultQuery1 = WarehouseDao.insertWarehouse(warehouse);

            if (resultQuery1 == 0) {
                throw new Exception("Errore nell'inserimento del prodotto nel db");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
