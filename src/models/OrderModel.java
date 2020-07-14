package models;

public class OrderModel {
    private int id;
    private int pIvaSupplier;
    private int matrStockMan;
    private int status;

    public OrderModel(int id, int pIvaSupplier, int matrStockMan, int status){
        this.id = id;
        this.pIvaSupplier = pIvaSupplier;
        this.matrStockMan = matrStockMan;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpIvaSupplier() {
        return pIvaSupplier;
    }

    public void setpIvaSupplier(int pIvaSupplier) {
        this.pIvaSupplier = pIvaSupplier;
    }

    public int getMatrStockMan() {
        return matrStockMan;
    }

    public void setMatrStockMan(int matrStockMan) {
        this.matrStockMan = matrStockMan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
