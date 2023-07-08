package com.project.foodie.Domain;

import java.io.Serializable;

public class CategoryDomain implements Serializable {
    private String title;
    private String pic;
    private int categoryID;

    // constructor
    public CategoryDomain(String title, String pic, int categoryID) {
        this.title = title;
        this.pic = pic;
        this.categoryID = categoryID;
    }

    // getter setter
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
