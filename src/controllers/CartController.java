package controllers;

import dao.CartDao;
import dao.ProductDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import models.CartModel;
import models.ProductModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.SplittableRandom;
import java.util.TreeMap;

public class CartController implements Initializable {
    @FXML
    private AnchorPane cartPage;
    @FXML
    private TextField prodName;
    @FXML
    private TextField prodCode;
    @FXML
    private TextField unitPrice;
    @FXML
    private TextField totalShopping;
    @FXML
    private TextField fidelityPoints;
    @FXML
    private ImageView img;
    @FXML
    private ChoiceBox qtyBox;
    @FXML
    private TextField shipping;
    @FXML
    private RadioButton standardRadioButton;
    @FXML
    private RadioButton expressRadioButton;
    @FXML
    private TextField promotion;


    CartModel cart;
    //ObservableList numberQty = FXCollections.observableArrayList();


    @FXML
    public void cleanCart(){
        cart.removeAll();
    }

    @FXML
    public void backToShopping() {
        try {
            Stage stage = (Stage) cartPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/Shopping.fxml"));
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void checkout(){
        try {
            Stage stage = (Stage) cartPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/Checkout.fxml"));
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    //carico i dati iniziali sulla pagina
    private void loadData(){
       // numberQty.removeAll(numberQty);
        //devo chiedere al database la quantità disponibile del prodotto
      /*  int a = 1, b = 2, c = 3, d =4;
        numberQty.addAll(a, b, c, d);

        for(Object p : products.keySet()){
          //  new ChoiceBox ();
            int qty = products.get(p);
            qtyBox.getItems().add(10);
            for(int i = 1; i <= qty; i++)
                numberQty.addAll(i);
            ProductDao.

        }

        qtyBox.getItems().addAll(numberQty);*/
        URL path = getClass().getResource("../../images/01_melanzane.jpg");

        //Image image = new Image("file:///home/king_cheikh/IdeaProjects/SpesaOnline/images/01_melanzane.jpg");//*************************************
        Image image = new Image(String.valueOf(path));
        img.setImage(image);

        for(int i = 0; i <= 5; i++)
            qtyBox.getItems().add(i);

        qtyBox.setValue(0);

        if((int)qtyBox.getValue() == 0){
           System.out.print("la quantità selezionata è 0");
        }

        cart = new CartModel();
    }

    @FXML
    /**
     * collegato al button della chiara
     */
    public void setUpCart(){
        //cart.addToCart(/*....*/);

        /*String imageName;
        int imageId;
        imageId = prodotto.getId();
        imageName = prodotto.getName();
        Image image = new Image("file:///home/king_cheikh/IdeaProjects/SpesaOnline/images/" + String.valueOf(imageId) + "_" + imageName +  ".jpg");*/
        Image image = new Image("file:///home/king_cheikh/IdeaProjects/SpesaOnline/images/01_melanzane.jpg");
        img.setImage(image);

        /*prodName = campoDellaChiara.getText();
        prodCode = campoDellaChiara.getText();
        unitPrice.setText(p.getprice());  -------------------- p = prodotto
        */
        String mod = "";
        if(standardRadioButton.isSelected())
            mod = "standardShipping";
        else if(expressRadioButton.isSelected())
            mod = "expressShipping";

        shipping.setText(String.valueOf(cart.getShippingCost(mod)));
       // promotion.setText(String.valueOf(cart.getPromoiton));
        totalShopping.setText(String.valueOf(cart.getTotalShopping())); // rivedere il metodo getTotalShopping
        fidelityPoints.setText(String.valueOf(cart.getPoints()));
      /*  int qtyStock = CartDao.getQtyInStock(p.getID());

        for(int i = 0; i <= qtyStock; i++)
            qtyBox.getItems().add(i);

        qtyBox.setValue(cart.getProductQty(p));

        //devo collegarelo all'evento se qtyBox viene messo a 0 di rimuovere il prodotto con il metodo dentro cartModel
        if((int)qtyBox.getValue() == 0){
            System.out.print("la quantità selezionata è 0");
        }*/
    }
}
