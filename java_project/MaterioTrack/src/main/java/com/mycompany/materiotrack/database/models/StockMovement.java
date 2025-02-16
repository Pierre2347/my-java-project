package com.mycompany.materiotrack.database.models;

import java.util.Date;

public class StockMovement {
    private int id;
    private int materialId;
    private double quantity;
    private String movementType; // IN or OUT
    private Date movementDate;
    private int projectId;
    
    public StockMovement() {}
    
    public StockMovement(int id, int materialId, double quantity, 
                        String movementType, Date movementDate, int projectId) {
        this.id = id;
        this.materialId = materialId;
        this.quantity = quantity;
        this.movementType = movementType;
        this.movementDate = movementDate;
        this.projectId = projectId;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }
    public Date getMovementDate() { return movementDate; }
    public void setMovementDate(Date movementDate) { this.movementDate = movementDate; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
} 