package com.mycompany.materiotrack.controllers;

import com.mycompany.materiotrack.services.ProjectMaterialService;
import com.mycompany.materiotrack.database.models.ProjectMaterial;
import java.sql.SQLException;
import java.util.List;

public class ProjectMaterialController {
    private final ProjectMaterialService projectMaterialService;

    public ProjectMaterialController() {
        this.projectMaterialService = new ProjectMaterialService();
    }

    public void addProjectMaterial(int projectId, int materialId, double allocatedQuantity) {
        try {
            ProjectMaterial projectMaterial = new ProjectMaterial();
            projectMaterial.setProjectId(projectId);
            projectMaterial.setMaterialId(materialId);
            projectMaterial.setAllocatedQuantity(allocatedQuantity);
            projectMaterialService.addProjectMaterial(projectMaterial);
            System.out.println("Project material added successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add project material: " + e.getMessage());
        }
    }

    public void listProjectMaterials() {
        try {
            List<ProjectMaterial> projectMaterials = projectMaterialService.getAllProjectMaterials();
            for (ProjectMaterial projectMaterial : projectMaterials) {
                System.out.println(projectMaterial);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch project materials: " + e.getMessage());
        }
    }

    public void updateProjectMaterial(int id, int projectId, int materialId, double allocatedQuantity) {
        try {
            ProjectMaterial projectMaterial = projectMaterialService.getProjectMaterialById(id);
            if (projectMaterial != null) {
                projectMaterial.setProjectId(projectId);
                projectMaterial.setMaterialId(materialId);
                projectMaterial.setAllocatedQuantity(allocatedQuantity);
                projectMaterialService.updateProjectMaterial(projectMaterial);
                System.out.println("Project material updated successfully!");
            } else {
                System.err.println("Project material not found!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update project material: " + e.getMessage());
        }
    }

    public void deleteProjectMaterial(int id) {
        try {
            projectMaterialService.deleteProjectMaterial(id);
            System.out.println("Project material deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete project material: " + e.getMessage());
        }
    }
} 