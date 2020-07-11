package models;

public class ProductOrderModel {
    private int id;
    private int idProduct;
    private int idOrder;
    private int qty;

    public ProductOrderModel(int id, int idProduct, int idOrder, int qty){
        this.id = id;
        this.idProduct = idProduct;
        this.idOrder = idOrder;
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


    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }


    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}