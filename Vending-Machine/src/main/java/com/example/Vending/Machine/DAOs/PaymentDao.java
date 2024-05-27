package com.example.Vending.Machine.DAOs;

import com.example.Vending.Machine.Models.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class PaymentDao {
    private JdbcTemplate jdbcTemplate;

    public PaymentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Payment usePaymentByType() {
        return null;
    }

    public Payment addPayment() {
        return null;
    }

    public Payment updatePayment() {
        return null;
    }

    public Payment deletePayment() {
        return null;
    }
}
