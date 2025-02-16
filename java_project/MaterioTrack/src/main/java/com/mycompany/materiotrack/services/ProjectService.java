package com.mycompany.materiotrack.services;

import com.mycompany.materiotrack.database.daos.ProjectDAO;
import com.mycompany.materiotrack.database.models.Project;
import java.sql.SQLException;
import java.util.List;

public class ProjectService {
    private final ProjectDAO projectDAO;

    public ProjectService() {
        this.projectDAO = new ProjectDAO();
    }

    public void addProject(Project project) throws SQLException {
        System.out.println("creating");
        projectDAO.create(project);
    }

    public Project getProjectById(int id) throws SQLException {
        return projectDAO.read(id);
    }

    public List<Project> getAllProjects() throws SQLException {
        return projectDAO.getAll();
    }

    public void updateProject(Project project) throws SQLException {
        projectDAO.update(project);
    }

    public void deleteProject(int id) throws SQLException {
        projectDAO.delete(id);
    }
} 