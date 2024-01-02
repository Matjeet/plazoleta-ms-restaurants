package com.pragma.powerup.domain.model;

public class Dish {

    private int id;
    private String name;
    private int price;
    private String description;
    private String urlImage;
    private int idCategory;
    private int idRestaurant;

    public Dish(int id, String name, int price, String description, String urlImage, int idCategory, int idRestaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.urlImage = urlImage;
        this.idCategory = idCategory;
        this.idRestaurant = idRestaurant;
    }

    public Dish() {
    }

    public int getId(){
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
