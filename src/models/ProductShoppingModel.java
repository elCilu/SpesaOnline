package models;

public class ProductShoppingModel {
    private int id;
    private int idProduct;
    private int idShopping;
    private int qty;

    public ProductShoppingModel(int id, int idProduct, int idShopping, int qty){
        this.id = id;
        this.idProduct = idProduct;
        this.idShopping = idShopping;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }


    public int getIdShopping() {
        return idShopping;
    }

    public void setIdShopping(int idShopping) {
        this.idShopping = idShopping;
    }


    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}