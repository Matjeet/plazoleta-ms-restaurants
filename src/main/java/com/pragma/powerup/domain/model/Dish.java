package com.pragma.powerup.domain.model;

public class Dish {

    private String name;
    private int price;
    private String Description;
    private String urlImage;
    private int idCategory;
    private int idRestaurant;

    public Dish(String name, int price, String description, String urlImage, int idCategory, int idRestaurant) {
        this.name = name;
        this.price = price;
        Description = description;
        this.urlImage = urlImage;
        this.idCategory = idCategory;
        this.idRestaurant = idRestaurant;
    }

    public Dish() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}
