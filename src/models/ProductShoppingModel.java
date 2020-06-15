package models;

public class ProductShoppingModel {
    private final int id;
    private final int idProduct;
    private final int idShopping;
    private final int qty;

    public ProductShoppingModel(int id, int idProduct, int idShopping, int qty){
        this.id = id;
        this.idProduct = idProduct;
        this.idShopping = idShopping;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getIdShopping() {
        return idShopping;
    }

    public int getQty() {
        return qty;
    }


}