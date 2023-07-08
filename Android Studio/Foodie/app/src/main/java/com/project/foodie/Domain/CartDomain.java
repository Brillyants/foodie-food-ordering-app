package com.project.foodie.Domain;

public class CartDomain {
    private int id_user;
    private String food_name;
    private int number_order;

    public CartDomain(int id_user, String food_name, int number_order) {
        this.id_user = id_user;
        this.food_name = food_name;
        this.number_order = number_order;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String id_food) {
        this.food_name = id_food;
    }

    public int getNumber_order() {
        return number_order;
    }

    public void setNumber_order(int number_order) {
        this.number_order = number_order;
    }
}
