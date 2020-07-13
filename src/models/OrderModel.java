package models;

public class OrderModel {
    private int id;
    private final int pIvaSupplier;
    private final int matrStockMan;
    private final int confirmed;

    public OrderModel(int id, int pIvaSupplier, int matrStockMan, int confirmed){
        this.id = id;
        this.pIvaSupplier = pIvaSupplier;
        this.matrStockMan = matrStockMan;
        this.confirmed = confirmed;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getpIvaSupplier(){
        return pIvaSupplier;
    }

    public int getMatrStockMan(){
        return  matrStockMan;
    }

    public int getConfirmed(){
        return confirmed; //return 0
    }

}
