package com.mycompany.materiotrack.services;

import com.mycompany.materiotrack.database.daos.StockMovementDAO;
import com.mycompany.materiotrack.database.models.StockMovement;
import java.sql.SQLException;
import java.util.List;

public class StockMovementService {
    private final StockMovementDAO stockMovementDAO;

    public StockMovementService() {
        this.stockMovementDAO = new StockMovementDAO();
    }

    public void addStockMovement(StockMovement movement) throws SQLException {
        stockMovementDAO.create(movement);
    }

    public StockMovement getStockMovementById(int id) throws SQLException {
        return stockMovementDAO.read(id);
    }

    public List<StockMovement> getAllStockMovements() throws SQLException {
        return stockMovementDAO.getAll();
    }

    public void updateStockMovement(StockMovement movement) throws SQLException {
        stockMovementDAO.update(movement);
    }

    public void deleteStockMovement(int id) throws SQLException {
        stockMovementDAO.delete(id);
    }
} 