package utils;

import models.ShoppingModel;

import java.util.Set;

public interface Delivery {
    public Set<ShoppingModel> getOnDelivery(); //visualizza le spese in consegna
    public void changeStatus(); //cambia lo stato della spesa tipo da IN_CONSEGNA a CONSEGNATO
}
