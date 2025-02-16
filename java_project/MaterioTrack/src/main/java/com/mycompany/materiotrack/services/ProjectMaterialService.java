package com.mycompany.materiotrack.services;

import com.mycompany.materiotrack.database.daos.ProjectMaterialDAO;
import com.mycompany.materiotrack.database.models.ProjectMaterial;
import java.sql.SQLException;
import java.util.List;

public class ProjectMaterialService {
    private final ProjectMaterialDAO projectMaterialDAO;

    public ProjectMaterialService() {
        this.projectMaterialDAO = new ProjectMaterialDAO();
    }

    public void addProjectMaterial(ProjectMaterial projectMaterial) throws SQLException {
        projectMaterialDAO.create(projectMaterial);
    }

    public ProjectMaterial getProjectMaterialById(int id) throws SQLException {
        return projectMaterialDAO.read(id);
    }

    public List<ProjectMaterial> getAllProjectMaterials() throws SQLException {
        return projectMaterialDAO.getAll();
    }

    public void updateProjectMaterial(ProjectMaterial projectMaterial) throws SQLException {
        projectMaterialDAO.update(projectMaterial);
    }

    public void deleteProjectMaterial(int id) throws SQLException {
        projectMaterialDAO.delete(id);
    }
} 