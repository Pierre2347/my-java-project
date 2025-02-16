package com.mycompany.materiotrack.database.models;

import java.util.Date;

public class PurchaseOrder {
    private int id;
    private int supplierId;
    private Date orderDate;
    private Date deliveryDate;
    private String status;
    private double totalAmount;
    
    public PurchaseOrder() {}
    
    public PurchaseOrder(int id, int supplierId, Date orderDate, 
                        Date deliveryDate, String status, double totalAmount) {
        this.id = id;
        this.supplierId = supplierId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public Date getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
} 