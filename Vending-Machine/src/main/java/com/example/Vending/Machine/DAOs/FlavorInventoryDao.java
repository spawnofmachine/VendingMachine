package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Exception.DaoException;
import com.example.Vending.Machine.Models.FlavorInventory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FlavorInventoryDao {
    private JdbcTemplate jdbcTemplate;

    public FlavorInventoryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<FlavorInventory> getAllFlavorQuantities() {
        return jdbcTemplate.query("SELECT * FROM flavor_inventory", this::mapRowToFlavorInventory);
    }

    public List<FlavorInventory> getAllFlavorsBelowPercent(int flavorQuantity) {
        return jdbcTemplate.query("SELECT * FROM flavor_inventory WHERE quantity < ?",
                this::mapRowToFlavorInventory, flavorQuantity);
    }

    public FlavorInventory getFlavorInventoryById(int flavorInventoryId) {
        return jdbcTemplate.queryForObject("SELECT * FROM flavor_inventory WHERE flavor_inventory_id = ?",
                this::mapRowToFlavorInventory, flavorInventoryId);
    }

    public FlavorInventory getFlavorQuantityByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM flavor_inventory fl WHERE fl.flavor_id = flavor.flavor_id",
                this::mapRowToFlavorInventory, name);
    }

    public FlavorInventory addFlavorQuantity(FlavorInventory flavorInventory) {
        FlavorInventory newFlavorInventory = null;
        String sql = "INSERT INTO flavor_inventory (flavor_id, quantity) VALUES (?, ?) RETURNING flavor_inventory_id";
        try {
            int flavorInventoryId = jdbcTemplate.queryForObject(sql, int.class, flavorInventory.getFlavorId(),
                    flavorInventory.getFlavorQuantity());
            newFlavorInventory = getFlavorInventoryById(flavorInventoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newFlavorInventory;
    }

    public FlavorInventory updateFlavor(FlavorInventory flavorInventory) {
        FlavorInventory updatedFlavorInventory = null;
        String sql = "UPDATE flavor_inventory SET flavor_id = ?, quantity = ? WHERE flavor_inventory_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, flavorInventory.getFlavorId(), flavorInventory.getFlavorQuantity(),
                    flavorInventory.getFlavorInventoryId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedFlavorInventory = getFlavorInventoryById(flavorInventory.getFlavorInventoryId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedFlavorInventory;
    }

    public int deleteFlavorQuantity(int flavorInventoryId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM flavor_inventory WHERE flavor_inventory_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, flavorInventoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private FlavorInventory mapRowToFlavorInventory(ResultSet row, int rowNumber) throws SQLException {
        FlavorInventory flavorInventory = new FlavorInventory();
        flavorInventory.setFlavorInventoryId(row.getInt("flavor_inventory_id"));
        flavorInventory.setFlavorId(row.getInt("flavor_id"));
        flavorInventory.setFlavorQuantity(row.getInt("quantity"));

        return flavorInventory;
    }
}
