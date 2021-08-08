package com.adobe.bookstore.service;

import com.adobe.bookstore.models.Order;
import com.adobe.bookstore.repository.OrdersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository repository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository repository) {
        this.repository = repository;
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order createOrder() {
        Order order = new Order();
        return insertOrder(order);
    }

    public Order insertOrder(Order order) {
        return repository.save(order);
    }

    public String getJsonOrders() throws JsonProcessingException {
        List<String> ordersId = repository.findAll().stream().map(Order::getId)
            .collect(Collectors.toList());
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ordersId);
    }

    public String getCSVOrders() {
        List<Order> orders = repository.findAll();
        return orders.stream().map(Order::getId).collect(Collectors.joining(","));
    }
}
