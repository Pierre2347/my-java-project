/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.materiotrack.views;

import com.mycompany.materiotrack.controllers.SupplierController;
import com.mycompany.materiotrack.database.models.Supplier;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author blackheart
 */
public class SuppliersPanel extends JPanel {
    private JTable suppliersTable;
    private JButton btnNewSupplier, btnAddSupplier, btnUpdateSupplier, btnDeleteSupplier;
    private DefaultTableModel tableModel;
    private SupplierController supplierController;

    public SuppliersPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Initialize SupplierController
        supplierController = new SupplierController();

        // Suppliers Table
        String[] columnNames = {"ID", "Name", "Contact", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        suppliersTable = new JTable(tableModel);
        suppliersTable.setFillsViewportHeight(true);
        suppliersTable.setRowHeight(25);
        suppliersTable.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Action Buttons
        btnAddSupplier = new JButton("Add Supplier");
        btnUpdateSupplier = new JButton("Update Supplier");
        btnDeleteSupplier = new JButton("Delete Supplier");

        // Style Buttons
        styleButton(btnAddSupplier, new Color(76, 175, 80));
        styleButton(btnUpdateSupplier, new Color(33, 150, 243));
        styleButton(btnDeleteSupplier, new Color(244, 67, 54));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAddSupplier);
        buttonPanel.add(btnUpdateSupplier);
        buttonPanel.add(btnDeleteSupplier);

        // Add action listeners
        btnAddSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSupplierDialog("Add");
            }
        });

        btnUpdateSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSupplierDialog("Update");
            }
        });

        btnDeleteSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedSupplier();
            }
        });

        // Layout
        add(new JScrollPane(suppliersTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load suppliers into the table
        loadSuppliers();
    }

    // Method to style buttons
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    // Method to load suppliers into the table
    private void loadSuppliers() {
        try {
            List<Supplier> suppliers = supplierController.listSuppliers();
            updateSupplierTable(suppliers);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load suppliers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the suppliers table
    public void updateSupplierTable(List<Supplier> suppliers) {
        tableModel.setRowCount(0); // Clear existing data
        for (Supplier supplier : suppliers) {
            Object[] row = {
                supplier.getId(),
                supplier.getName(),
                supplier.getContactPerson(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getAddress()
            };
            tableModel.addRow(row);
        }
    }

    // Method to show the supplier dialog
    private void showSupplierDialog(String action) {
        JTextField nameField = new JTextField();
        JTextField contactPersonField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();

        Object[] fields = {
            "Name:", nameField,
            "Contact Person:", contactPersonField,
            "Phone:", phoneField,
            "Email:", emailField,
            "Address:", addressField
        };

        int option = JOptionPane.showConfirmDialog(
            this,
            fields,
            action + " Supplier",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String contactPerson = contactPersonField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();

                if (action.equals("Add")) {
                    supplierController.addSupplier(name, contactPerson, phone, email, address);
                } else if (action.equals("Update")) {
                    int selectedRow = suppliersTable.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Please select a supplier to update.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    supplierController.updateSupplier(id, name, contactPerson, phone, email, address);
                }

                loadSuppliers(); // Refresh the table
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to delete the selected supplier
    private void deleteSelectedSupplier() {
        int selectedRow = suppliersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0); // Get the ID from the first column
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this supplier?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                supplierController.deleteSupplier(id);
                loadSuppliers(); // Refresh the table
                JOptionPane.showMessageDialog(this, "Supplier deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to delete supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
