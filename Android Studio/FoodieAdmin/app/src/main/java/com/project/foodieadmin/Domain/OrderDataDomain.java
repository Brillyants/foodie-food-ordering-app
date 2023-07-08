package com.project.foodieadmin.Domain;

import java.io.Serializable;

public class OrderDataDomain implements Serializable {
    private int id_user;
    private String order_code;
    private String payment_pic;

    public OrderDataDomain(int id_user, String order_code, String payment_pic) {
        this.id_user = id_user;
        this.order_code = order_code;
        this.payment_pic = payment_pic;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getPayment_pic() {
        return payment_pic;
    }

    public void setPayment_pic(String payment_pic) {
        this.payment_pic = payment_pic;
    }
}
