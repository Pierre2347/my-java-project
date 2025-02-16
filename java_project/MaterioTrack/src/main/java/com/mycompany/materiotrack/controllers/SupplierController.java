package com.mycompany.materiotrack.controllers;

import com.mycompany.materiotrack.services.SupplierService;
import com.mycompany.materiotrack.database.models.Supplier;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import com.mycompany.materiotrack.database.DatabaseConnection;

public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController() {
        this.supplierService = new SupplierService();
    }

    public void addSupplier(String name, String contactPerson, String phone, String email, String address) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Supplier supplier = new Supplier();
            supplier.setName(name);
            supplier.setContactPerson(contactPerson);
            supplier.setPhone(phone);
            supplier.setEmail(email);
            supplier.setAddress(address);
            supplierService.addSupplier(supplier, conn);
            System.out.println("Supplier added successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add supplier: " + e.getMessage());
        }
    }

    public List<Supplier> listSuppliers() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return supplierService.getAllSuppliers(conn);
        }
    }

    public void updateSupplier(int id, String name, String contactPerson, String phone, String email, String address) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Supplier supplier = supplierService.getSupplierById(id, conn);
            if (supplier != null) {
                supplier.setName(name);
                supplier.setContactPerson(contactPerson);
                supplier.setPhone(phone);
                supplier.setEmail(email);
                supplier.setAddress(address);
                supplierService.updateSupplier(supplier, conn);
                System.out.println("Supplier updated successfully!");
            } else {
                System.err.println("Supplier not found!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update supplier: " + e.getMessage());
        }
    }

    public void deleteSupplier(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            supplierService.deleteSupplier(id, conn);
            System.out.println("Supplier deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete supplier: " + e.getMessage());
        }
    }

    public Supplier getSupplierById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Supplier supplier = supplierService.getSupplierById(id, conn);
            System.out.println("Supplier retrieved successfully!");
            return supplier;
        } catch (SQLException e) {
            System.err.println("Failed to retrieve supplier: " + e.getMessage());
            return new Supplier();
        }
    }
} 