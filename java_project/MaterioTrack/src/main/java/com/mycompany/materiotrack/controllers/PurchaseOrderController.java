package com.mycompany.materiotrack.controllers;

import com.mycompany.materiotrack.services.PurchaseOrderService;
import com.mycompany.materiotrack.database.models.PurchaseOrder;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController() {
        this.purchaseOrderService = new PurchaseOrderService();
    }

    public void addPurchaseOrder(int supplierId, Date orderDate, Date deliveryDate, String status, double totalAmount) {
        try {
            PurchaseOrder order = new PurchaseOrder(
                0, // ID will be auto-generated
                supplierId,
                orderDate,
                deliveryDate, // Add this
                status,
                totalAmount
            );
            purchaseOrderService.addPurchaseOrder(order);
            System.out.println("Purchase order added successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add purchase order: " + e.getMessage());
        }
    }

    public List<PurchaseOrder> listPurchaseOrders() throws SQLException {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    public void updatePurchaseOrder(int id, int supplierId, Date orderDate, String status, double totalAmount) {
        try {
            PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(id);
            if (order != null) {
                order.setSupplierId(supplierId);
                order.setOrderDate(orderDate);
                order.setStatus(status);
                order.setTotalAmount(totalAmount);
                purchaseOrderService.updatePurchaseOrder(order);
                System.out.println("Purchase order updated successfully!");
            } else {
                System.err.println("Purchase order not found!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update purchase order: " + e.getMessage());
        }
    }

    public void deletePurchaseOrder(int id) {
        try {
            purchaseOrderService.deletePurchaseOrder(id);
            System.out.println("Purchase order deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete purchase order: " + e.getMessage());
        }
    }
} 