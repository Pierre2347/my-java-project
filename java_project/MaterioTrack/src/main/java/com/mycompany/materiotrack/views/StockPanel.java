package com.mycompany.materiotrack.views;

import com.mycompany.materiotrack.controllers.StockMovementController;
import com.mycompany.materiotrack.database.models.StockMovement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class StockPanel extends JPanel {
    private JTable stockTable;
    private JButton btnReceive, btnIssue;
    private DefaultTableModel tableModel;
    private StockMovementController stockMovementController;

    public StockPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Initialize StockMovementController
        stockMovementController = new StockMovementController();

        // Stock Movement Table
        String[] columnNames = {"Date", "Material", "Type", "Quantity", "Location"};
        tableModel = new DefaultTableModel(columnNames, 0);
        stockTable = new JTable(tableModel);
        stockTable.setFillsViewportHeight(true);
        stockTable.setRowHeight(25);
        stockTable.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Quick Actions
        JPanel actionPanel = new JPanel();
        btnReceive = createStockButton("📥 Receive", new Color(76, 175, 80));
        btnIssue = createStockButton("📤 Issue", new Color(244, 67, 54));
        actionPanel.add(btnReceive);
        actionPanel.add(btnIssue);

        // Add action listeners
        btnReceive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStockMovementDialog("Receive");
            }
        });

        btnIssue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStockMovementDialog("Issue");
            }
        });

        // Layout
        add(new JScrollPane(stockTable), BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Load stock movements into the table
        loadStockMovements();
    }

    // Method to create styled buttons
    private JButton createStockButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    // Method to load stock movements into the table
    private void loadStockMovements() {
        try {
            List<StockMovement> movements = stockMovementController.listStockMovements();
            updateStockTable(movements);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load stock movements: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the stock movements table
    public void updateStockTable(List<StockMovement> movements) {
        tableModel.setRowCount(0); // Clear existing data
        for (StockMovement movement : movements) {
            Object[] row = {
                movement.getMovementDate(),
                movement.getMaterialId(),
                movement.getMovementType(),
                movement.getQuantity(),
                movement.getProjectId()
            };
            tableModel.addRow(row);
        }
    }

    // Method to show the stock movement dialog
    private void showStockMovementDialog(String movementType) {
        JTextField materialIdField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField movementDateField = new JTextField();
        JTextField projectIdField = new JTextField();

        Object[] fields = {
            "Material ID:", materialIdField,
            "Quantity:", quantityField,
            "Movement Date (YYYY-MM-DD):", movementDateField,
            "Project ID:", projectIdField
        };

        int option = JOptionPane.showConfirmDialog(
            this,
            fields,
            movementType + " Stock",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                int materialId = Integer.parseInt(materialIdField.getText());
                double quantity = Double.parseDouble(quantityField.getText());
                Date movementDate = java.sql.Date.valueOf(movementDateField.getText());
                int projectId = Integer.parseInt(projectIdField.getText());

                stockMovementController.addStockMovement(materialId, quantity, movementType, movementDate, projectId);
                loadStockMovements(); // Refresh the table
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

