package com.hexaware.model;

import java.util.Date; 

public class Order {
    private int orderId;
    private int userId;
    private Date orderDate;

    // Constructor
    public Order(int orderId, int userId, Date orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
    }

    // Getter and setter for orderId
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Getter and setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and setter for orderDate
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}

