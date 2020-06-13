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

        cart = new CartModel(messages);

        //converting al png images in jpg image
        PngToJpg.changeExtension();
        System.out.println("Conversion completed");

        List<ProductModel> products = new ArrayList<>();
        products.addAll(ProductDao.getAllProducts());

        for(int j = 0; j < products.size()/4; j++) {
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
            for (int i = 1; i <= tmp; i++)
                qtyBox.getItems().add(i);

            qtyBox.setValue(p.getQtyStock());
            qtyVBox.getChildren().add(qtyBox);

            qtyBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    System.out.println("Trying to change the quantity");
                    cart.setProductQty(p, newValue.intValue());
                    productTotalPrice(prodPrice, p);
                    setShipping();
                    totals();
                }
            });

            Button trash = new Button();
            ImageView trashImage = new ImageView();
            trashImage.setImage(new Image("file://" + prodImg.getAbsolutePath() + "/images/trash.jpg"));
            trashImage.setFitHeight(25);
            trashImage.setFitWidth(25);
            trash.setGraphic(trashImage);
            trashVBox.setAlignment(Pos.CENTER);
            trashVBox.getChildren().add(trash);

            trash.setOnAction(actionEvent -> {cart.remove(p);//non funziona bene
                imgVBox.getChildren().remove(img);
                nameCodeVBox.getChildren().remove(prodNameCode);
                qtyVBox.getChildren().remove(qtyBox);
                trashVBox.getChildren().remove(trash);
                priceVBox.getChildren().remove(prodPrice);
               Text t = new Text("Product removed!");
                //nameCodeVBox.getChildren().add(t);
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

    private void productTotalPrice(TextField prodPrice, ProductModel p){
        prodPrice.setText(String.format("€%.2f", cart.getTotalProductPrice(p)));
    }

    private void totals(){
        // promotion.setText(String.valueOf(cart.getPromotion));
        totalShopping.setText(String.format("€ %.2f", cart.getTotalShopping(mod))); // mancano i codici promozionali
        fidelityPoints.setText(String.valueOf(cart.getPoints() + " punti"));
    }

    @FXML
    //collegato alla button di Chiara
    //mi passa sempre un cart invece di un ProductModel e qty alla volta
    //quando un cliente clicca su aggiunti al carrello,i prodotti vengono aggiunti a un CartModel e compare un messaggio che dice "prodotto aggiunto al carrello" con 2 tasti: uno che dice "vai al carrello" e l'altro che dice "continua con gli acquisti",
    //quando viene cliccato vai al carrello mi passi il CartModel che contengono tutti i prodotti mentre lanci il cart.fxml, mentre se si clicca "continua con gli acquisti" si torna alla pagina dei prodotti...
    // (quindi possiamo fare in modo che il messaggio sia una specie di popup)
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

        if(standardRadioButton.isSelected())
            mod = 1;
        else if(expressRadioButton.isSelected())
            mod = 2;
        shipping.setText(String.valueOf(cart.getShippingCost(mod)));

        // promotion.setText(String.valueOf(cart.getPromotion));
        totalShopping.setText(String.valueOf(cart.getTotalShopping(mod))); // mancano i codici promozionali
        fidelityPoints.setText(String.valueOf(cart.getPoints()));
    }
}
