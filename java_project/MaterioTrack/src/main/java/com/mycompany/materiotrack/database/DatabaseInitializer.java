package com.mycompany.materiotrack.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        String[] createTableQueries = {
            // Create tables without foreign keys first
            "CREATE TABLE IF NOT EXISTS materials (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "description TEXT, " +
            "category VARCHAR(100), " +
            "quantity DOUBLE NOT NULL, " +
            "unit VARCHAR(50) NOT NULL, " +
            "min_stock_level DOUBLE NOT NULL)",

            "CREATE TABLE IF NOT EXISTS suppliers (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "contact_person VARCHAR(255), " +
            "phone VARCHAR(20), " +
            "email VARCHAR(255), " +
            "address TEXT)",

            "CREATE TABLE IF NOT EXISTS projects (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "location VARCHAR(255), " +
            "start_date DATE NOT NULL, " +
            "end_date DATE, " +
            "status VARCHAR(50) NOT NULL)",

            // Now create tables with foreign keys
            "CREATE TABLE IF NOT EXISTS purchase_orders (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "supplier_id INT NOT NULL, " +
            "order_date DATE NOT NULL, " +
            "delivery_date DATE, " +
            "status VARCHAR(50) NOT NULL, " +
            "total_amount DOUBLE NOT NULL, " +
            "FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE)",

            "CREATE TABLE IF NOT EXISTS stock_movements (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "material_id INT NOT NULL, " +
            "quantity DOUBLE NOT NULL, " +
            "movement_type ENUM('IN', 'OUT') NOT NULL, " +
            "movement_date DATETIME NOT NULL, " +
            "project_id INT, " +
            "FOREIGN KEY (material_id) REFERENCES materials(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL)",

            "CREATE TABLE IF NOT EXISTS project_materials (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "project_id INT NOT NULL, " +
            "material_id INT NOT NULL, " +
            "allocated_quantity DOUBLE NOT NULL, " +
            "FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (material_id) REFERENCES materials(id) ON DELETE CASCADE)"
        };

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Disable foreign key checks temporarily
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            
            for (String query : createTableQueries) {
                stmt.executeUpdate(query);
            }
            
            // Re-enable foreign key checks
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            
            System.out.println("Database tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
} 