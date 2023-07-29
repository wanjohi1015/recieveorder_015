package com.blueprint.blueprintdeliv;

public class Pmodel {

    String title, imageUrl,description;
    int  price;




    public Pmodel (){
    }


    public Pmodel (  int price, String title, String imageUrl, String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;

    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(){
        this.description = description;
    }
}
