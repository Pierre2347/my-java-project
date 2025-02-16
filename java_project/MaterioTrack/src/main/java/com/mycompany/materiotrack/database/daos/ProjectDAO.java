package com.mycompany.materiotrack.database.daos;

import com.mycompany.materiotrack.database.BaseDAO;
import com.mycompany.materiotrack.database.DatabaseConnection;
import com.mycompany.materiotrack.database.models.Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends BaseDAO<Project> {

    @Override
    protected String getTableName() {
        return "projects";
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected Project mapResultSetToEntity(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setName(rs.getString("name"));
        project.setLocation(rs.getString("location"));
        project.setStartDate(rs.getDate("start_date"));
        project.setEndDate(rs.getDate("end_date"));
        project.setStatus(rs.getString("status"));
        return project;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Project project) throws SQLException {
        stmt.setString(1, project.getName());
        stmt.setString(2, project.getLocation());
        stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
        stmt.setDate(4, project.getEndDate() != null ? 
            new java.sql.Date(project.getEndDate().getTime()) : null);
        stmt.setString(5, project.getStatus());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Project project) throws SQLException {
        stmt.setString(1, project.getName());
        stmt.setString(2, project.getLocation());
        stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
        stmt.setDate(4, project.getEndDate() != null ? 
            new java.sql.Date(project.getEndDate().getTime()) : null);
        stmt.setString(5, project.getStatus());
        stmt.setInt(6, project.getId());
    }

    @Override
    protected int getParameterCount(Project entity) {
        return 5; // name, location, start_date, end_date, status
    }

    @Override
    protected String getColumnNames(Project entity) {
        return "name, location, start_date, end_date, status";
    }

    // Additional methods
    public List<Project> findByStatus(String status) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE status = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(mapResultSetToEntity(rs));
                }
            }
        }
        return projects;
    }

    public List<Project> findActiveProjects() throws SQLException {
        return findByStatus("active");
    }

    @Override
    public Project read(int id) throws SQLException {
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
    public List<Project> getAll() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                projects.add(mapResultSetToEntity(rs));
            }
        }
        return projects;
    }

    @Override
    public void update(Project project) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET name = ?, location = ?, start_date = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, project);
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