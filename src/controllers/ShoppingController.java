package controllers;

import com.jfoenix.controls.*;
import dao.ProductDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ProductModel;
import sample.Global;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {

    @FXML
    public VBox shoppingPage;
    @FXML
    public JFXTextField searchField;           //barra di ricerca
    @FXML
    public JFXListView<String> depField;       //menu a tendina a sx
    @FXML
    public JFXComboBox<String> sortBy;         //ordina per prezzo/alfabetico
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

    private static List<ProductModel> products = ProductDao.select("", 0, "Tutto", new int[]{0, 0, 0});

    //TODO: sistema tag

    protected int[] byTag() {
        int[] bits = {0, 0, 0};
        if (bioFilter.isSelected()) {
            bits[0] = 1;
        }
        if (glutenFreeFilter.isSelected()) {
            bits[1] = 1;
        }
        if (dairyFreeFilter.isSelected()) {
            bits[2] = 1;
        }
        return bits;
    }

    private int sortBy() {
        int indexSort = 0;
        String sorted = sortBy.getSelectionModel().getSelectedItem();
        switch (sorted) {
            case "Prezzo cresc." -> indexSort = 1;
            case "Prezzo decresc." -> indexSort = 2;
            case "Alfabetico" -> indexSort = 3;
        }
        return indexSort;
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
            cartController.setUpCart();

            stage.setScene(new Scene(Loader.getRoot()));
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void userButton(){
        Global.changeScene(shoppingPage, "customerData");
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
        searchField.textProperty().addListener((observable, oldValue, newValue) -> select());

        logoImage.setImage(new Image(Global.IMG_PATH +  "shopping_logo.jpg"));
        userImage.setImage(new Image(Global.IMG_PATH +  "user_image.png"));
        cartImage.setImage(new Image(Global.IMG_PATH + "cart_image.png"));
        //gestione dinamica della pagina

        loadData();
    }

    //carico i dati nella pagina
    private void loadData() {
        for (ProductModel p : products) {
            //product image
            ImageView img = new ImageView();
            img.setImage(new Image(Global.IMG_PATH + "prod_" + String.format("%02d", p.getId()) + ".jpg"));
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
            qty.setText("1");
            qty.setPromptText("qtà");
            qty.setMaxSize(50, 5);
            qtyVBox.getChildren().add(qty);
            qtyVBox.setSpacing(55);

            //add button
            JFXButton addButton = new JFXButton();
            addButton.setStyle("-fx-background-color: F9AA33");
            ImageView addImage = new ImageView();
            addImage.setImage(new Image(Global.IMG_PATH + "add_button.png"));
            addImage.setFitHeight(25);
            addImage.setFitWidth(25);
            addButton.setGraphic(addImage);
            addButton.setAlignment(Pos.CENTER);
            addVBox.getChildren().add(addButton);

            //gestione textfield qtyChoice
            addButton.setOnMouseClicked(mouseEvent -> addToCart(p, Integer.parseInt(qty.getText())));
        }
    }

    private void addToCart(ProductModel p, int qty) {
        if (Global.cart.putIfAbsent(p, qty) != null) {
            Global.cart.replace(p, Global.cart.get(p) + qty);
        }
    }

    @FXML
    protected void select(){
        products = ProductDao.select(
                searchField.getText() == null ?
                        "" : searchField.getText(),
                sortBy(),
                depField.getSelectionModel().getSelectedItem() == null ?
                        "Tutto" : depField.getSelectionModel().getSelectedItem(),
                byTag()
        );
        refresh();
        loadData();
    }
}