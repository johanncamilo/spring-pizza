package com.belos.spring_pizza.service;

import com.belos.spring_pizza.persistence.entity.OrderEntity;
import com.belos.spring_pizza.persistence.projection.OrderSummary;
import com.belos.spring_pizza.persistence.repository.OrderRepository;
import com.belos.spring_pizza.service.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String ON_SITE = "S";

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll() {
        List<OrderEntity> orders = this.orderRepository.findAll();
        orders.forEach(o -> System.out.println(o.getCustomer().getName()));
        return orders;
    }

    public List<OrderEntity> getTodayOrders() {
        LocalDateTime today = LocalDate.now().atTime(0, 0);
        return this.orderRepository.findAllByDateAfter(today);
    }

    public List<OrderEntity> getOutsideOrders() {
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);

        // esto recibe una lista de Strings por eso la variable de arriba se convierte con Arrays.asList
        return this.orderRepository.findAllByMethodIn(methods);
    }

    public List<OrderEntity> getCustomerOrders(String idCustomer) {
        return this.orderRepository.findCustomerOrders(idCustomer);
    }

    public OrderSummary getSummary(int orderId) {
        return this.orderRepository.findSummary(orderId);
    }

    @Transactional
    public boolean saveRandomOrder(RandomOrderDto randomOrderDto) {
         return this.orderRepository.saveRandomOrder(randomOrderDto.getIdCustomer(), randomOrderDto.getMethod());
    }
}
