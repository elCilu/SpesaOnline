package models;

import enums.PaymentMethod;

public class ClientModel {
    private int id;
    private String name;
    private String surname;
    private String address;
    private String zip;
    private String phoneNumber;
    private String email;
    private PaymentMethod idPaymentMethod;
    private Integer points_cards;

    public ClientModel(int id, String name, String surname, String address, String zip, String phoneNumber, String email, PaymentMethod idPaymentMethod, Integer points_cards) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idPaymentMethod = idPaymentMethod;
        this.points_cards = points_cards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PaymentMethod getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(PaymentMethod idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public int getPoints() {
        return points_cards;
    }

    public void setPoints(int points_cards) {
        this.points_cards = points_cards;
    }
}


