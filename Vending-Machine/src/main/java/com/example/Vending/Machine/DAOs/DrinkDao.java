package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Exception.DaoException;
import com.example.Vending.Machine.Models.Drink;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DrinkDao {
    private JdbcTemplate jdbcTemplate;

    public DrinkDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Drink> getDrinks() {
        return jdbcTemplate.query("SELECT * FROM drink", this::mapRowToDrink);
    }

    public Drink getDrinkById(int drinkId) {
        return jdbcTemplate.queryForObject("SELECT * FROM drink WHERE drink_id = ?", this::mapRowToDrink, drinkId);
    }

    public Drink getDrinkByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM drink WHERE name = ?", this::mapRowToDrink, name);
    }

    public Drink addDrink(Drink drink) {
        Drink newDrink = null;
        String sql = "INSERT INTO drink (name, type, cost, canbeflavored) VALUES (?, ?, ?, ?) RETURNING drink_id";
        try {
            int drinkId = jdbcTemplate.queryForObject(sql, int.class, drink.getName(), drink.getType(), drink.getCost(),
                    drink.isCanBeFlavored());
            newDrink = getDrinkById(drinkId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newDrink;
    }

    public Drink updateDrink(Drink drink) {
        Drink updatedDrink = null;
        String sql = "UPDATE drink SET name = ?, type = ?, cost = ?, canbeflavored = ? WHERE drink_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, drink.getName(), drink.getType(), drink.getCost(), drink.isCanBeFlavored(),
                    drink.getDrinkId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedDrink = getDrinkById(drink.getDrinkId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedDrink;
    }

    public int deleteDrink(int drinkId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM drink WHERE id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, drinkId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Drink mapRowToDrink(ResultSet row, int rowNumber) throws SQLException {
        Drink drink = new Drink();
        drink.setDrinkId(row.getInt("drink_id"));
        drink.setName(row.getString("name"));
        drink.setType(row.getString("type"));
        drink.setCost(row.getDouble("cost"));
        drink.setCanBeFlavored(row.getBoolean("canbeflavored"));

        return drink;
    }
}
