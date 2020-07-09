package controllers;

import dao.ShoppingDao;
import enums.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.ShoppingModel;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StockManController implements Initializable {
    @FXML
    ImageView logo;
    @FXML
    VBox shoppingVBox;
    @FXML
    VBox viewVBox;
    @FXML
    VBox visualizeShoppingVBox;

        List<ShoppingModel> shoppings;
    //modifica stato della spesa (da confermato a in consegna)
    public void modifyStatus(ShoppingModel shopping) {
        Date currentDate = new Date();

        //magazziniere controlla che la spesa deve essere spedita quel giorno
        if(currentDate.compareTo(shopping.getDeliveryDate()) == 0)  //controllare se è giusto
            shopping.setStatus(Status.DELIVERING);
        //stampa onAction la data non è quella di oggi
    }

    //invia ordine a fornitore
    public void sendOrder(){

        //controlla se il qtyStock cioè la quantità rimanente in magazzino del prodotto è < di qtyMin
        //se è minore, invia l'ordine
        //fai model ordine int id, int piva fornitore, int matricola magazz.
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage();
    }

    void loadPage(){
        //get all shoppings from the database
        shoppings = ShoppingDao.getShoppings(Status.CONFIRMED);

        for(ShoppingModel sh : shoppings){
            //short description of a shopping
            Text shoppingText = new Text();
            shoppingText.setText("Spesa: " + sh.getId() +  sh.getPurchaseDate());
            shoppingVBox.getChildren().add(shoppingText);

            //visualize button
            Button visualize = new Button();
            visualize.setText("Visualizza");
            visualize.setOnAction(actionEvent -> { /*pulisci l'area di visualizzazione e poi recupera i prodotti(productShopping) e stampali + button che cambia status */});
        }
    }

    public void newOrder(){

        visualizeShoppingVBox.getChildren().clear();

        //get product to order...

        //print them on the VBox

        //button "ordina"  --> onAction(sendOrder)

        //visualizza ordini già inviati --> pulisci l'area stampa ordini dalla tabella nel db


    }

}
