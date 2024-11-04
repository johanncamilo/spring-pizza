
package com.belos.spring_pizza.persistence.repository;

import com.belos.spring_pizza.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);
    List<OrderEntity> findAllByMethodIn(List<String> methods);

    @Query(value = "select * from pizza_order where id_customer = :id", nativeQuery = true)
    List<OrderEntity> findCustomerOrders(@Param("id") String idCustomer);
}
