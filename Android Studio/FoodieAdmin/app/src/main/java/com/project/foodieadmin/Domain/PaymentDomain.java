package com.project.foodieadmin.Domain;

import java.io.Serializable;

public class PaymentDomain implements Serializable {
    private String order_code;
    private String cust_name;
    private String cust_phone;
    private String cust_location;
    private String date;
    private int cust_total;
    private String cust_pic;

    public PaymentDomain() {

    }

    public PaymentDomain(String order_code, String cust_name, String cust_phone,
                         String cust_location, String date, int cust_total, String cust_pic) {
        this.order_code = order_code;
        this.cust_name = cust_name;
        this.cust_phone = cust_phone;
        this.cust_location = cust_location;
        this.date = date;
        this.cust_total = cust_total;
        this.cust_pic = cust_pic;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCust_pic() {
        return cust_pic;
    }

    public void setCust_pic(String cust_pic) {
        this.cust_pic = cust_pic;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }

    public String getCust_location() {
        return cust_location;
    }

    public void setCust_location(String cust_location) {
        this.cust_location = cust_location;
    }

    public int getCust_total() {
        return cust_total;
    }

    public void setCust_total(int cust_total) {
        this.cust_total = cust_total;
    }
}
