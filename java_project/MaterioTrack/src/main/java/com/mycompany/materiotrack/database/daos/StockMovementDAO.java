package com.mycompany.materiotrack.database.daos;

import com.mycompany.materiotrack.database.BaseDAO;
import com.mycompany.materiotrack.database.DatabaseConnection;
import com.mycompany.materiotrack.database.models.StockMovement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO extends BaseDAO<StockMovement> {

    @Override
    protected String getTableName() {
        return "stock_movements";
    }
       @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected StockMovement mapResultSetToEntity(ResultSet rs) throws SQLException {
        StockMovement movement = new StockMovement();
        movement.setId(rs.getInt("id"));
        movement.setMaterialId(rs.getInt("material_id"));
        movement.setQuantity(rs.getDouble("quantity"));
        movement.setMovementType(rs.getString("movement_type"));
        movement.setMovementDate(rs.getTimestamp("movement_date"));
        movement.setProjectId(rs.getInt("project_id"));
        return movement;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, StockMovement movement) throws SQLException {
        stmt.setInt(1, movement.getMaterialId());
        stmt.setDouble(2, movement.getQuantity());
        stmt.setString(3, movement.getMovementType());
        stmt.setTimestamp(4, new java.sql.Timestamp(movement.getMovementDate().getTime()));
        stmt.setInt(5, movement.getProjectId());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, StockMovement movement) throws SQLException {
        stmt.setInt(1, movement.getMaterialId());
        stmt.setDouble(2, movement.getQuantity());
        stmt.setString(3, movement.getMovementType());
        stmt.setTimestamp(4, new java.sql.Timestamp(movement.getMovementDate().getTime()));
        stmt.setInt(5, movement.getProjectId());
        stmt.setInt(6, movement.getId());
    }

    @Override
    protected int getParameterCount(StockMovement entity) {
        return 5; // material_id, quantity, movement_type, movement_date, project_id
    }

    @Override
    protected String getColumnNames(StockMovement entity) {
        return "material_id, quantity, movement_type, movement_date, project_id";
    }

    // Additional methods
    public List<StockMovement> findByMaterial(int materialId) throws SQLException {
        List<StockMovement> movements = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE material_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movements.add(mapResultSetToEntity(rs));
                }
            }
        }
        return movements;
    }

    @Override
    public StockMovement read(int id) throws SQLException {
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
    public List<StockMovement> getAll() throws SQLException {
        List<StockMovement> movements = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movements.add(mapResultSetToEntity(rs));
            }
        }
        return movements;
    }

    @Override
    public void update(StockMovement movement) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET material_id = ?, quantity = ?, movement_type = ?, movement_date = ?, project_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, movement);
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