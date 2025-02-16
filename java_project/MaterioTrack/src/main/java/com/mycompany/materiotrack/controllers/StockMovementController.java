package com.mycompany.materiotrack.controllers;

import com.mycompany.materiotrack.services.StockMovementService;
import com.mycompany.materiotrack.database.models.StockMovement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController() {
        this.stockMovementService = new StockMovementService();
    }

    public void addStockMovement(int materialId, double quantity, String movementType, Date movementDate, int projectId) {
        try {
            StockMovement movement = new StockMovement();
            movement.setMaterialId(materialId);
            movement.setQuantity(quantity);
            movement.setMovementType(movementType);
            movement.setMovementDate(movementDate);
            movement.setProjectId(projectId);
            stockMovementService.addStockMovement(movement);
            System.out.println("Stock movement added successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add stock movement: " + e.getMessage());
        }
    }

    public List<StockMovement> listStockMovements() throws SQLException {
        return stockMovementService.getAllStockMovements();
    }

    public void updateStockMovement(int id, int materialId, double quantity, String movementType, Date movementDate, int projectId) {
        try {
            StockMovement movement = stockMovementService.getStockMovementById(id);
            if (movement != null) {
                movement.setMaterialId(materialId);
                movement.setQuantity(quantity);
                movement.setMovementType(movementType);
                movement.setMovementDate(movementDate);
                movement.setProjectId(projectId);
                stockMovementService.updateStockMovement(movement);
                System.out.println("Stock movement updated successfully!");
            } else {
                System.err.println("Stock movement not found!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update stock movement: " + e.getMessage());
        }
    }

    public void deleteStockMovement(int id) {
        try {
            stockMovementService.deleteStockMovement(id);
            System.out.println("Stock movement deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete stock movement: " + e.getMessage());
        }
    }
} 