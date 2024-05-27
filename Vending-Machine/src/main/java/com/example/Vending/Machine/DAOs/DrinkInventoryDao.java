package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Exception.DaoException;
import com.example.Vending.Machine.Models.DrinkInventory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DrinkInventoryDao {
    private JdbcTemplate jdbcTemplate;

    public DrinkInventoryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DrinkInventory> getAllDrinkQuantities() {
        return jdbcTemplate.query("SELECT * FROM drink_inventory", this::mapRowToDrinkInventory);
    }

    public List<DrinkInventory> getAllDrinkQuantitiesBelowpercent(int drinkQuantity) {
        return jdbcTemplate.query("SELECT * FROM drink_inventory WHERE quantity < ?", this::mapRowToDrinkInventory, drinkQuantity);
    }

    public DrinkInventory getDrinkQuantityById(int drinkInventoryId) {
        return jdbcTemplate.queryForObject("SELECT * FROM drink_inventory WHERE drink_inventory_id = ?", this::mapRowToDrinkInventory, drinkInventoryId);
    }

    public DrinkInventory addDrinkQuantity(DrinkInventory drinkInventory) {
        DrinkInventory newDrinkInventory = null;
        String sql = "INSERT INTO drink_inventory (drink_id, quantity) VALUES (?, ?) RETURNING drink_inventory_id";
        try {
            int drinkInventoryId = jdbcTemplate.queryForObject(sql, int.class, drinkInventory.getDrinkId(),
                    drinkInventory.getDrinkQuantity());
            newDrinkInventory = getDrinkQuantityById(drinkInventoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newDrinkInventory;
    }

    public DrinkInventory updateDrinkQuantity(DrinkInventory drinkInventory) {
        DrinkInventory updatedDrinkInventory = null;
        String sql = "UPDATE drink_inventory SET quantity WHERE drink_inventory_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, drinkInventory.getDrinkQuantity(), drinkInventory.getDrinkInventoryId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedDrinkInventory = getDrinkQuantityById(drinkInventory.getDrinkInventoryId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedDrinkInventory;
    }

    public int deleteDrinkQuantity(int drinkInventoryId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM drink_inventory WHERE drink_inventory_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, drinkInventoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private DrinkInventory mapRowToDrinkInventory(ResultSet row, int rowNumber) throws SQLException {
        DrinkInventory drinkInventory = new DrinkInventory();
        drinkInventory.setDrinkInventoryId(row.getInt("drink_inventory_id"));
        drinkInventory.setDrinkId(row.getInt("drink_id"));
        drinkInventory.setDrinkQuantity(row.getInt("quantity"));

        return drinkInventory;
    }
}
