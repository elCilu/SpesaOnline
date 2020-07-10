package models;

public class WarehouseModel {
    private int id;
    private final int qty;
    private final int qtyMin;
    private final int qtyMax;

    public WarehouseModel(int id, int qty, int qtyMin, int qtyMax){
        this.id = id;
        this.qty = qty;
        this.qtyMin = qtyMin;
        this.qtyMax = qtyMax;
    }

    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    public int getQtyMin() {
        return qtyMin;
    }

    public int getQtyMax() {
        return qtyMax;
    }
}
