
package com.belos.spring_pizza.persistence.repository;

import com.belos.spring_pizza.persistence.entity.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {}
