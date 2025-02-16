package com.mycompany.materiotrack.database.daos;

import com.mycompany.materiotrack.database.BaseDAO;
import com.mycompany.materiotrack.database.DatabaseConnection;
import com.mycompany.materiotrack.database.models.Material;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO extends BaseDAO<Material> {

   @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected String getTableName() {
        return "materials";
    }

    @Override
    protected Material mapResultSetToEntity(ResultSet rs) throws SQLException {
        Material material = new Material();
        material.setId(rs.getInt("id"));
        material.setName(rs.getString("name"));
        material.setDescription(rs.getString("description"));
        material.setCategory(rs.getString("category"));
        material.setQuantity(rs.getDouble("quantity"));
        material.setUnit(rs.getString("unit"));
        material.setMinStockLevel(rs.getDouble("min_stock_level"));
        return material;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Material material) throws SQLException {
        stmt.setString(1, material.getName());
        stmt.setString(2, material.getDescription());
        stmt.setString(3, material.getCategory());
        stmt.setDouble(4, material.getQuantity());
        stmt.setString(5, material.getUnit());
        stmt.setDouble(6, material.getMinStockLevel());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Material material) throws SQLException {
        stmt.setString(1, material.getName());
        stmt.setString(2, material.getDescription());
        stmt.setString(3, material.getCategory());
        stmt.setDouble(4, material.getQuantity());
        stmt.setString(5, material.getUnit());
        stmt.setDouble(6, material.getMinStockLevel());
        stmt.setInt(7, material.getId());
    }

    @Override
    protected int getParameterCount(Material entity) {
        return 6; // name, description, category, quantity, unit, min_stock_level
    }

    @Override
    protected String getColumnNames(Material entity) {
        return "name, description, category, quantity, unit, min_stock_level";
    }

    // CRUD Operations
    public void create(Material material) throws SQLException {
        String query = "INSERT INTO " + getTableName() + 
            " (name, description, category, quantity, unit, min_stock_level) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, material);
            stmt.executeUpdate();
        }
    }

    public Material read(int id) throws SQLException {
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

    public void update(Material material) throws SQLException {
        String query = "UPDATE " + getTableName() + 
            " SET name = ?, description = ?, category = ?, quantity = ?, unit = ?, min_stock_level = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, material);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Material> getAll() throws SQLException {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                materials.add(mapResultSetToEntity(rs));
            }
        }
        return materials;
    }
} 