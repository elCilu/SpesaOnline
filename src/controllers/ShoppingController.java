package controllers;

import dao.ProductDao;
import enums.Tag;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CartModel;
import models.ProductModel;
import javafx.scene.image.ImageView;
import utils.OSystem;
import java.io.File;
import java.net.URL;
import java.util.*;

public class ShoppingController implements Initializable {

    @FXML
    public ScrollPane shoppingPage;
    @FXML
    public TextField searchField;           //barra di ricerca
    @FXML
    public TextField qtyChoice;             //quantità aggiunta da utente
    @FXML
    public ListView<String> depField;       //menu a tendina a sx
    @FXML
    public ChoiceBox<String> sortBy;         //ordina per prezzo/alfabetico
    @FXML
    public CheckBox bioFilter;              //filtra prodotti bio
    @FXML
    public CheckBox glutenFreeFilter;       //filtra prodotti no glutine
    @FXML
    public CheckBox dairyFreeFilter;        //filtra prodotti no lattosio
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

    private static List<ProductModel> products = new ArrayList<>();
    private static List<ProductModel> tmp = new ArrayList<>();  //per ricerca tag
    private static final File prodImg = new File("");
    private CartModel cart = new CartModel();
    private String path = "";

    @FXML
    protected List<ProductModel> searchBy(){
        String res = searchField.getText();

        products = ProductDao.searchBy(res);    //l'utente ha inserito il nome di un prodotto o una marca

        return products;
    }


    protected void orderByAscendingPrice(){
        TreeSet<ProductModel> result = new TreeSet<>(new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel o1, ProductModel o2) {
                if (o1.getprice() <= o2.getprice()) {
                    return -1;
                }
                else
                    return 1;
            }
        });

        for (ProductModel p : products) {
            result.add(p);      //ordino tramite comparator del tree set
        }
        products = new ArrayList<>(result);
        refresh();
        loadData();
    }


    protected void orderByDescendingPrice(){
        TreeSet<ProductModel> result = new TreeSet<>(new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel o1, ProductModel o2) {
                if (o1.getprice() >= o2.getprice()) {
                    return -1;
                }
                else
                    return 1;
            }
        });

        for (ProductModel p : products) {
            result.add(p);      //ordino tramite comparator del tree set
        }
        products = new ArrayList<>(result);
        refresh();
        loadData();
    }


    protected void orderByAlphabeticOrder(){
        TreeSet<ProductModel> result = new TreeSet<>(new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel o1, ProductModel o2) {
                if(o1.getName().compareTo(o2.getName()) == 0){          //se i due prodotti hanno nomi uguali
                    return o1.getBrand().compareTo(o2.getBrand());      //li ordino per marca
                }
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (ProductModel p : products) {
            result.add(p);      //ordino tramite comparator del tree set
        }
        products = new ArrayList<>(result);
        refresh();
        loadData();
    }

    @FXML
    protected void filterByTag(){

        if(tmp.isEmpty())
            tmp = products;     //se non ho fatto ricerche precedenti

        for(ProductModel p : products) {
            //se il prodotto è solo bio (enum in posizione 1)
            if (bioFilter.isSelected() && (!glutenFreeFilter.isSelected()) && (!dairyFreeFilter.isSelected())
                    && p.getTag().compareTo(Tag.BIO) != 0) {
                tmp.remove(p);
            }
            //se il prodotto è solo senza glutine (enum in posizione 2)
            else if ((!bioFilter.isSelected()) && glutenFreeFilter.isSelected() && (!dairyFreeFilter.isSelected())
                        && p.getTag().compareTo(Tag.GLUTEN_FREE) != 0) {
                tmp.remove(p);
            }
            //se il prodotto è solo senza lattosio (enum in posizione 3)
            else if ((!bioFilter.isSelected()) && (!glutenFreeFilter.isSelected()) && dairyFreeFilter.isSelected()
                        && p.getTag().compareTo(Tag.DAIRY_FREE) != 0) {
                tmp.remove(p);
            }
            //se il prodotto è bio e senza glutine (enum in posizione 4)
            else if (bioFilter.isSelected() && glutenFreeFilter.isSelected() && (!dairyFreeFilter.isSelected())
                        && p.getTag().compareTo(Tag.BIO_GLUTEN_FREE) != 0) {
                tmp.remove(p);
            }
            //se il prodotto è bio e senza lattosio (enum in posizione 5)
            else if (bioFilter.isSelected() && (!glutenFreeFilter.isSelected()) && dairyFreeFilter.isSelected()
                        && p.getTag().compareTo(Tag.BIO_DAIRY_FREE) != 0) {
                tmp.remove(p);
            }
            //se il prodotto è senza glutine e senza lattosio (enum in posizione 6)
            else if ((!bioFilter.isSelected()) && glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()
                        && p.getTag().compareTo(Tag.GLUTEN_FREE_DAIRY_FREE) != 0) {
                tmp.remove(p);
            }
            //se seleziono tutti i tag (enum in posizione 7)
            else if (bioFilter.isSelected() && glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()
                        && p.getTag().compareTo(Tag.BIO_GLUTEN_FREE_DAIRY_FREE) != 0) {
                tmp.remove(p);
            }
            //se nessun tag è stato selezionato, lascia invariata la ricerca
        }
        refresh();
        loadData();
    }

    @FXML
    protected void getProdByDep(MouseEvent arg){

        String choice = depField.getSelectionModel().getSelectedItem();
        if(choice.equals("Frutta e verdura"))
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
        else if(choice.equals("Acqua, bevande, alcolici"))
            products = ProductDao.getProductsByDep("Acqua, bevande, alcolici");
        else if(choice.equals("Igiene"))
            products = ProductDao.getProductsByDep("Igiene");
        else if(choice.equals("Casa"))
            products = ProductDao.getProductsByDep("Casa");
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
        products = ProductDao.getAllProducts();
        sortBy.getItems().addAll("Prezzo cresc.", "Prezzo decresc.", "Alfabetico");
        loadData();
    }

    //carico i dati nella pagina
    private void loadData() {

        if(OSystem.isWindows())
            path = "C:\\" + prodImg.getAbsolutePath() + "\\images\\";
        if(OSystem.isUnix())
            path = "file://" + prodImg.getAbsolutePath() + "/images/";
        if(OSystem.isMac())
            path = "";

        logoImage.setImage(new Image(path +  "shopping_logo.jpg"));

        //setta button/collegamento pagina anagrafica
        Button userButton = new Button();
        userImage.setImage(new Image(path +  "user_image.png"));
        userImage.setFitHeight(50);
        userImage.setFitWidth(50);
        userButton.setGraphic(userImage);
        userButton.setOnAction(actionEvent -> userButton());

        //setta button/collegamento pagina carrello
        Button cartButton = new Button();
        cartImage.setImage(new Image(path + "cart_image.png"));
        cartImage.setFitHeight(50);
        cartImage.setFitWidth(50);
        cartButton.setGraphic(cartImage);
        cartButton.setOnAction(actionEvent -> cartButton());

        //gestione barra di ricerca
        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)){
                    searchBy();
                    refresh();
                    loadData();
                }
            }
        });

        //setto la lista di reparti a sx
        ObservableList<String> items = FXCollections.observableArrayList("Frutta e verdura", "Carne", "Pesce", "Scatolame",
                "Uova e latticini", "Salumi e formaggi", "Pane e pasticceria", "Confezionati", "Surgelati e gelati", "Merenda e dolci",
                "Acqua, bevande, alcolici", "Igiene", "Casa");
        depField.setItems(items);
        depField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getProdByDep(mouseEvent);
                refresh();
                loadData();
            }
        });

        //setto la combobox
        sortBy.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("Prezzo cresc."))
                    orderByAscendingPrice();
                else if(t1.equals("Prezzo decresc."))
                    orderByDescendingPrice();
                else if(t1.equals("Alfabetico"))
                    orderByAlphabeticOrder();
            }
        });

        //gestisco le checkbox
        bioFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(bioFilter.isSelected())
                    filterByTag();
            }
        });
        glutenFreeFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(glutenFreeFilter.isSelected())
                    filterByTag();
            }
        });
        dairyFreeFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(dairyFreeFilter.isSelected())
                    filterByTag();
            }
        });


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
            qtyVBox.getChildren().add(qty);

            //add button
            Button addButton = new Button();
            ImageView addImage = new ImageView();
            addImage.setImage(new Image(path + "add_button.png"));
            addImage.setFitHeight(25);
            addImage.setFitWidth(25);
            addButton.setGraphic(addImage);
            addVBox.setAlignment(Pos.CENTER);
            addVBox.getChildren().add(addButton);

            //gestione textfield qtyChoice
            qtyChoice.setText("0");
            int qty_for_cart = Integer.parseInt(qtyChoice.getText());
            addButton.setOnAction(actionEvent -> {cart.addToCart(p, qty_for_cart);
            });
        }


    }


}
