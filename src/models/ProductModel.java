package models;

public class ProductModel {
    private int id;
    private String name;
    private String brand;
    private int qtyPack;
    private String dep;
    private int qtyStock;
    private float prize;

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

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }
}
