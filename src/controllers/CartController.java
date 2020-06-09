package controllers;

import dao.CartDao;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import models.CartModel;
import models.ProductModel;

import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.font.NumericShaper;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    @FXML
    private AnchorPane cartPage;
    @FXML
    private VBox imgVBox;
    @FXML
    private VBox nameCodeVBox;
    @FXML
    private VBox qtyVBox;
    @FXML
    private VBox priceVBox;
    @FXML
    private TextField totalShopping;
    @FXML
    private TextField fidelityPoints;
    @FXML
    private TextField shipping;
    @FXML
    private RadioButton standardRadioButton;
    @FXML
    private RadioButton expressRadioButton;
    @FXML
    private TextField promotion;

    private static CartModel cart;
    private static final File prodImg = new File("");


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
        /*
      /*  for(Object p : products.keySet()){
          //  new ChoiceBox ();
            int qty = products.get(p);
            qtyBox.getItems().add(10);
            for(int i = 1; i <= qty; i++)
                numberQty.addAll(i);
            ProductDao.

        }

        qtyBox.getItems().addAll(numberQty);***********************

        //product image
        ImageView img = new ImageView();
       // img.setImage(new Image("file:///home/king_cheikh/IdeaProjects/SpesaOnline/images/01_melanzane.jpg"));
        img.setImage(new Image("file://" + prodImg.getAbsolutePath() + "/images/01_melanzane.jpg"));
        img.setFitHeight(70);
        img.setFitWidth(120);
        imgVBox.getChildren().add(img);

        //product name & code
        TextField prodNameCode = new TextField();
        prodNameCode.setText("Melanzane " + " 36");
        nameCodeVBox.getChildren().add(prodNameCode);

        //product quantity
        ChoiceBox<Integer> qty = new ChoiceBox<Integer>();
        for(int i = 0; i <= 5; i++)
            qty.getItems().add(i);

        qty.setValue(0);

        qtyVBox.getChildren().add(qty);

        //product price per package
        TextField prodPrice = new TextField();
        prodPrice.setText("€"+"1.5");
        priceVBox.getChildren().add(prodPrice);*/

        cart = new CartModel();
        ProductModel p = new ProductModel(102, "Prova", "BrandProva", 200, "depProva", 5, 5F, 1);
        cart.addToCart(p, 3);

        //product image
        ImageView img = new ImageView();
        String imageName = p.getName();
        int imageId = p.getId();

        img.setImage(new Image("file:///home/king_cheikh/IdeaProjects/SpesaOnline/images/01_melanzane.jpg"));
        //img.setImage(new Image("file://" + prodImg.getAbsolutePath() + "/images/"+ imageId + "_" + imageName +  ".jpg"));
        img.setFitHeight(70);
        img.setFitWidth(120);
        imgVBox.getChildren().add(img);


        //product name & code
        TextField prodNameCode = new TextField();
        prodNameCode.setText(p.getName() + "  " + p.getId());
        nameCodeVBox.getChildren().add(prodNameCode);

        //product total price
        TextField prodPrice = new TextField();
        prodPrice.setText("€"+ cart.getTotalProductPrice(p));
        priceVBox.getChildren().add(prodPrice);

        //product quantity
        ChoiceBox<Integer> qtyBox = new ChoiceBox<>();
        int tmp = CartDao.getQtyInStock(1);
        for(int i = 0; i <= tmp; i++)
            qtyBox.getItems().add(i);

        qtyBox.setValue(3);
        qtyVBox.getChildren().add(qtyBox);

       qtyBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                System.out.println("Trying to change the quantity");
                if(CartDao.getQtyInStock(1) < newValue.intValue()){
                    System.out.println("Quantity not available for " + ov);
                    qtyBox.setValue(oldValue.intValue());
                }
                else if(newValue.intValue() == 0){
                    imgVBox.getChildren().remove(img);
                    nameCodeVBox.getChildren().remove(prodNameCode);
                    qtyVBox.getChildren().remove(qtyBox);
                    priceVBox.getChildren().remove(prodPrice);
                    Text tmp= new Text("Product removed!");
                    nameCodeVBox.getChildren().add(tmp);
                }
                else
                    qtyBox.setValue(newValue.intValue());
            }
        });


        //shipping costs
        //da sistemare...non funziona la selezione delle radio
        String mod = "";
        if(standardRadioButton.isSelected()) {
            mod = "standardShipping";
            shipping.setText(String.valueOf(cart.getShippingCost(mod)));
        }
        else if(expressRadioButton.isSelected()) {
            mod = "expressShipping";
            shipping.setText(String.valueOf(cart.getShippingCost(mod)));
        }


        // promotion.setText(String.valueOf(cart.getPromotion));
        totalShopping.setText(String.valueOf(cart.getTotalShopping(mod))); // mancano i codici promozionali
        fidelityPoints.setText(String.valueOf(cart.getPoints()));
    }

    @FXML
    //collegato alla button di Chiara
    public void setUpCart(ProductModel p, int qty){
        cart.addToCart(p, qty);

        //product image
        ImageView img = new ImageView();
        String imageName = p.getName();
        int imageId = p.getId();

        //img.setImage(new Image("file:///home/king_cheikh/IdeaProjects/SpesaOnline/images/01_melanzane.jpg"));
        img.setImage(new Image("file://" + prodImg.getAbsolutePath() + "/images/"+ imageId + "_" + imageName +  ".jpg"));
        img.setFitHeight(70);
        img.setFitWidth(120);
        imgVBox.getChildren().add(img);


        //product name & code
        TextField prodNameCode = new TextField();
        prodNameCode.setText(p.getName() + "  " + p.getId());
        nameCodeVBox.getChildren().add(prodNameCode);

        //product quantity
        ChoiceBox<Integer> qtyBox = new ChoiceBox<>();
        for(int i = 0; i <= CartDao.getQtyInStock(p.getId()); i++)
            qtyBox.getItems().add(i);

        qtyBox.setValue(qty);
        qtyVBox.getChildren().add(qtyBox);

        qtyBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                System.out.println("Trying to change the quantity");
                if(CartDao.getQtyInStock(p.getId()) < newValue.intValue()){
                    System.out.println("Quantity not available for " + ov);
                    qtyBox.setValue(oldValue.intValue());
                }
                else
                    qtyBox.setValue(newValue.intValue());
            }
        });

        //product total price
        TextField prodPrice = new TextField();
        prodPrice.setText("€"+ cart.getTotalProductPrice(p));
        priceVBox.getChildren().add(prodPrice);

        //shipping costs
        String mod = "";
        if(standardRadioButton.isSelected())
            mod = "standardShipping";
        else if(expressRadioButton.isSelected())
            mod = "expressShipping";
        shipping.setText(String.valueOf(cart.getShippingCost(mod)));

        // promotion.setText(String.valueOf(cart.getPromotion));
        totalShopping.setText(String.valueOf(cart.getTotalShopping(mod))); // mancano i codici promozionali
        fidelityPoints.setText(String.valueOf(cart.getPoints()));
    }
}
