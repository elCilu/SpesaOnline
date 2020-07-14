package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dao.ProductDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ProductModel;
import sample.Global;
import utils.PngToJpg;


import java.net.URL;
import java.util.*;

public class CartController implements Initializable {
    @FXML
    private ScrollPane cartPage;
    @FXML
    private VBox imgVBox;
    @FXML
    private VBox nameCodeVBox;
    @FXML
    private VBox qtyVBox;
    @FXML
    private VBox trashVBox;
    @FXML
    private VBox priceVBox;
    @FXML
    private JFXTextField totalShopping;
    @FXML
    private JFXTextField subTotal;
    @FXML
    private JFXTextField fidelityPoints;
    @FXML
    private JFXTextField shipping;
    @FXML
    private JFXRadioButton standardRadioButton;
    @FXML
    private JFXRadioButton expressRadioButton;
    @FXML
    private JFXTextField promotion;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Text messages;

    private int mod = 0;


    @FXML
    public void cleanCart(){
        if(!Global.cart.keySet().isEmpty()) {
            Global.cart.clear();

            //clear delle vBox
            imgVBox.getChildren().clear();
            nameCodeVBox.getChildren().clear();
            qtyVBox.getChildren().clear();
            priceVBox.getChildren().clear();
            trashVBox.getChildren().clear();

            //clear del totale del carrello e dei punti fedeltà
            totalShopping.setText("00.0");
            fidelityPoints.setText("00.0");

            totalPriceLabel.setVisible(false);
            messages.setText("IL TUO CARRELLO È VUOTO!");
        }
    }

    @FXML
    public void backToShopping() {
        try {
            Stage stage = (Stage) cartPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/shopping.fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void checkout(){
        if(Global.cart.keySet().isEmpty()){
            System.out.println("Il tuo carrello è vuoto!");
        }
        else {

            try {
                Stage stage = (Stage) cartPage.getScene().getWindow();
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("../views/checkout.fxml"));

                //load the parent
                Loader.load();
                CheckOutController checkout = Loader.getController();

                //sending cart to the checkout page
                checkout.setCart(mod);


                stage.setScene(new Scene(Loader.getRoot()));
                stage.sizeToScene();
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    //carico i dati iniziali sulla pagina
    private void loadData(){
        //converting al png images in jpg image
        PngToJpg.changeExtension();
        System.out.println("Conversion completed");
    }

    @FXML
    public void setShipping(){

        if (standardRadioButton.isSelected()) {
            mod = 1;
            shipping.setText(String.valueOf(getShippingCost(mod)));
            totals();
        }
        if (expressRadioButton.isSelected()) {
            mod = 2;
            shipping.setText(String.valueOf(getShippingCost(mod)));
            totals();
        }
    }

    private void productTotalPrice(Text prodPrice, ProductModel p){
        prodPrice.setText(String.format("€%.2f", p.getprice() * Global.cart.get(p)));
    }

    private void totals(){
        // promotion.setText(String.valueOf(cart.getPromotion));
        subTotal.setText(String.format("€ %.2f", subTotal()));
        totalShopping.setText(String.format("€ %.2f", subTotal() + getShippingCost(mod))); // mancano i codici promozionali
        fidelityPoints.setText((int) subTotal() + " punti");
    }

    public void setUpCart(){
        for(ProductModel p : Global.cart.keySet()) {
            //product image
            ImageView img = new ImageView();
            img.setImage(new Image(Global.IMG_PATH + "prod_" + String.format("%02d", p.getId()) +  ".jpg"));
            img.setFitHeight(70);
            img.setFitWidth(120);
            imgVBox.getChildren().add(img);

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand()  + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            nameCodeVBox.getChildren().add(prodNameCode);

            //product total price
            Text prodPrice = new Text();
            productTotalPrice(prodPrice, p);
            priceVBox.getChildren().add(prodPrice);

            //product quantity
            Spinner<Integer> qtyBox = new Spinner<>();
            qtyBox.setValueFactory(new IntegerSpinnerValueFactory(1, ProductDao.getQtyInStock(p.getId()), Global.cart.get(p)));
            qtyBox.setOnMouseClicked(mouseEvent -> {
                System.out.println("Trying to change the quantity");
                Global.cart.replace(p, Global.cart.get(p), qtyBox.getValue());
                productTotalPrice(prodPrice, p);
                setShipping();
                totals();
            });

            qtyBox.setPrefSize(65,35);
            qtyVBox.getChildren().add(qtyBox);

            JFXButton trash = new JFXButton();
            ImageView trashImage = new ImageView();
            trashImage.setImage(new Image(Global.IMG_PATH + "trash.jpg"));
            trashImage.setFitHeight(25);
            trashImage.setFitWidth(25);
            trash.setGraphic(trashImage);
            trash.setButtonType(JFXButton.ButtonType.RAISED);
            trashVBox.setAlignment(Pos.CENTER);
            trashVBox.getChildren().add(trash);

            trash.setOnAction(actionEvent -> {Global.cart.remove(p, Global.cart.get(p));//non funziona bene forse
                imgVBox.getChildren().remove(img);
                nameCodeVBox.getChildren().remove(prodNameCode);
                qtyVBox.getChildren().remove(qtyBox);
                trashVBox.getChildren().remove(trash);
                priceVBox.getChildren().remove(prodPrice);
                totals();
            });
        }

        standardRadioButton.setSelected(true);
        setShipping();
        shipping.setBackground(Background.EMPTY);

        totals();
        totalShopping.setBackground(Background.EMPTY);
        promotion.setBackground(Background.EMPTY);
        fidelityPoints.setBackground(Background.EMPTY);

        if(!Global.cart.keySet().isEmpty())
            messages.setText("");

    }

    private float subTotal(){
        float tot = 0;

        for(ProductModel p: Global.cart.keySet())
            tot += p.getprice() * Global.cart.get(p);

        return tot;
    }

    private float getShippingCost(int mod) {
        float shippingCost;
        if((subTotal() >= 50 && (mod == 0 || mod == 1)) || Global.cart.isEmpty())
            shippingCost = 0.0F;
        else if(mod == 1)
            shippingCost = 5.99F;
        else
            shippingCost = 10.99F;
        return shippingCost;
    }
}
