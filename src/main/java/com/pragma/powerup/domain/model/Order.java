package com.pragma.powerup.domain.model;


public class Order {

    private int id;
    private int idRestaurant;
    private int idClient;
    private int idStatus;

    public Order(int id, int idRestaurant, int idClient, int idStatus) {
        this.id = id;
        this.idRestaurant = idRestaurant;
        this.idClient = idClient;
        this.idStatus = idStatus;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
}
