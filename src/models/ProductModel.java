package models;

public class ProductModel {
    private int id;
    private String name;
    private String brand;
    private int qtyPack;
    private String dep;
    private int qtyStock;
    private float price;
    private int tag; //TODO: collegarlo a Tag Enum


    public ProductModel(int id, String name, String brand, int qtyPack, String dep, int qtyStock, float price, int tag) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.qtyPack = qtyPack;
        this.dep = dep;
        this.qtyStock = qtyStock;
        this.price = price;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQtyPack() {
        return qtyPack;
    }

    public void setQtyPack(int qtyPack) {
        this.qtyPack = qtyPack;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public int getQtyStock() {
        return qtyStock;
    }

    public void setQtyStock(int qtyStock) {
        this.qtyStock = qtyStock;
    }

    public float getprice() {
        return price;
    }

    public void setprice(float price) {
        this.price = price;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String toString() {
        return String.format("Id: %d\nNome: %s\nMarca: %s\nQta Conf: %d\nReparto: %s\nQta Scorta: %d" +
                        "\nPrezzo: %f\nTag: %s", getId(), getName(), getBrand(), getQtyPack(), getDep(),
                getQtyStock(), getprice(), getTag());
    }
}
