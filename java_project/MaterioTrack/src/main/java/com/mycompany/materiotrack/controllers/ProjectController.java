package com.mycompany.materiotrack.controllers;

import com.mycompany.materiotrack.services.ProjectService;
import com.mycompany.materiotrack.database.models.Project;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ProjectController {
    private final ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    public void addProject(String name, String location, Date startDate,  Date endDate, String status) {
        try {
            Project project = new Project();
            project.setName(name);
            project.setLocation(location);
            project.setStartDate(startDate);
            project.setStatus(status);
            project.setEndDate(endDate);
            projectService.addProject(project);
            System.out.println("Project added successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add project: " + e.getMessage());
        }
    }

    public List<Project> listProjects() throws SQLException {
        return projectService.getAllProjects();
    }

    public void updateProject(int id, String name, String location, Date startDate, String status) {
        try {
            Project project = projectService.getProjectById(id);
            if (project != null) {
                project.setName(name);
                project.setLocation(location);
                project.setStartDate(startDate);
                project.setStatus(status);
                projectService.updateProject(project);
                System.out.println("Project updated successfully!");
            } else {
                System.err.println("Project not found!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update project: " + e.getMessage());
        }
    }

    public void deleteProject(int id) {
        try {
            projectService.deleteProject(id);
            System.out.println("Project deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete project: " + e.getMessage());
        }
    }
} 