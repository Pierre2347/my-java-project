package com.mycompany.materiotrack.controllers;

import com.mycompany.materiotrack.services.MaterialService;
import com.mycompany.materiotrack.database.models.Material;
import java.sql.SQLException;
import java.util.List;
import java.util.Collections;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.mycompany.materiotrack.database.DatabaseConnection;
import java.sql.PreparedStatement;

public class MaterialController {
    private final MaterialService materialService;

    public MaterialController() {
        this.materialService = new MaterialService();
    }

    public void addMaterial(Material material) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            materialService.addMaterial(material, conn);
            System.out.println("Material added successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add material: " + e.getMessage());
        }
    }

    public void listMaterials() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Material> materials = materialService.getAllMaterials(conn);
            for (Material material : materials) {
                System.out.println(material);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch materials: " + e.getMessage());
        }
    }

    public void updateMaterial(Material material) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (material != null) {
                materialService.updateMaterial(material, conn);
                System.out.println("Material updated successfully!");
            } else {
                System.err.println("Material not found!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update material: " + e.getMessage());
        }
    }

    public void deleteMaterial(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            materialService.deleteMaterial(id, conn);
            System.out.println("Material deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete material: " + e.getMessage());
        }
    }

    public Material getMaterialById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Material m = materialService.getMaterialById(id, conn);
            System.out.println("Material retrieved successfully!");
            return m;
        } catch (SQLException e) {
            System.err.println("Failed to retrieve material: " + e.getMessage());
            return new Material();
        }
    }

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM materials");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materials.add(new Material(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getString("unit"),
                    rs.getDouble("min_stock_level")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }
} 