package com.pragma.powerup.domain.model;

public class OrderDish {

    private int id;
    private int quantity;
    private int idDish;
    private int idOrder;

    public OrderDish(int id, int quantity, int idDish, int idOrder) {
        this.id = id;
        this.quantity = quantity;
        this.idDish = idDish;
        this.idOrder = idOrder;
    }

    public OrderDish() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
}
