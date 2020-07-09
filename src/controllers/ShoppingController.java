package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import dao.ProductDao;
import enums.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.CartModel;
import models.ProductModel;
import javafx.scene.image.ImageView;
import utils.OSystem;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.*;

public class ShoppingController implements Initializable {

    @FXML
    public VBox shoppingPage;
    @FXML
    public JFXTextField searchField;           //barra di ricerca
    //@FXML
    //public TextField qtyChoice;             //quantità aggiunta da utente
    @FXML
    public ListView<String> depField;       //menu a tendina a sx
    @FXML
    public ChoiceBox<String> sortBy;         //ordina per prezzo/alfabetico
    @FXML
    public JFXCheckBox bioFilter;              //filtra prodotti bio
    @FXML
    public JFXCheckBox glutenFreeFilter;       //filtra prodotti no glutine
    @FXML
    public JFXCheckBox dairyFreeFilter;        //filtra prodotti no lattosio
    @FXML
    public ImageView logoImage, userImage, cartImage;
    @FXML
    private VBox imgVBox;
    @FXML
    private VBox nameCodeVBox;
    @FXML
    private VBox qtyVBox;
    @FXML
    private VBox addVBox;
    @FXML
    private VBox priceVBox;

    private static List<ProductModel> products = ProductDao.getAllProducts();
    private static final File prodImg = new File("");
    private CartModel cart = new CartModel();
    private String path = "";

    //TODO: alcuni prodotti non vengono visualizzati, sistema tag e allinea grafica
    //cambia png in jpg rummo, cambia query di ricerca



    protected void setPath(){
        if(OSystem.isWindows())
            path = "C:\\" + prodImg.getAbsolutePath() + "\\images\\";
        if(OSystem.isUnix())
            path = "file://" + prodImg.getAbsolutePath() + "/images/";
    }

    @FXML
    protected void filterByTag() {   /*SISTEMA!*/

        if (bioFilter.isSelected()) {
            if(!glutenFreeFilter.isSelected()) {
                if (dairyFreeFilter.isSelected()) {
                    products = ProductDao.getProductsByTag(Tag.BIO_DAIRY_FREE.ordinal());
                } else {
                    products = ProductDao.getProductsByTag(Tag.BIO.ordinal());
                }
            } else {
                if (dairyFreeFilter.isSelected()) {
                    products = ProductDao.getProductsByTag(Tag.BIO_GLUTEN_FREE_DAIRY_FREE.ordinal());
                } else {
                    products = ProductDao.getProductsByTag(Tag.BIO_GLUTEN_FREE.ordinal());
                }
            }
        }
        //se il prodotto è solo senza glutine (enum in posizione 2)
        else if ((!bioFilter.isSelected()) && glutenFreeFilter.isSelected() && !dairyFreeFilter.isSelected()) {
            products = ProductDao.getProductsByTag(Tag.GLUTEN_FREE.ordinal());
        }
        //se il prodotto è solo senza lattosio (enum in posizione 3)
        else if (!bioFilter.isSelected() && !glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()) {
            products = ProductDao.getProductsByTag(Tag.DAIRY_FREE.ordinal());
        }
        //se il prodotto è bio e senza glutine (enum in posizione 4)
        else if (bioFilter.isSelected() && glutenFreeFilter.isSelected() && !dairyFreeFilter.isSelected()) {
            products = ProductDao.getProductsByTag(Tag.BIO_GLUTEN_FREE.ordinal());
        }
        //se il prodotto è bio e senza lattosio (enum in posizione 5)
        else if (bioFilter.isSelected() && !glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()) {
            products = ProductDao.getProductsByTag(Tag.BIO_DAIRY_FREE.ordinal());
        }
        //se il prodotto è senza glutine e senza lattosio (enum in posizione 6)
        else if ((!bioFilter.isSelected()) && glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()) {
            products = ProductDao.getProductsByTag(Tag.GLUTEN_FREE_DAIRY_FREE.ordinal());
        }
        //se seleziono tutti i tag (enum in posizione 7)
        else if (bioFilter.isSelected() && glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()) {
            products = ProductDao.getProductsByTag(Tag.BIO_GLUTEN_FREE_DAIRY_FREE.ordinal());
        } else {
            products = ProductDao.getAllProducts();
        }
        refresh();
        loadData();
    }

    @FXML
    protected void sortBy() {
        String sorted = sortBy.getSelectionModel().getSelectedItem();
        switch (sorted) {
            case "Prezzo cresc.":
                products = ProductDao.getIncreasingOrder();
                break;
            case "Prezzo decresc.":
                products = ProductDao.getDecreasingOrder();
                break;
            case "Alfabetico":
                products = ProductDao.getAlphabeticOrder();
                break;
            default:
                products = ProductDao.getAllProducts();
                break;
        }
        refresh();
        loadData();
    }

    @FXML
    protected void getProdByDep(){
        String choice = depField.getSelectionModel().getSelectedItem();
        if(choice.equals("Tutto"))
            products = ProductDao.getAllProducts();
        else if(choice.equals("Frutta e verdura"))
            products = ProductDao.getProductsByDep("Frutta e verdura");
        else if(choice.equals("Carne"))
            products = ProductDao.getProductsByDep("Carne");
        else if(choice.equals("Pesce"))
            products = ProductDao.getProductsByDep("Pesce");
        else if(choice.equals("Scatolame"))
            products = ProductDao.getProductsByDep("Scatolame");
        else if(choice.equals("Uova e latticini"))
            products = ProductDao.getProductsByDep("Uova e latticini");
        else if(choice.equals("Salumi e formaggi"))
            products = ProductDao.getProductsByDep("Salumi e formaggi");
        else if(choice.equals("Pane e pasticceria"))
            products = ProductDao.getProductsByDep("Pane e pasticceria");
        else if(choice.equals("Confezionati"))
            products = ProductDao.getProductsByDep("Confezionati");
        else if(choice.equals("Surgelati e gelati"))
            products = ProductDao.getProductsByDep("Surgelati e gelati");
        else if(choice.equals("Merenda e dolci"))
            products = ProductDao.getProductsByDep("Merenda e dolci");
        else if(choice.equals("Acqua bevande e alcolici"))
            products = ProductDao.getProductsByDep("Acqua bevande e alcolici");
        else if(choice.equals("Igiene"))
            products = ProductDao.getProductsByDep("Igiene");
        else if(choice.equals("Casa"))
            products = ProductDao.getProductsByDep("Casa");
        refresh();
        loadData();
    }

    @FXML
    protected void cartButton(){
        try {
            Stage stage = (Stage) shoppingPage.getScene().getWindow();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("../views/cart.fxml"));

            //load the parent
            Loader.load();
            CartController cartController = Loader.getController();

            //sending cart to the cart page
            cartController.setUpCart(cart);

            stage.setScene(new Scene(Loader.getRoot()));
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void userButton(){
        try {
            Stage stage = (Stage) shoppingPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/costumerData.fxml"));
            stage.setScene(new Scene(root, 400, 350));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {

        //clear delle vBox
        imgVBox.getChildren().clear();
        nameCodeVBox.getChildren().clear();
        qtyVBox.getChildren().clear();
        priceVBox.getChildren().clear();
        addVBox.getChildren().clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //gestione barra di ricerca
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1){
                products = ProductDao.searchBy(searchField.getText());
            } else if (newValue.equals("")){
                products = ProductDao.getAllProducts();
            }
            refresh();
            loadData();
        });

        //carico logo
        setPath();
        logoImage.setImage(new Image(path +  "shopping_logo.jpg"));

        //setta button/collegamento pagina anagrafica
        userImage.setImage(new Image(path +  "user_image.png"));
        userImage.setFitHeight(50);
        userImage.setFitWidth(50);
        userImage.setOnMouseClicked(mouseEvent -> userButton());

        //setta button/collegamento pagina carrello
        cartImage.setImage(new Image(path + "cart_image.png"));
        cartImage.setFitHeight(50);
        cartImage.setFitWidth(50);
        cartImage.setOnMouseClicked(mouseEvent -> cartButton());

        //gestione dinamica della pagina
        loadData();
    }

    //carico i dati nella pagina
    private void loadData() {

        for (ProductModel p : products) {

            //product image
            ImageView img = new ImageView();
            img.setImage(new Image(path + "prod_" + String.format("%02d", p.getId()) + ".jpg"));
            img.setFitHeight(70);
            img.setFitWidth(120);
            imgVBox.getChildren().add(img);

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand() + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            nameCodeVBox.getChildren().add(prodNameCode);

            //prezzo unitario
            Text unitPrice = new Text();
            unitPrice.setText(String.format("€ %.02f", p.getprice()));
            priceVBox.getChildren().add(unitPrice);

            //aggiungi textfield per quantità
            TextField qty = new TextField();
            qty.setPromptText("qtà");
            qty.setMaxSize(50, 5);
            qtyVBox.getChildren().add(qty);
            qtyVBox.setSpacing(55);

            //add button
            JFXButton addButton = new JFXButton();
            addButton.setStyle("-fx-background-color: F9AA33");
            ImageView addImage = new ImageView();
            addImage.setImage(new Image(path + "add_button.png"));
            addImage.setFitHeight(25);
            addImage.setFitWidth(25);
            addButton.setGraphic(addImage);
            addButton.setAlignment(Pos.CENTER);
            addVBox.getChildren().add(addButton);

            //gestione textfield qtyChoice
            addButton.setOnMouseClicked(mouseEvent -> cart.addToCart(p, Integer.parseInt(qty.getText())));
        }


    }

}
