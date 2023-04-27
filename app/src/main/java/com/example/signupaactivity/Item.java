package com.example.signupaactivity;

import android.graphics.Bitmap;

public class Item {
    private String name;
    private String description;
    private String image;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;

    public Item(String name, String description, String image, double price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getImageResourceId() {
        return 0;
    }
}
