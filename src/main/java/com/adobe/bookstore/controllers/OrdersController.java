package com.adobe.bookstore.controllers;

import com.adobe.bookstore.service.OrdersService;
import com.adobe.bookstore.service.OrdersServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersServiceImpl ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public ResponseEntity<String> getOrders(
        @RequestParam(name = "format", required = false) String type)
        throws JsonProcessingException {
        if ("text/csv".equals(type)) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv"))
                .body(ordersService.getCSVOrders());
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                ordersService.getJsonOrders());
        }
    }


}
