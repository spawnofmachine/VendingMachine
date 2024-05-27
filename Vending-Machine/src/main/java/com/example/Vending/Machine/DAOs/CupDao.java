package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Exception.DaoException;
import com.example.Vending.Machine.Models.Cup;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CupDao {
    private JdbcTemplate jdbcTemplate;

    public CupDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Cup> getCups() {
        return jdbcTemplate.query("SELECT * FROM cup", this::mapRowToCup);
    }

    public Cup getCupById(int cupId) {
        return jdbcTemplate.queryForObject("SELECT * FROM cup WHERE cup_id = ?", this::mapRowToCup, cupId);
    }

    public Cup getCupBySize(String size) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM cup WHERE size = ?", this::mapRowToCup, size);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Cup addCup(Cup cup) {
        Cup newCup = null;
        String sql = "INSERT INTO cup (size, capacity, cost) VALUES (?, ?, ?) RETURNING cup_id";
        try {
            int cupId = jdbcTemplate.queryForObject(sql, int.class, cup.getSize(), cup.getCapacity(), cup.getCost());
            newCup = getCupById(cupId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCup;
    }

    public Cup updateCup(Cup cup) {
        Cup updatedCup = null;
        String sql = "UPDATE cup SET size = ?, capacity = ?, cost = ? WHERE cup_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, cup.getSize(), cup.getCapacity(), cup.getCost(), cup.getCupId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCup;
    }

    public int deleteCup(int cupId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM cup WHERE cup_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, cupId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Cup mapRowToCup(ResultSet row, int rowNumber) throws SQLException {
        Cup cup = new Cup();
        cup.setCupId(row.getInt("cup_id"));
        cup.setSize(row.getString("size"));
        cup.setCapacity(row.getInt("capacity"));
        cup.setCost(row.getDouble("cost"));

        return cup;
    }
}
