package com.adobe.bookstore.controllers;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.adobe.bookstore.service.OrdersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OrdersController.class)
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdersServiceImpl ordersService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getOrders() throws Exception {
        mockMvc.perform(get("/orders").param("format", "non-csv")).andExpect(status().isOk());

        verify(ordersService, times(1)).getJsonOrders();
    }
}