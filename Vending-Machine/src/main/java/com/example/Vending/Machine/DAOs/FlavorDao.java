package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Exception.DaoException;
import com.example.Vending.Machine.Models.Flavor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FlavorDao {
    private JdbcTemplate jdbcTemplate;

    public FlavorDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Flavor> getFlavors() {
        return jdbcTemplate.query("SELECT * FROM flavor", this::mapRowToFlavor);
    }

    public Flavor getFlavorById(int flavorId) {
        return jdbcTemplate.queryForObject("SELECT * FROM flavor WHERE flavor_id = ?", this::mapRowToFlavor, flavorId);
    }

    public Flavor getFlavorByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM flavor WHERE name = ?", this::mapRowToFlavor, name);
    }

    public Flavor addFlavor(Flavor flavor) {
        Flavor newFlavor = null;
        String sql = "INSERT INTO flavor (name) VALUES (?) RETURNING flavor_id";
        try {
            int flavorId = jdbcTemplate.queryForObject(sql, int.class, flavor.getName());
            newFlavor = getFlavorById(flavorId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newFlavor;
    }

    public Flavor updateFlavor(Flavor flavor) {
        Flavor updatedFlavor = null;
        String sql = "UPDATE flavor SET name = ? Where flavor_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, flavor.getName(), flavor.getFlavorId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedFlavor = getFlavorById(flavor.getFlavorId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedFlavor;
    }

    public int deleteFlavor(int flavorId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM flavor WHERE flavor_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, flavorId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Flavor mapRowToFlavor(ResultSet row, int rowNumber) throws SQLException {
        Flavor flavor = new Flavor();
        flavor.setFlavorId(row.getInt("flavor_id"));
        flavor.setName(row.getString("name"));

        return flavor;
    }
}
