package models;

import java.util.Date;

public class ShoppingModel {
    private int id;
    private Date purchaseDate;
    private Date deliveryDate;
    private float totalCost;
    private int earnedPoints;
    private int status;
    private int idClient;
    private int idPaymentMethod;

    public ShoppingModel(int id, Date purchaseDate, Date deliveryDate, float totalCost, int earnedPoints, int status, int idClient, int idPaymentMethod) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.totalCost = totalCost;
        this.earnedPoints = earnedPoints;
        this.status = status;
        this.idClient = idClient;
        this.idPaymentMethod = idPaymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
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

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(int idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }
}
