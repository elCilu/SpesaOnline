package controllers;

import java.util.List;
import java.util.ArrayList;
import dao.CartDao;
import dao.ProductDao;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import utils.PngToJpg;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CartModel;
import models.ProductModel;

import javax.swing.event.ChangeEvent;
//import java.awt.*;
import java.awt.font.NumericShaper;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

public class CartController implements Initializable {
    @FXML
    private AnchorPane cartPage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane productsPane;
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
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Text messages;

    private static CartModel cart;
    private static final File prodImg = new File("");
    private String mod = "";


    @FXML
    public void cleanCart(){
        cart.removeAll(imgVBox, nameCodeVBox, qtyVBox, priceVBox);
        totalPriceLabel.setVisible(false);
        productsPane.getChildren().removeAll(imgVBox, nameCodeVBox, qtyVBox, priceVBox);
        //Text message = new Text("\t\t\t\n\nIL TUO CARRELLO È VUOTO!");
       // productsPane.getChildren().add(message);
       // message.setX(500);
        //message.setY(200);
        messages.setText("IL TUO CARRELLO È VUOTO!");//trovare il modo di stamparlo proprio al centro della pagina
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

        cart = new CartModel();

        //converting al png images in jpg image
        PngToJpg.changeExtension();
        System.out.println("Conversion completed");

        List<ProductModel> products = new ArrayList<>();
        products.addAll(ProductDao.getAllProducts());

        for(int j = 0; j < products.size(); j++) {
            ProductModel p = products.get(j);
            cart.addToCart(p, p.getQtyStock());

            //product image
            ImageView img = new ImageView();
            img.setImage(new Image("file://" + prodImg.getAbsolutePath() + "/images/" + "prod_" + String.format("%02d", p.getId()) +  ".jpg"));
            img.setFitHeight(70);
            img.setFitWidth(120);
            //img.setPreserveRatio(true);
            imgVBox.getChildren().add(img);

           /* Button delBut = new Button();
            delBut.setText("");
            ImageView cancel = new ImageView(new Image("file:///home/king_cheikh/IdeaProjects/SpesaOnline/images/prod_01.jpg"));
            cancel.setFitHeight(20);
            cancel.setFitWidth(30);
            delBut.setGraphic(cancel);
            qtyVBox.getChildren().add(delBut);*/

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand()  + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            nameCodeVBox.getChildren().add(prodNameCode);

            //product total price
            TextField prodPrice = new TextField();
            prodPrice.setBackground(Background.EMPTY);
            prodPrice.setAlignment(Pos.CENTER);
            prodPrice.setEditable(false);
            productTotalPrice(prodPrice, p);
            priceVBox.getChildren().add(prodPrice);

            //product quantity
            ChoiceBox<Integer> qtyBox = new ChoiceBox<>();
            int tmp = CartDao.getQtyInStock(p.getId());
            for (int i = 0; i <= tmp; i++)
                qtyBox.getItems().add(i);

            qtyBox.setValue(p.getQtyStock());
            qtyVBox.getChildren().add(qtyBox);

            qtyBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    System.out.println("Trying to change the quantity");
                    cart.setProductQty(p, newValue.intValue());
                    totals();

                    if (newValue.intValue() == 0) {
                        cart.remove(p);
                        imgVBox.getChildren().remove(img);
                        nameCodeVBox.getChildren().remove(prodNameCode);
                        qtyVBox.getChildren().remove(qtyBox);
                        priceVBox.getChildren().remove(prodPrice);
                        Text tmp = new Text("Product removed!");
                        nameCodeVBox.getChildren().add(tmp);
                    }
                }
            });
        }


        //shipping costs
        //da sistemare...non funziona la selezione delle radio
        if (standardRadioButton.isSelected()) {
            mod = "standardShipping";
            shipping.setText(String.valueOf(cart.getShippingCost(mod)));
        } else if (expressRadioButton.isSelected()) {
            mod = "expressShipping";
            shipping.setText(String.valueOf(cart.getShippingCost(mod)));
        }

        totals();
    }

    private void productTotalPrice(TextField prodPrice, ProductModel p){
        prodPrice.setText(String.format("€%.2f", cart.getTotalProductPrice(p)));
    }

    private void totals(){
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
