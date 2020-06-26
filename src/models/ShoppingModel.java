package models;

import enums.PaymentMethod;
import enums.Status;
import java.util.Date;

public class ShoppingModel {
    private int id;
    private final Date purchaseDate;
    private final Date deliveryDate;
    private final String deliveryH;
    private final float totalCost;
    private final int earnedPoints;
    private Status status;
    private final int idClient;
    private final PaymentMethod idPaymentMethod;

    public ShoppingModel(int id, Date purchaseDate, Date deliveryDate, String deliveryH, float totalCost, int earnedPoints, Status status, int idClient, PaymentMethod idPaymentMethod) {
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

    public void setId(int id){
        this.id = id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getIdClient() {
        return idClient;
    }

    public PaymentMethod getIdPaymentMethod() {
        return idPaymentMethod;
    }
}
