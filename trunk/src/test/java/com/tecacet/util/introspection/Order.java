package com.tecacet.util.introspection;

import java.util.Date;

public class Order {

    private String account;
    private Customer customer;
    private int quantity;
    private double price;
    private Date date;
    private boolean active = false;

    public Order() {

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String number) {
        this.account = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
