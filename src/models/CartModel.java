package models;

import javafx.scene.text.Text;

import java.awt.*;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CartModel {
    private static SortedMap<ProductModel, Integer> products = new TreeMap<>();

    //Il costruttore costruisce un carrello vuoto
    //devo trovare il modo di stamparlo sulla GUI
    public CartModel() {
        //messages.setText("IL TUO CARRELLO È VUOTO!");
    }

    /**aggiunge i prodotti al carrello uno ad uno
     * @param p: prodootto
     * @param qty : quantità
    */
    public void addToCart(ProductModel p, int qty){
        products.put(p, qty);
    }

    /**lo uso per cancellare i singoli prodotti
     * @param p: prodotto che devo rimuovere dal carrello
     * */
    public void remove(ProductModel p){
        products.remove(p, products.get(p));
    }

    /**
     * rimuove tutti i prodotti dal carrello
     */
    public void removeAll(){
        products.clear();


    }

    /**
     * imposta la nuova quantità di un prodotto
     * @param p prodotto
     * @param qty nuova quantità
     */
    public void setProductQty(ProductModel p, int qty){
        products.replace(p, products.get(p), qty);
    }

    /**
     *
     * @param p prodotto
     * @return la quantità del prodotto nel carrello
     */
    public int getProductQty(ProductModel p){
        return products.get(p);
    }

    /**
     *
     * @return i prodotti nel carrello
     */
    public Set<ProductModel> getProducts(){
       return products.keySet();
    }
    /**
     * @param p prodotto
     * @return prezzo totale del prodotto
     */
    public float getTotalProductPrice(ProductModel p){
        float tot;
        tot = p.getprice() * products.get(p);
        return tot;
    }

    /**
     *calcolo il tale del carrello
     */
    public float getTotalShopping(int mod){
        return subTotal() + getShippingCost(mod);//devo sommare i codici promozionali
    }

    /**
     *
     * @return il totale di tutti i prodotti senza la spedizione e le promozioni
     */
    public float subTotal(){
        float tot = 0;

        for(ProductModel p: products.keySet())
            tot += getTotalProductPrice(p);

        return tot;
    }

    /**
     *
     * @param mod modalità di spedizione
     * @return il costo della scpedizione
     */
    public Float getShippingCost(int mod){
        if((subTotal() >= 50 && (mod == 0 || mod == 1)) || products.isEmpty())
            return 0.0F;
        else if(mod == 1)
            return 5.99F;
        else
            return 10.99F;
    }

    /**
     *
     * @return punti fedeltà
     */
    public int getPoints(){
        return (int) subTotal();
    }

}
