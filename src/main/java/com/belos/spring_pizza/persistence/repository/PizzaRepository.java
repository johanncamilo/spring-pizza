package com.belos.spring_pizza.persistence.repository;

import com.belos.spring_pizza.persistence.entity.PizzaEntity;
import com.belos.spring_pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

    int countAllByAvailableFalse();

    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);

    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);

    int countByVeganTrue();

    /**
     * TRADITIONAL WAY
     * Two params:
     *
     * @param idPizza  int
     * @param newPrice double
     */
    @Modifying
    @Query(value = """
            update pizza
            set price = :newPrice
            where id_pizza = :idPizza""", nativeQuery = true)
    void updatePriceTrad(@Param("idPizza") int idPizza, @Param("newPrice") double newPrice);

    /**
     * SPRING EXPRESSION LANGUAGE
     * One param dto
     * syntax: :#{#}
     *
     * @param newPizzaPrice de tipo UpdatePizzaPriceDto
     */
    @Modifying
    @Query(value = """
            update pizza
            set price = :#{#newPizzaPrice.newPrice}
            where id_pizza = :#{#newPizzaPrice.pizzaId}""", nativeQuery = true)
    void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);
}
