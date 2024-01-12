package com.pragma.powerup.domain.model;

public class Order {

    private int id;
    private int idRestaurant;

    public Order() {
    }

    public Order(int id, int idRestaurant) {
        this.id = id;
        this.idRestaurant = idRestaurant;
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
}
