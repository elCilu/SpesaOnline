package models;

import java.util.Date;

public class FidelityCard {
    private int idCard;
    private Date emissionDate;
    private int totalPoints;
    private int idUser;

    public FidelityCard(int idCard, Date emissionDate, int totalPoints, int idUser) {
        this.idCard = idCard;
        this.emissionDate = emissionDate;
        this.totalPoints = totalPoints;
        this.idUser = idUser;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public Date getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
