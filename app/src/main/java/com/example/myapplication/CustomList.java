package com.example.myapplication;

public class CustomList {

    String name,price,descriptionArray;
    int image;

    public CustomList(String name, String price, String descriptionArray, int image) {
        this.name = name;
        this.price = price;
        this.descriptionArray = descriptionArray;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescriptionArray() {
        return descriptionArray;
    }

    public void setDescriptionArray(String descriptionArray) {
        this.descriptionArray = descriptionArray;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
