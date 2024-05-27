package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Exception.DaoException;
import com.example.Vending.Machine.Models.CupInventory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CupInventoryDao {
    private JdbcTemplate jdbcTemplate;

    public CupInventoryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<CupInventory> getAllCupsQuantity() {
        return jdbcTemplate.query("SELECT * FROM cup_inventory", this::mapRowToCupInventory);
    }

    public CupInventory getCupInventoryById(int cupInventoryId) {
        return jdbcTemplate.queryForObject("SELECT * FROM cup_inventory WHERE cup_inventory_id = ?",
                this::mapRowToCupInventory, cupInventoryId);
    }

    public List<CupInventory> getAllCupsBelowQuantity(int cupQuantity) {
        return jdbcTemplate.query("SELECT * FROM cup_inventory WHERE quantity < ?", this::mapRowToCupInventory, cupQuantity);
    }

    public CupInventory getCupQuantityBySize(String size) {
        return jdbcTemplate.queryForObject("SELECT * FROM cup_inventory ci JOIN cup c ON ci.cup_id = c.cup_id " +
                "WHERE size = ?", this::mapRowToCupInventory, size);
    }

    public CupInventory addCupQuantity(CupInventory cupInventory) {
        CupInventory newCupInventory = null;
        String sql = "INSERT INTO cup_inventory (cup_id, quantity) Values (?, ?) RETURNING cup_inventory_id";
        try {
            int cupInventoryId = jdbcTemplate.queryForObject(sql, int.class, cupInventory.getCupId(), cupInventory.getCupQuantity());
            newCupInventory = getCupInventoryById(cupInventoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCupInventory;
    }

    public CupInventory updateCupQuantity(CupInventory cupInventory) {
        CupInventory updatedCupInventory = null;
        String sql = "UPDATE cup_inventory SET quantity = ? WHERE cup_inventory_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, cupInventory.getCupQuantity(), cupInventory.getCupInventoryId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedCupInventory = getCupInventoryById(cupInventory.getCupInventoryId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCupInventory;
    }

    public int deleteCupQuantity(int cupInventoryId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM cup_inventory WHERE cup_inventory_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, cupInventoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private CupInventory mapRowToCupInventory(ResultSet row, int rowNumber) throws SQLException {
        CupInventory cupInventory = new CupInventory();
        cupInventory.setCupInventoryId(row.getInt("cup_inventory_id"));
        cupInventory.setCupId(row.getInt("cup_id"));
        cupInventory.setCupQuantity(row.getInt("quantity"));

        return cupInventory;
    }
}
