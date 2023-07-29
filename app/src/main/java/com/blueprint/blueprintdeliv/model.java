package com.blueprint.blueprintdeliv;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class model {
    @ServerTimestamp
    Date timestamp;



    String  title;
    int price, quantity;



    public model() {
    }

    public model(int price, int quantity, String title, Date timestamp) {
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.timestamp = timestamp;

    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTimestamp() {
        return timestamp;
    }


}


