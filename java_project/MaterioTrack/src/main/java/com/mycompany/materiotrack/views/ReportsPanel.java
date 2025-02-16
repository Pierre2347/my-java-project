/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.materiotrack.views;

import com.mycompany.materiotrack.controllers.ProjectController;
import com.mycompany.materiotrack.controllers.StockMovementController;
import com.mycompany.materiotrack.controllers.SupplierController;
import com.mycompany.materiotrack.database.models.Project;
import com.mycompany.materiotrack.database.models.StockMovement;
import com.mycompany.materiotrack.database.models.Supplier;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author blackheart
 */
public class ReportsPanel extends JPanel {
    private JComboBox<String> reportType;
    private JButton btnGenerate;
    private JTextArea reportArea;

    public ReportsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Report Selector
        JPanel controlPanel = new JPanel();
        reportType = new JComboBox<>(new String[]{
            "Stock Levels", "Project Budgets", "Supplier Performance"
        });
        btnGenerate = new JButton("Generate Report");
        controlPanel.add(reportType);
        controlPanel.add(btnGenerate);

        // Report Display Area
        reportArea = new JTextArea();
        reportArea.setEditable(false);

        // Add action listener to the generate button
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);
    }

    // Method to generate the report
    private void generateReport() {
        String selectedReport = (String) reportType.getSelectedItem();
        StringBuilder report = new StringBuilder();

        try {
            switch (selectedReport) {
                case "Stock Levels":
                    report.append("=== Stock Levels Report ===\n");
                    report.append(generateStockLevelsReport());
                    break;
                case "Project Budgets":
                    report.append("=== Project Budgets Report ===\n");
                    report.append(generateProjectBudgetsReport());
                    break;
                case "Supplier Performance":
                    report.append("=== Supplier Performance Report ===\n");
                    report.append(generateSupplierPerformanceReport());
                    break;
                default:
                    report.append("No report selected.");
            }
        } catch (Exception e) {
            report.append("Error generating report: " + e.getMessage());
        }

        reportArea.setText(report.toString());
    }

    // Method to generate the stock levels report
    private String generateStockLevelsReport() throws SQLException {
        StockMovementController stockController = new StockMovementController();
        List<StockMovement> stockMovements = stockController.listStockMovements();
        StringBuilder report = new StringBuilder();

        for (StockMovement movement : stockMovements) {
            report.append("Material ID: ").append(movement.getMaterialId())
                  .append(", Quantity: ").append(movement.getQuantity())
                  .append(", Type: ").append(movement.getMovementType())
                  .append(", Date: ").append(movement.getMovementDate())
                  .append("\n");
        }

        return report.toString();
    }

    // Method to generate the project budgets report
    private String generateProjectBudgetsReport() throws SQLException {
        ProjectController projectController = new ProjectController();
        List<Project> projects = projectController.listProjects();
        StringBuilder report = new StringBuilder();

//        for (Project project : projects) {
//            report.append("Project ID: ").append(project.getId())
//                  .append(", Name: ").append(project.getName())
//                  .append(", Budget: ").append(project.getBudget())
//                  .append("\n");
//        }

        return report.toString();
    }

    // Method to generate the supplier performance report
    private String generateSupplierPerformanceReport() throws SQLException {
        SupplierController supplierController = new SupplierController();
        List<Supplier> suppliers = supplierController.listSuppliers();
        StringBuilder report = new StringBuilder();

        for (Supplier supplier : suppliers) {
            report.append("Supplier ID: ").append(supplier.getId())
                  .append(", Name: ").append(supplier.getName())
                  .append(", Contact: ").append(supplier.getContactPerson())
                  .append("\n");
        }

        return report.toString();
    }
}
