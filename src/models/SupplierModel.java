package models;

public class SupplierModel {
    private final int pIva;
    private final String companyName;
    private int dep;    //reparto del quale si occupano, c'è un fornitore per reparto

    public SupplierModel(int pIva, String companyName, int dep) {
        this.pIva = pIva;
        this.companyName = companyName;
        this.dep = dep;
    }

    public int getpIva() {
        return pIva;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getDep() {
        return dep;
    }
}
