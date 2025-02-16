package com.mycompany.materiotrack.database.daos;

import com.mycompany.materiotrack.database.BaseDAO;
import com.mycompany.materiotrack.database.DatabaseConnection;
import com.mycompany.materiotrack.database.models.PurchaseOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDAO extends BaseDAO<PurchaseOrder> {

    @Override
    protected String getTableName() {
        return "purchase_orders";
    }
       @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected PurchaseOrder mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new PurchaseOrder(
            rs.getInt("id"),
            rs.getInt("supplier_id"),
            rs.getDate("order_date"),
            rs.getDate("delivery_date"),
            rs.getString("status"),
            rs.getDouble("total_amount")
        );
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, PurchaseOrder purchaseOrder) throws SQLException {
        stmt.setInt(1, purchaseOrder.getSupplierId());
        stmt.setDate(2, new java.sql.Date(purchaseOrder.getOrderDate().getTime()));
        stmt.setDate(3, purchaseOrder.getDeliveryDate() != null ? 
            new java.sql.Date(purchaseOrder.getDeliveryDate().getTime()) : null);
        stmt.setString(4, purchaseOrder.getStatus());
        stmt.setDouble(5, purchaseOrder.getTotalAmount());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, PurchaseOrder order) throws SQLException {
        stmt.setInt(1, order.getSupplierId());
        stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
        stmt.setDate(3, order.getDeliveryDate() != null ? 
            new java.sql.Date(order.getDeliveryDate().getTime()) : null);
        stmt.setString(4, order.getStatus());
        stmt.setDouble(5, order.getTotalAmount());
        stmt.setInt(6, order.getId());
    }

    @Override
    protected String getColumnNames(PurchaseOrder entity) {
        return "supplier_id, order_date, delivery_date, status, total_amount";
    }

    @Override
    protected int getParameterCount(PurchaseOrder entity) {
        return 5; // Matches the number of columns in getColumnNames
    }

    // Additional methods
    public List<PurchaseOrder> findBySupplier(int supplierId) throws SQLException {
        List<PurchaseOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToEntity(rs));
                }
            }
        }
        return orders;
    }

    @Override
    public PurchaseOrder read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<PurchaseOrder> getAll() throws SQLException {
        List<PurchaseOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(mapResultSetToEntity(rs));
            }
        }
        return orders;
    }

    @Override
    public void update(PurchaseOrder order) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET supplier_id = ?, order_date = ?, status = ?, total_amount = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, order);
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
} 