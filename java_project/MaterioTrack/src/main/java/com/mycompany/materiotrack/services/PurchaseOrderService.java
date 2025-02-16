package com.mycompany.materiotrack.services;

import com.mycompany.materiotrack.database.daos.PurchaseOrderDAO;
import com.mycompany.materiotrack.database.models.PurchaseOrder;
import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderService {
    private final PurchaseOrderDAO purchaseOrderDAO;

    public PurchaseOrderService() {
        this.purchaseOrderDAO = new PurchaseOrderDAO();
    }

    public void addPurchaseOrder(PurchaseOrder order) throws SQLException {
        purchaseOrderDAO.create(order);
    }

    public PurchaseOrder getPurchaseOrderById(int id) throws SQLException {
        return purchaseOrderDAO.read(id);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() throws SQLException {
        return purchaseOrderDAO.getAll();
    }

    public void updatePurchaseOrder(PurchaseOrder order) throws SQLException {
        purchaseOrderDAO.update(order);
    }

    public void deletePurchaseOrder(int id) throws SQLException {
        purchaseOrderDAO.delete(id);
    }
} 