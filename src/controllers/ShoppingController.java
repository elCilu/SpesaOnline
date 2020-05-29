package controllers;

import dao.ProductDao;
import enums.Tag;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.ProductModel;

import java.net.URL;
import java.util.*;

public class ShoppingController implements Initializable {

    @FXML
    public TextField searchField;           //barra di ricerca
    @FXML
    public ListView<String> depField;       //menu a tendina a sx
    @FXML
    public ComboBox<String> sortBy;         //ordina per prezzo/alfabetico
    @FXML
    public CheckBox bioFilter;              //filtra prodotti bio
    @FXML
    public CheckBox glutenFreeFilter;       //filtra prodotti no glutine
    @FXML
    public CheckBox dairyFreeFilter;        //filtra prodotti no lattosio
    @Override
    public void initialize(URL var1, ResourceBundle var2){

    }

    @FXML
    protected List<ProductModel> searchBy(){
        String res = searchField.getText();
        List<ProductModel> products;

        if(searchField.getText().isEmpty()){        //se l'utente non ha ancora cercato nulla
            products = ProductDao.getAllProducts(); //ritorno tutti i prodotti del db
        }
        else{
            products = ProductDao.searchBy(res);    //l'utente ha inserito il nome di un prodotto o una marca
        }
        return products;
    }

    @FXML
    protected TreeSet<ProductModel> orderByAscendingPrice(){
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
        List<ProductModel> products = searchBy();    //lista di supporto
        for (ProductModel p : products) {
            result.add(p);      //ordino tramite comparator del tree set
        }
        return result;
    }

    @FXML
    protected TreeSet<ProductModel> orderByDescendingPrice(){
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
        List<ProductModel> products = searchBy();    //lista di supporto
        for (ProductModel p : products) {
            result.add(p);      //ordino tramite comparator del tree set
        }
        return result;
    }

    @FXML
    protected TreeSet<ProductModel> orderByAlphabeticOrder(){
        TreeSet<ProductModel> result = new TreeSet<>(new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel o1, ProductModel o2) {
                if(o1.getName().compareTo(o2.getName()) == 0){          //se i due prodotti hanno nomi uguali
                    return o1.getBrand().compareTo(o2.getBrand());      //li ordino per marca
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        List<ProductModel> products = searchBy();    //lista di supporto
        for (ProductModel p : products) {
            result.add(p);      //ordino tramite comparator del tree set
        }
        return result;
    }

    @FXML
    protected List<ProductModel> filterByTag(){
        List<ProductModel> result = new ArrayList<ProductModel>();

        //se il prodotto è solo bio (enum in posizione 1)
        if(bioFilter.isSelected() && (!glutenFreeFilter.isSelected()) && (!dairyFreeFilter.isSelected())){
            result = ProductDao.getProductsByTag(Tag.BIO.ordinal());
        }
        //se il prodotto è solo senza glutine (enum in posizione 2)
        else if((!bioFilter.isSelected()) && glutenFreeFilter.isSelected() && (!dairyFreeFilter.isSelected())){
            result = ProductDao.getProductsByTag(Tag.GLUTEN_FREE.ordinal());
        }
        //se il prodotto è solo senza lattosio (enum in posizione 3)
        else if((!bioFilter.isSelected()) && (!glutenFreeFilter.isSelected()) && dairyFreeFilter.isSelected()){
            result = ProductDao.getProductsByTag(Tag.DAIRY_FREE.ordinal());
        }
        //se il prodotto è bio e senza glutine (enum in posizione 4)
        else if(bioFilter.isSelected() && glutenFreeFilter.isSelected() && (!dairyFreeFilter.isSelected())){
            result = ProductDao.getProductsByTag(Tag.BIO_GLUTEN_FREE.ordinal());
        }
        //se il prodotto è bio e senza lattosio (enum in posizione 5)
        else if(bioFilter.isSelected() && (!glutenFreeFilter.isSelected()) && dairyFreeFilter.isSelected()){
            result = ProductDao.getProductsByTag(Tag.BIO_DAIRY_FREE.ordinal());
        }
        //se il prodotto è senza glutine e senza lattosio (enum in posizione 6)
        else if((!bioFilter.isSelected()) && glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()){
            result = ProductDao.getProductsByTag(Tag.GLUTEN_FREE_DAIRY_FREE.ordinal());
        }
        //se seleziono tutti i tag (enum in posizione 7)
        else if(bioFilter.isSelected() && glutenFreeFilter.isSelected() && dairyFreeFilter.isSelected()){
            result = ProductDao.getProductsByTag(Tag.BIO_GLUTEN_FREE_DAIRY_FREE.ordinal());
        }
        //se nessun tag è stato selezionato, lascia invariata la ricerca
        return result;
    }
}
