package com.project.foodieadmin.Domain;

import java.io.Serializable;

public class OrderDomain implements Serializable {
    private String order_code;
    private int id_user;
    private String food_name;
    private String food_pic;
    private int number_order;
    private String order_date;
    private double total;

    public OrderDomain(String order_code, int id_user, String food_name,
                       String food_pic, int number_order, String order_date, double total) {
        this.order_code = order_code;
        this.id_user = id_user;
        this.food_name = food_name;
        this.food_pic = food_pic;
        this.number_order = number_order;
        this.order_date = order_date;
        this.total = total;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
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

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_pic() {
        return food_pic;
    }

    public void setFood_pic(String food_pic) {
        this.food_pic = food_pic;
    }

    public int getNumber_order() {
        return number_order;
    }

    public void setNumber_order(int number_order) {
        this.number_order = number_order;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
