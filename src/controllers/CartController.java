package controllers;

import dao.CartDao;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CartModel;
import models.ProductModel;
import utils.OSystem;
import utils.PngToJpg;

import java.io.File;
import java.net.URL;
import java.util.*;

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
    private VBox trashVBox;
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
    private String path = "";
    private int mod = 0;


    @FXML
    public void cleanCart(){
        if(!cart.getProducts().isEmpty()) {
            cart.removeAll();

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
            messages.setText("IL TUO CARRELLO È VUOTO!");//trovare il modo di stamparlo proprio al centro della pagina in modo dinamico
        }
    }

    @FXML
    public void backToShopping() {
        try {
            Stage stage = (Stage) cartPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/shopping.fxml"));
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void checkout(){
        if(cart.getProducts().isEmpty()){
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
                checkout.setCart(cart, mod);


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

        //build the path
        if(OSystem.isWindows())
            path = "C:\\" + prodImg.getAbsolutePath() + "\\images\\";
        if(OSystem.isUnix())
            path = "file://" + prodImg.getAbsolutePath() + "/images/";
        if(OSystem.isMac())
            path = "";
    }

    @FXML
    public void setShipping(){
        if (standardRadioButton.isSelected()) {
            mod = 1;
            shipping.setText(String.valueOf(cart.getShippingCost(mod)));
            totals();
        }
        if (expressRadioButton.isSelected()) {
            mod = 2;
            shipping.setText(String.valueOf(cart.getShippingCost(mod)));
            totals();
        }
    }

    private void productTotalPrice(Text prodPrice, ProductModel p){
        prodPrice.setText(String.format("€%.2f", cart.getTotalProductPrice(p)));
    }

    private void totals(){
        // promotion.setText(String.valueOf(cart.getPromotion));
        totalShopping.setText(String.format("€ %.2f", cart.getTotalShopping(mod))); // mancano i codici promozionali
        fidelityPoints.setText(cart.getPoints() + " punti");
    }

    public void setUpCart(CartModel cartShopping){
        cart = cartShopping;

        Set<ProductModel> products = cart.getProducts();

        for(ProductModel p : products) {
            //product image
            ImageView img = new ImageView();
            img.setImage(new Image(path + "prod_" + String.format("%02d", p.getId()) +  ".jpg"));
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
            ChoiceBox<Integer> qtyBox = new ChoiceBox<>();
            int tmp = CartDao.getQtyInStock(p.getId());/******************************************qtyStock*************************/
            for (int i = 1; i <= tmp; i++)
                qtyBox.getItems().add(i);

            qtyBox.setValue(p.getQtyStock());
            qtyBox.setPrefSize(50,35);
            qtyVBox.getChildren().add(qtyBox);

            qtyBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Number>) (ov, oldValue, newValue) -> {
                System.out.println("Trying to change the quantity");
                cart.setProductQty(p, newValue.intValue());
                productTotalPrice(prodPrice, p);
                setShipping();
                totals();
            });

            Button trash = new Button();
            ImageView trashImage = new ImageView();
            trashImage.setImage(new Image(path + "trash.jpg"));
            trashImage.setFitHeight(25);
            trashImage.setFitWidth(25);
            trash.setGraphic(trashImage);
            trashVBox.setAlignment(Pos.CENTER);
            trashVBox.getChildren().add(trash);

            trash.setOnAction(actionEvent -> {cart.remove(p);//non funziona bene forse
                imgVBox.getChildren().remove(img);
                nameCodeVBox.getChildren().remove(prodNameCode);
                qtyVBox.getChildren().remove(qtyBox);
                trashVBox.getChildren().remove(trash);
                priceVBox.getChildren().remove(prodPrice);
                //Text t = new Text("Product removed!");
                messages.setText("Product removed!");
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

        if(!cart.getProducts().isEmpty())
            messages.setText("");

        /*cart.addToCart(p, qty);

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

        if(standardRadioButton.isSelected())
            mod = 1;
        else if(expressRadioButton.isSelected())
            mod = 2;
        shipping.setText(String.valueOf(cart.getShippingCost(mod)));

        // promotion.setText(String.valueOf(cart.getPromotion));
        totalShopping.setText(String.valueOf(cart.getTotalShopping(mod))); // mancano i codici promozionali
        fidelityPoints.setText(String.valueOf(cart.getPoints()));*/
    }
}
