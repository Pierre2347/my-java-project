package com.mycompany.materiotrack.services;

import com.mycompany.materiotrack.database.models.Material;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialService {

    public void addMaterial(Material material, Connection conn) throws SQLException {
        String query = "INSERT INTO materials (name, description, category, quantity, unit, min_stock_level) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, material.getName());
            stmt.setString(2, material.getDescription());
            stmt.setString(3, material.getCategory());
            stmt.setDouble(4, material.getQuantity());
            stmt.setString(5, material.getUnit());
            stmt.setDouble(6, material.getMinStockLevel());
            stmt.executeUpdate();
        }
    }

    public List<Material> getAllMaterials(Connection conn) throws SQLException {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM materials";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                materials.add(new Material(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getDouble("quantity"),
                    rs.getString("unit"),
                    rs.getDouble("min_stock_level")
                ));
            }
        }
        return materials;
    }

    public void updateMaterial(Material material, Connection conn) throws SQLException {
        String query = "UPDATE materials SET name = ?, description = ?, category = ?, quantity = ?, unit = ?, min_stock_level = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, material.getName());
            stmt.setString(2, material.getDescription());
            stmt.setString(3, material.getCategory());
            stmt.setDouble(4, material.getQuantity());
            stmt.setString(5, material.getUnit());
            stmt.setDouble(6, material.getMinStockLevel());
            stmt.setInt(7, material.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteMaterial(int id, Connection conn) throws SQLException {
        String query = "DELETE FROM materials WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Material getMaterialById(int id, Connection conn) throws SQLException {
        String query = "SELECT * FROM materials WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("quantity"),
                        rs.getString("unit"),
                        rs.getDouble("min_stock_level")
                    );
                }
            }
        }
        return null;
    }
} 