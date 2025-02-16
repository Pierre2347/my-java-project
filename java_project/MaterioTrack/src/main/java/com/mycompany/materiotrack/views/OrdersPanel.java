/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.materiotrack.views;

import com.mycompany.materiotrack.controllers.PurchaseOrderController;
import com.mycompany.materiotrack.database.models.PurchaseOrder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author blackheart
 */
public class OrdersPanel extends JPanel {
    private JTable ordersTable;
    private JButton btnNewOrder, btnAddOrder, btnUpdateOrder, btnDeleteOrder;
    private DefaultTableModel tableModel;
    private PurchaseOrderController purchaseOrderController;

    public OrdersPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Initialize PurchaseOrderController
        purchaseOrderController = new PurchaseOrderController();

        // Orders Table
        String[] columnNames = {"ID", "Supplier ID", "Order Date", "Delivery Date", "Status", "Total Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ordersTable = new JTable(tableModel);
        ordersTable.setFillsViewportHeight(true);
        ordersTable.setRowHeight(25);
        ordersTable.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // New Order Button
        btnNewOrder = new JButton("Create Purchase Order");
        btnNewOrder.setIcon(new ImageIcon("src/main/resources/icons/order.png"));

        // Action Buttons
        btnAddOrder = new JButton("Add Order");
        btnUpdateOrder = new JButton("Update Order");
        btnDeleteOrder = new JButton("Delete Order");

        // Style Buttons
        styleButton(btnNewOrder, new Color(76, 175, 80));
        styleButton(btnAddOrder, new Color(76, 175, 80));
        styleButton(btnUpdateOrder, new Color(33, 150, 243));
        styleButton(btnDeleteOrder, new Color(244, 67, 54));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnNewOrder);
        buttonPanel.add(btnAddOrder);
        buttonPanel.add(btnUpdateOrder);
        buttonPanel.add(btnDeleteOrder);

        // Add action listeners
        btnNewOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrderDialog("Add");
            }
        });

        btnAddOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrderDialog("Add");
            }
        });

        btnUpdateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrderDialog("Update");
            }
        });

        btnDeleteOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedOrder();
            }
        });

        // Layout
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load purchase orders into the table
        loadPurchaseOrders();
    }

    // Method to style buttons
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    // Method to load purchase orders into the table
    private void loadPurchaseOrders() {
        try {
            List<PurchaseOrder> orders = purchaseOrderController.listPurchaseOrders();
            updateOrderTable(orders);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load purchase orders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the purchase orders table
    public void updateOrderTable(List<PurchaseOrder> orders) {
        tableModel.setRowCount(0); // Clear existing data
        for (PurchaseOrder order : orders) {
            Object[] row = {
                order.getId(),
                order.getSupplierId(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getStatus(),
                order.getTotalAmount()
            };
            tableModel.addRow(row);
        }
    }

    // Method to show the order dialog
    private void showOrderDialog(String action) {
        JTextField supplierIdField = new JTextField();
        JTextField orderDateField = new JTextField();
        JTextField deliveryDateField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField totalAmountField = new JTextField();

        Object[] fields = {
            "Supplier ID:", supplierIdField,
            "Order Date (YYYY-MM-DD):", orderDateField,
            "Delivery Date (YYYY-MM-DD):", deliveryDateField,
            "Status:", statusField,
            "Total Amount:", totalAmountField
        };

        int option = JOptionPane.showConfirmDialog(
            this,
            fields,
            action + " Purchase Order",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                int supplierId = Integer.parseInt(supplierIdField.getText());
                Date orderDate = java.sql.Date.valueOf(orderDateField.getText());
                Date deliveryDate = java.sql.Date.valueOf(deliveryDateField.getText());
                String status = statusField.getText();
                double totalAmount = Double.parseDouble(totalAmountField.getText());

                if (action.equals("Add")) {
                    purchaseOrderController.addPurchaseOrder(supplierId, orderDate, deliveryDate, status, totalAmount);
                } else if (action.equals("Update")) {
                    int selectedRow = ordersTable.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Please select an order to update.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    purchaseOrderController.updatePurchaseOrder(id, supplierId, orderDate, status, totalAmount);
                }

                loadPurchaseOrders(); // Refresh the table
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to delete the selected order
    private void deleteSelectedOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0); // Get the ID from the first column
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this order?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                purchaseOrderController.deletePurchaseOrder(id);
                loadPurchaseOrders(); // Refresh the table
                JOptionPane.showMessageDialog(this, "Order deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to delete order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
