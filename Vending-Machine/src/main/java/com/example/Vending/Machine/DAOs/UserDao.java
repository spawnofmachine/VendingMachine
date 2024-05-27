package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDao {
    private JdbcTemplate jdbctemplate;
    private PasswordEncoder passwordEncoder;

    public UserDao(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbctemplate = new JdbcTemplate(dataSource);
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getALlUsers() {
        return jdbctemplate.query("SELECT * FROM user", this::mapRowToUser);
    }

    public User getUser(String username) {
        return jdbctemplate.queryForObject("SELECT * FROM user WHERE username = ?",
                this::mapRowToUser, username);
    }

    public List<String> getRolesForUser(String username) {
        return jdbctemplate.queryForList("SELECT role FROM roles WHERE username = ?",
                String.class, username);
    }

    public void createUser(User user) {
        jdbctemplate.update(
                "INSERT INTO user (username, password, first_name, last_name, email) VALUES(?, ?, ?, ?, ?",
                user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName(), user.getEmail()
        );
    }

    public void updateUser(User user, boolean updatePassword) {
        if (updatePassword) {
            jdbctemplate.update(
                    "UPDATE user SET password = ?, first_name = ?, last_name = ?, email = ? WHERE username = ?",
                    passwordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername()
            );
        } else {
            jdbctemplate.update(
                    "UPDATE user SET first_name = ?, last_name = ?, email = ? WHERE username = ?",
                    user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername()
            );
        }
    }

    public void deleteUser(String username) {
        jdbctemplate.update(
                "DELETE FROM user WHERE username = ?",
                username
        );
    }

    public void addRoleToUser(String username, String role) {
        jdbctemplate.update(
                "INSERT INTO roles (username, role) VALUES (?, ?)",
                username, role
        );
    }

    public void removeRoleFromUser(String username, String role) {
        jdbctemplate.update(
                "DELETE FROM user WHERE username = ? AND role = ?",
                username, role
        );
    }

    public boolean checkUsernameAndPassword(String username, String password) {
        User user = getUser(username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    private User mapRowToUser(ResultSet row, int rowNumber) throws SQLException {
        User user = new User();
        user.setUsername(row.getString("username"));
        user.setPassword(row.getString("password"));
        user.setFirstName(row.getString("first_name"));
        user.setLastName(row.getString("last_name"));
        user.setEmail(row.getString("email"));
        return user;
    }
}
