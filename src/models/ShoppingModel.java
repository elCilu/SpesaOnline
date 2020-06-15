package models;

import java.util.Date;

public class ShoppingModel {
    private final int id;
    private final Date purchaseDate;
    private final Date deliveryDate;
    private final String deliveryH;
    private final float totalCost;
    private final int earnedPoints;
    private int status;
    private final int idClient;
    private final int idPaymentMethod;

    public ShoppingModel(int id, Date purchaseDate, Date deliveryDate, String deliveryH, float totalCost, int earnedPoints, int status, int idClient, int idPaymentMethod) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.deliveryH = deliveryH;
        this.totalCost = totalCost;
        this.earnedPoints = earnedPoints;
        this.status = status;
        this.idClient = idClient;
        this.idPaymentMethod = idPaymentMethod;
    }

    public int getId() {
        return id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryH() {
        return deliveryH;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdPaymentMethod() {
        return idPaymentMethod;
    }
}
