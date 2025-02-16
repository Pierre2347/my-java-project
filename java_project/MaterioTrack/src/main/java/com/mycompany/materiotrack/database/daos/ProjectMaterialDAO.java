package com.mycompany.materiotrack.database.daos;

import com.mycompany.materiotrack.database.BaseDAO;
import com.mycompany.materiotrack.database.DatabaseConnection;
import com.mycompany.materiotrack.database.models.ProjectMaterial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectMaterialDAO extends BaseDAO<ProjectMaterial> {

    @Override
    protected String getTableName() {
        return "project_materials";
    }
    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected ProjectMaterial mapResultSetToEntity(ResultSet rs) throws SQLException {
        ProjectMaterial pm = new ProjectMaterial();
        pm.setId(rs.getInt("id"));
        pm.setProjectId(rs.getInt("project_id"));
        pm.setMaterialId(rs.getInt("material_id"));
        pm.setAllocatedQuantity(rs.getDouble("allocated_quantity"));
        return pm;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, ProjectMaterial pm) throws SQLException {
        stmt.setInt(1, pm.getProjectId());
        stmt.setInt(2, pm.getMaterialId());
        stmt.setDouble(3, pm.getAllocatedQuantity());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, ProjectMaterial pm) throws SQLException {
        stmt.setInt(1, pm.getProjectId());
        stmt.setInt(2, pm.getMaterialId());
        stmt.setDouble(3, pm.getAllocatedQuantity());
        stmt.setInt(4, pm.getId());
    }

    @Override
    protected int getParameterCount(ProjectMaterial entity) {
        return 3; // project_id, material_id, allocated_quantity
    }

    @Override
    protected String getColumnNames(ProjectMaterial entity) {
        return "project_id, material_id, allocated_quantity";
    }

    // Additional methods
    public List<ProjectMaterial> findByProject(int projectId) throws SQLException {
        List<ProjectMaterial> materials = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materials.add(mapResultSetToEntity(rs));
                }
            }
        }
        return materials;
    }

    public List<ProjectMaterial> findByMaterial(int materialId) throws SQLException {
        List<ProjectMaterial> projects = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE material_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(mapResultSetToEntity(rs));
                }
            }
        }
        return projects;
    }

    @Override
    public ProjectMaterial read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<ProjectMaterial> getAll() throws SQLException {
        List<ProjectMaterial> projectMaterials = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                projectMaterials.add(mapResultSetToEntity(rs));
            }
        }
        return projectMaterials;
    }

    @Override
    public void update(ProjectMaterial projectMaterial) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET project_id = ?, material_id = ?, allocated_quantity = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, projectMaterial);
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
} 