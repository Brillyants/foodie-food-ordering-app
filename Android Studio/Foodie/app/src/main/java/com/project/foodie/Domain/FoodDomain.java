package com.project.foodie.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private String title;
    private String pic;
    private String desc;
    private int categoryID;
    private int price;
    private int numInCard;

    public FoodDomain() {
    }

    // constructor without numInCard
    public FoodDomain(String title, String pic, String desc, int categoryID, int price) {
        this.title = title;
        this.pic = pic;
        this.desc = desc;
        this.price = price;
        this.categoryID = categoryID;
    }

    // constructor with numInCard
    public FoodDomain(String title, String pic, String desc, int categoryID, int price, int numInCard) {
        this.title = title;
        this.pic = pic;
        this.desc = desc;
        this.price = price;
        this.categoryID = categoryID;
        this.numInCard = numInCard;
    }

    // getter and setter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumInCard() {
        return numInCard;
    }

    public void setNumInCard(int numInCard) {
        this.numInCard = numInCard;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
