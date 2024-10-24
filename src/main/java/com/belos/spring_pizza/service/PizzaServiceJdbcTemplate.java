package com.belos.spring_pizza.service;

import com.belos.spring_pizza.persistence.entity.PizzaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PizzaServiceJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PizzaEntity> getAll() {
        return this.jdbcTemplate.query("select * from pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
    }
}
