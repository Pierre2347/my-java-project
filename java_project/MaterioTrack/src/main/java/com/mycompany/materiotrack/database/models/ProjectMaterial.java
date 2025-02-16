package com.mycompany.materiotrack.database.models;

public class ProjectMaterial {
    private int id;
    private int projectId;
    private int materialId;
    private double allocatedQuantity;
    
    public ProjectMaterial() {}
    
    public ProjectMaterial(int id, int projectId, int materialId, double allocatedQuantity) {
        this.id = id;
        this.projectId = projectId;
        this.materialId = materialId;
        this.allocatedQuantity = allocatedQuantity;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }
    public double getAllocatedQuantity() { return allocatedQuantity; }
    public void setAllocatedQuantity(double allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; }
} 