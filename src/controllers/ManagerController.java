package controllers;

import dao.ManagerDao;
import dao.ProductDao;
import dao.ShoppingDao;
import dao.WarehouseDao;
import enums.Tag;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.ProductModel;
import models.ShoppingModel;
import models.WarehouseModel;
import sample.Global;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ManagerController implements Initializable {
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

    private static final List<ShoppingModel> shoppings = ShoppingDao.getAllShoppings();

    ProductModel product;
    WarehouseModel warehouse;
    ChoiceBox<Tag> tags = new ChoiceBox<>();
    int dep = ManagerDao.getDep(Global.USER_ID);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dep == 1)
            reparto.setText("Frutta e verdura");
        else if(dep == 2)
            reparto.setText("Carne");
        else if(dep == 3)
            reparto.setText("Pesce");
        else if(dep == 4)
            reparto.setText("Scatolame");
        else if(dep == 5)
            reparto.setText("Uova e latticini");
        else if(dep == 6)
            reparto.setText("Salumi e formaggi");
        else if(dep == 7)
            reparto.setText("Pane e pasticceria");
        else if(dep == 8)
            reparto.setText("Confezionati");
        else if(dep == 9)
            reparto.setText("Surgelati e gelati");
        else if(dep == 10)
            reparto.setText("Merenda e dolci");
        else if(dep == 11)
            reparto.setText("Acqua bevande e alcolici");
        else if(dep == 12)
            reparto.setText("Igiene");
        else if(dep == 13)
            reparto.setText("Casa");

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
            price = Float.parseFloat(prezzo.getText().replace(',', '.'));
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
            warehouse.setIdProduct(ProductDao.selectLast());
            int resultQuery1 = WarehouseDao.insertWarehouse(warehouse);

            if (resultQuery1 == 0) {
                throw new Exception("Errore nell'inserimento del prodotto nel db");
            }else{
                actionTarget.setText("Prodotto inserito correttamente!");
            }
            nome.clear();
            marca.clear();
            qtyP.setText("1");
            qtyS.setText("1");
            prezzo.setText("1");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
