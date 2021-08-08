package com.adobe.bookstore.service;

import com.adobe.bookstore.models.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrdersService {

    Order createOrder();
    String getJsonOrders() throws JsonProcessingException;
    String getCSVOrders();

}
