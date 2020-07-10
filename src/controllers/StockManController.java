package controllers;

import enums.Status;
import javafx.fxml.FXML;
import models.ShoppingModel;
import java.util.Date;

public class StockManController {

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
}
