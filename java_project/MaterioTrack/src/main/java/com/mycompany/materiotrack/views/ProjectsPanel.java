package com.mycompany.materiotrack.views;

import com.mycompany.materiotrack.controllers.ProjectController;
import com.mycompany.materiotrack.database.models.Project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class ProjectsPanel extends JPanel {
    private JTable projectsTable;
    private JButton btnNewProject;
    private DefaultTableModel tableModel;
    private ProjectController projectController;

    public ProjectsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Initialize ProjectController
        projectController = new ProjectController();

        // Project Timeline
        JPanel timelinePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Custom timeline drawing logic here
                g.setColor(Color.BLUE);
                g.drawLine(50, 75, 550, 75); // Draw a simple timeline
            }
        };
        timelinePanel.setPreferredSize(new Dimension(600, 150));
        timelinePanel.setBackground(Color.LIGHT_GRAY);

        // Project List
        String[] columnNames = {"ID", "Name", "Location", "Start Date", "End Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        projectsTable = new JTable(tableModel);
        projectsTable.setFillsViewportHeight(true);
        projectsTable.setRowHeight(25);
        projectsTable.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Action Buttons
        btnNewProject = new JButton("New Project");
        btnNewProject.setIcon(new ImageIcon("src/main/resources/icons/add.png"));
        btnNewProject.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnNewProject.setBackground(new Color(0, 102, 204));
        btnNewProject.setForeground(Color.WHITE);

        // Add action listener for the new project button
        btnNewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNewProjectDialog();
            }
        });

        // Layout
        add(new JScrollPane(projectsTable), BorderLayout.CENTER);
        add(timelinePanel, BorderLayout.NORTH);
        add(btnNewProject, BorderLayout.SOUTH);

        // Load projects into the table
        loadProjects();
    }

    // Method to load projects into the table
    private void loadProjects() {
        try {
            List<Project> projects = projectController.listProjects();
            updateProjectsTable(projects);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load projects: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the projects table
    public void updateProjectsTable(List<Project> projects) {
        tableModel.setRowCount(0); // Clear existing data
        for (Project project : projects) {
            Object[] row = {
                project.getId(),
                project.getName(),
                project.getLocation(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    // Method to show the new project dialog
    private void showNewProjectDialog() {
        JTextField nameField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JTextField statusField = new JTextField();

        Object[] fields = {
            "Name:", nameField,
            "Location:", locationField,
            "Start Date (YYYY-MM-DD):", startDateField,
            "End Date (YYYY-MM-DD):", endDateField,
            "Status:", statusField
        };

        int option = JOptionPane.showConfirmDialog(
            this,
            fields,
            "New Project",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String location = locationField.getText();
                Date startDate = java.sql.Date.valueOf(startDateField.getText());
                Date endDate = java.sql.Date.valueOf(endDateField.getText());
                String status = statusField.getText();

                projectController.addProject(name, location, startDate, endDate, status);
                loadProjects(); // Refresh the table
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Getter for the new project button
    public JButton getBtnNewProject() {
        return btnNewProject;
    }
}