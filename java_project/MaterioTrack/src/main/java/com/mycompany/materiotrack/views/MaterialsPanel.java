/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.materiotrack.views;

import com.mycompany.materiotrack.controllers.MaterialController;
import com.mycompany.materiotrack.database.models.Material;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author blackheart
 */
public class MaterialsPanel extends JPanel {
    private final MaterialController materialController;
    private JTable materialsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton btnAdd, btnEdit, btnDelete;

    public MaterialsPanel(MaterialController materialController) {
        this.materialController = materialController;
        initComponents();
        loadMaterials();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table Model
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Description", "Category", "Quantity", "Unit", "Min Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable direct cell editing
            }
        };
        materialsTable = new JTable(tableModel);
        materialsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(25);
        searchField.putClientProperty("JTextField.placeholderText", "Search materials...");
        searchField.getDocument().addDocumentListener(new SearchDocumentListener());
        JButton btnSearch = new JButton("🔍");
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);

        // Control Buttons
        JPanel controlPanel = new JPanel();
        btnAdd = createControlButton("➕ Add", new Color(0, 153, 51), this::addMaterial);
        btnEdit = createControlButton("✏️ Edit", new Color(0, 102, 204), this::editMaterial);
        btnDelete = createControlButton("🗑️ Delete", new Color(204, 0, 0), this::deleteMaterial);
        controlPanel.add(btnAdd);
        controlPanel.add(btnEdit);
        controlPanel.add(btnDelete);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(materialsTable), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createControlButton(String text, Color color, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        return btn;
    }

    private void loadMaterials() {
        SwingWorker<List<Material>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Material> doInBackground() {
                return materialController.getAllMaterials();
            }

            @Override
            protected void done() {
                try {
                    tableModel.setRowCount(0);
                    for (Material material : get()) {
                        tableModel.addRow(new Object[]{
                            material.getId(),
                            material.getName(),
                            material.getDescription(),
                            material.getCategory(),
                            material.getQuantity(),
                            material.getUnit(),
                            material.getMinStockLevel()
                        });
                    }
                } catch (Exception e) {
                    showError("Error loading materials: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void addMaterial(ActionEvent e) {
        MaterialDialog dialog = new MaterialDialog((Frame) SwingUtilities.getWindowAncestor(this));
        if (dialog.showDialog()) {
            Material newMaterial = new Material(
                0, // ID
                dialog.getName(),
                dialog.getDescription(),
                dialog.getCategory(),
                dialog.getQuantity(),
                dialog.getUnit(),
                dialog.getMinStockLevel()
            );
            
            materialController.addMaterial(newMaterial);
            loadMaterials();
        }
    }

    private void editMaterial(ActionEvent e) {
        int selectedRow = materialsTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a material to edit");
            return;
        }

        int materialId = (int) tableModel.getValueAt(selectedRow, 0);
        Material existingMaterial = materialController.getMaterialById(materialId);
        System.out.println(materialId);
        MaterialDialog dialog = new MaterialDialog((Frame) SwingUtilities.getWindowAncestor(this));
        dialog.setName(existingMaterial.getName());
        dialog.setCategory(existingMaterial.getCategory());
        dialog.setQuantity(existingMaterial.getQuantity());
        dialog.setUnit(existingMaterial.getUnit());
        dialog.setDescription(existingMaterial.getDescription());
        dialog.setMinStockLevel(existingMaterial.getMinStockLevel());

        if (dialog.showDialog()) {
            existingMaterial.setName(dialog.getName());
            existingMaterial.setCategory(dialog.getCategory());
            existingMaterial.setQuantity(dialog.getQuantity());
            existingMaterial.setUnit(dialog.getUnit());
            existingMaterial.setDescription(dialog.getDescription());
            existingMaterial.setMinStockLevel(dialog.getMinStockLevel());
            
            materialController.updateMaterial(existingMaterial);
            loadMaterials();
        }
    }

    private void deleteMaterial(ActionEvent e) {
        int selectedRow = materialsTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a material to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this material?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int materialId = (int) tableModel.getValueAt(selectedRow, 0);
            materialController.deleteMaterial(materialId);
            loadMaterials();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class SearchDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filterTable();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterTable();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterTable();
        }

        private void filterTable() {
            String query = searchField.getText().toLowerCase();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            materialsTable.setRowSorter(sorter);
            
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    // Material input dialog
    private static class MaterialDialog extends JDialog {
        private final JTextField nameField = new JTextField(20);
        private final JComboBox<String> categoryCombo = new JComboBox<>(
            new String[]{"Construction", "Electrical", "Plumbing"});
        private final JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        private final JTextField unitField = new JTextField(10);
        private final JTextField descriptionField = new JTextField(20);
        private final JSpinner minStockSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10000.0, 1.0));
        private boolean confirmed = false;

        public MaterialDialog(Frame owner) {
            super(owner, "Material Details", true);
            setLayout(new GridLayout(0, 2, 10, 10));
            
            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Category:"));
            add(categoryCombo);
            add(new JLabel("Quantity:"));
            add(quantitySpinner);
            add(new JLabel("Unit:"));
            add(unitField);
            add(new JLabel("Description:"));
            add(descriptionField);
            add(new JLabel("Min Stock Level:"));
            add(minStockSpinner);

            JButton btnOK = new JButton("OK");
            btnOK.addActionListener(e -> {
                confirmed = true;
                dispose();
            });
            
            JButton btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(e -> dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(btnOK);
            buttonPanel.add(btnCancel);
            
            add(buttonPanel, BorderLayout.SOUTH);
            pack();
            setLocationRelativeTo(owner);
        }

        public boolean showDialog() {
            setVisible(true);
            return confirmed;
        }

        // Getters
        public String getName() { return nameField.getText(); }
        public String getCategory() { return (String) categoryCombo.getSelectedItem(); }
        public int getQuantity() { return (Integer) quantitySpinner.getValue(); }
        public String getUnit() { return unitField.getText(); }
        public String getDescription() { return descriptionField.getText(); }
        public double getMinStockLevel() { return (Double) minStockSpinner.getValue(); }

        // Setters for editing
        public void setName(String name) { nameField.setText(name); }
        public void setCategory(String category) { categoryCombo.setSelectedItem(category); }
        public void setQuantity(double quantity) { quantitySpinner.setValue(quantity); }
        public void setUnit(String unit) { unitField.setText(unit); }
        public void setDescription(String description) { descriptionField.setText(description); }
        public void setMinStockLevel(double minStockLevel) { minStockSpinner.setValue(minStockLevel); }
    }
}
