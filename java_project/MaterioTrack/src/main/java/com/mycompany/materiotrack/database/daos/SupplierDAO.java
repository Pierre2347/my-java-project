package com.mycompany.materiotrack.database.daos;

import com.mycompany.materiotrack.database.BaseDAO;
import com.mycompany.materiotrack.database.models.Supplier;
import com.mycompany.materiotrack.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO extends BaseDAO<Supplier> {

    @Override
    protected String getTableName() {
        return "suppliers";
    }
       @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected Supplier mapResultSetToEntity(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setId(rs.getInt("id"));
        supplier.setName(rs.getString("name"));
        supplier.setContactPerson(rs.getString("contact_person"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setEmail(rs.getString("email"));
        supplier.setAddress(rs.getString("address"));
        return supplier;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Supplier supplier) throws SQLException {
        stmt.setString(1, supplier.getName());
        stmt.setString(2, supplier.getContactPerson());
        stmt.setString(3, supplier.getPhone());
        stmt.setString(4, supplier.getEmail());
        stmt.setString(5, supplier.getAddress());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Supplier supplier) throws SQLException {
        stmt.setString(1, supplier.getName());
        stmt.setString(2, supplier.getContactPerson());
        stmt.setString(3, supplier.getPhone());
        stmt.setString(4, supplier.getEmail());
        stmt.setString(5, supplier.getAddress());
        stmt.setInt(6, supplier.getId());
    }

    @Override
    protected int getParameterCount(Supplier entity) {
        return 5; // Number of parameters in the INSERT query
    }

    @Override
    protected String getColumnNames(Supplier entity) {
        return "name, contact_person, phone, email, address";
    }

    // Additional methods specific to Supplier
    public List<Supplier> findByName(String name) throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE name LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    suppliers.add(mapResultSetToEntity(rs));
                }
            }
        }
        return suppliers;
    }

    @Override
    public Supplier read(int id) throws SQLException {
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
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                suppliers.add(mapResultSetToEntity(rs));
            }
        }
        return suppliers;
    }

    @Override
    public void update(Supplier supplier) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET name = ?, contact_person = ?, phone = ?, email = ?, address = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, supplier);
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