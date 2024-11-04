package com.belos.spring_pizza.persistence.repository;

import com.belos.spring_pizza.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends ListCrudRepository<CustomerEntity, String> {
    //    JPQL instead of queryMethod
    @Query(value = "select c from CustomerEntity c where c.phoneNumber = :phone")
    CustomerEntity findByPhone(@Param("phone") String phone);
}
