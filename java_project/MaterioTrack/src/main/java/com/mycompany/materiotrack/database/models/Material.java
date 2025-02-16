package com.mycompany.materiotrack.database.models;

public class Material {
    private int id;
    private String name;
    private String description;
    private String category;
    private double quantity;
    private String unit;
    private double min_stock_level;
    
    // Constructors, getters, and setters
    public Material() {}
    
    public Material(int id, String name, String description, String category, 
                   double quantity, String unit, double minStockLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.min_stock_level = minStockLevel;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public double getMinStockLevel() { return min_stock_level; }
    public void setMinStockLevel(double minStockLevel) { this.min_stock_level = minStockLevel; }

    // Optional: toString() method for debugging
    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", min_stock_level=" + min_stock_level +
                '}';
    }
} 