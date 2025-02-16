/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.materiotrack;
import com.mycompany.materiotrack.database.DatabaseConnection;
import com.mycompany.materiotrack.database.DatabaseInitializer;
import com.mycompany.materiotrack.database.daos.*;
import com.mycompany.materiotrack.database.models.*;
import java.sql.SQLException;
import java.util.Date;
import com.mycompany.materiotrack.views.*;

/**
 *
 * @author Piere and bwemba
 */
public class MaterioTrack {

    public static void main(String[] args) {
        System.out.println("Initializing MaterioTrack...");
        
        // Add a shutdown hook to close the connection
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing database connection...");
//            DatabaseConnection.closeConnection();
        }));

        try {
            MainView mainview = new MainView();
            mainview.setVisible(true);

            // Initialize database tables
            DatabaseInitializer.initializeDatabase();
            
            // Test database connection
            DatabaseConnection.testConnection();
            
            // Create DAO instances
            MaterialDAO materialDAO = new MaterialDAO();
            SupplierDAO supplierDAO = new SupplierDAO();
            ProjectDAO projectDAO = new ProjectDAO();
            PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();
            StockMovementDAO stockMovementDAO = new StockMovementDAO();
            ProjectMaterialDAO projectMaterialDAO = new ProjectMaterialDAO();

            // Test data insertion
            testDatabaseOperations(
                materialDAO, 
                supplierDAO, 
                projectDAO, 
                purchaseOrderDAO, 
                stockMovementDAO, 
                projectMaterialDAO
            );
            
            System.out.println("Application started successfully!");
            
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testDatabaseOperations(
        MaterialDAO materialDAO,
        SupplierDAO supplierDAO,
        ProjectDAO projectDAO,
        PurchaseOrderDAO purchaseOrderDAO,
        StockMovementDAO stockMovementDAO,
        ProjectMaterialDAO projectMaterialDAO
    ) throws SQLException {
        // Insert a material
        Material cement = new Material();
        cement.setName("Cement");
        cement.setDescription("Portland Cement");
        cement.setCategory("Construction");
        cement.setQuantity(1000);
        cement.setUnit("kg");
        cement.setMinStockLevel(500);
        materialDAO.create(cement);
        System.out.println("Inserted material: Cement");

        // Insert a supplier first
        Supplier supplier = new Supplier();
        supplier.setName("ABC Construction Supplies");
        supplier.setContactPerson("John Doe");
        supplier.setPhone("123-456-7890");
        supplier.setEmail("john@abc.com");
        supplier.setAddress("123 Main St");
        supplierDAO.create(supplier);
        System.out.println("Inserted supplier: ABC Construction Supplies");

        // Insert a project
        Project project = new Project();
        project.setName("City Center Construction");
        project.setLocation("Downtown");
        project.setStartDate(new Date());
        project.setStatus("active");
        projectDAO.create(project);
        System.out.println("Inserted project: City Center Construction");

        Date deliveryDate=new Date();
        
        // Now use the supplier's ID for the purchase order
        System.out.println(supplier.getId());
        PurchaseOrder order = new PurchaseOrder();
        order.setSupplierId(1);
        order.setOrderDate(new Date());
        order.setDeliveryDate(deliveryDate);
        order.setStatus("pending");
        order.setTotalAmount(5000.00);
        purchaseOrderDAO.create(order);
        System.out.println("Inserted purchase order");

        // Insert a stock movement
        StockMovement movement = new StockMovement();
        movement.setMaterialId(1);
        movement.setQuantity(500);
        movement.setMovementType("IN");
        movement.setMovementDate(new Date());
        movement.setProjectId(1);
        stockMovementDAO.create(movement);
        System.out.println("Inserted stock movement");

        // Insert project material allocation
        ProjectMaterial projectMaterial = new ProjectMaterial();
        projectMaterial.setProjectId(1);
        projectMaterial.setMaterialId(1);
        projectMaterial.setAllocatedQuantity(300);
        projectMaterialDAO.create(projectMaterial);
        System.out.println("Inserted project material allocation");

        System.out.println("All test data inserted successfully!");
    }
}
