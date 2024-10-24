package com.belos.spring_pizza.persistence.repository;

import com.belos.spring_pizza.persistence.entity.PizzaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
}
