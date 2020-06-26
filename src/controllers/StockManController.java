package controllers;

import enums.Status;
import models.ShoppingModel;
import java.util.Calendar;

public class StockManController {

    //modifica stato della spesa (da confermato a in consegna)
    public void modifyStatus(ShoppingModel shopping) {
        Calendar cal = Calendar.getInstance();  //crea nuovo calendar (date è deprecata, non si usa più)

        //magazziniere controlla che la spesa deve essere spedita quel giorno
        if(/*se la data attuale (di sistema) è uguale a quella di consegna*/)  //controllare se è giusto
            shopping.setStatus(Status.DELIVERING);
    }

    //invia ordine a fornitore
    public void sendOrder(){

        //controlla se il qtyStock cioè la quantità rimanente in magazzino del prodotto è < di qtyMin
        //se è minore, invia l'ordine
    }
}
