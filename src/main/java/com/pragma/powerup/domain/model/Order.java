package com.pragma.powerup.domain.model;

import java.util.List;

public class Order {

    private int id;
    private List<OrderDish> orderDishes;
    private int idRestaurant;
    private int idStatus;

    public Order(int id, List<OrderDish> orderDishes, int idRestaurant, int idStatus) {
        this.id = id;
        this.orderDishes = orderDishes;
        this.idRestaurant = idRestaurant;
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

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
}
