package com.mycompany.materiotrack.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
    protected Connection connection;

    public BaseDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize DAO: " + e.getMessage(), e);
        }
    }

    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    protected abstract void setCreateParameters(PreparedStatement stmt, T entity) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement stmt, T entity) throws SQLException;

    // Add the getParameterCount method
    protected abstract int getParameterCount(T entity);

    // Add the missing methods
    public abstract T read(int id) throws SQLException;
    public abstract List<T> getAll() throws SQLException;
    public abstract void update(T entity) throws SQLException;
    public abstract void delete(int id) throws SQLException;

    // Updated create method
    public void create(T entity) throws SQLException {
        // Generate the column names dynamically
        String columns = getColumnNames(entity);
        String placeholders = getParameterPlaceholders(entity);
        String query = "INSERT INTO " + getTableName() + " (" + columns + ") VALUES (" + placeholders + ")";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, entity);
            stmt.executeUpdate();
        }
    }

    // Helper method to generate parameter placeholders
    private String getParameterPlaceholders(T entity) {
        // Count the number of parameters needed
        int parameterCount = getParameterCount(entity);
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < parameterCount; i++) {
            placeholders.append("?");
            if (i < parameterCount - 1) {
                placeholders.append(", ");
            }
        }
        return placeholders.toString();
    }

    // Helper method to generate column names
    protected abstract String getColumnNames(T entity);

    protected abstract Connection getConnection() throws SQLException;
} 