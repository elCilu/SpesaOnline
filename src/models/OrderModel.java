package models;

public class OrderModel {
    private int id;
    private final int pIvaSupplier;
    private final int matrStockMan;

    public OrderModel(int id, int pIvaSupplier, int matrStockMan){
        this.id = id;
        this.pIvaSupplier = pIvaSupplier;
        this.matrStockMan = matrStockMan;
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

}
