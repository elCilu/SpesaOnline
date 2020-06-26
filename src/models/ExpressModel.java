package models;

public class ExpressModel {
    private final int pIva;
    private final String companyName;

    public ExpressModel(int pIva, String companyName){
        this.pIva = pIva;
        this.companyName = companyName;
    }

    public int getpIva() {
        return pIva;
    }

    public String getCompanyName() {
        return companyName;
    }
}
